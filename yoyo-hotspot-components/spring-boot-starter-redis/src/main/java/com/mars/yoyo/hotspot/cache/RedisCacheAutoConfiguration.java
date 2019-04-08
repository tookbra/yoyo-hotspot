package com.mars.yoyo.hotspot.cache;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tookbra
 * @date 2018/4/16
 * @description
 */
@EnableCaching
@ConditionalOnClass({ JedisConnection.class, RedisOperations.class, Jedis.class })
@EnableConfigurationProperties(RedisProperties.class)
public class RedisCacheAutoConfiguration extends CachingConfigurerSupport {
	/**
	 * Redis connection configuration.
	 */
	@Configuration
	@ConditionalOnClass(GenericObjectPool.class)
	protected static class RedisConnectionConfiguration {

		private final RedisProperties properties;

		private final RedisSentinelConfiguration sentinelConfiguration;

		private final RedisClusterConfiguration clusterConfiguration;

		public RedisConnectionConfiguration(RedisProperties properties,
				ObjectProvider<RedisSentinelConfiguration> sentinelConfigurationProvider,
				ObjectProvider<RedisClusterConfiguration> clusterConfigurationProvider) {
			this.properties = properties;
			this.sentinelConfiguration = sentinelConfigurationProvider.getIfAvailable();
			this.clusterConfiguration = clusterConfigurationProvider.getIfAvailable();
		}

		@Bean(name = "redisConnectionFactory")
		@Primary
		public JedisConnectionFactory redisConnectionFactory() throws UnknownHostException {
			return applyProperties(createJedisConnectionFactory());
		}

		protected final JedisConnectionFactory applyProperties(JedisConnectionFactory factory) {
			factory.setHostName(this.properties.getHost());
			factory.setPort(this.properties.getPort());
			if (this.properties.getPassword() != null) {
				factory.setPassword(this.properties.getPassword());
			}
			factory.setDatabase(this.properties.getDatabase());
			if (this.properties.getTimeout() > 0) {
				factory.setTimeout(this.properties.getTimeout());
			}
			return factory;
		}

		protected final RedisSentinelConfiguration getSentinelConfig() {

			RedisProperties.Sentinel sentinelProperties = this.properties.getSentinel();

			// 判断是否已添加集群配置信息
			if (this.sentinelConfiguration != null) {

				// 取得master节点
				RedisNode master = (RedisNode) this.sentinelConfiguration.getMaster();

				// master节点存在，并且是配置的master节点，则使用该配置
				if (master != null && StringUtils.hasText(master.getHost())
						&& master.getHost().equals(sentinelProperties.getMaster())) {

					return this.sentinelConfiguration;

				}
			}

			if (sentinelProperties != null && StringUtils.hasText(sentinelProperties.getMaster())) {
				RedisSentinelConfiguration config = new RedisSentinelConfiguration();
				config.master(sentinelProperties.getMaster());
				config.setSentinels(createSentinels(sentinelProperties));
				return config;
			}
			return null;
		}

		/**
		 * Create a {@link RedisClusterConfiguration} if necessary.
		 * 
		 * @return {@literal null} if no cluster settings are set.
		 */
		protected final RedisClusterConfiguration getClusterConfiguration() {
			if (this.clusterConfiguration != null) {
				return this.clusterConfiguration;
			}
			if (this.properties.getCluster() == null) {
				return null;
			}
			RedisProperties.Cluster clusterProperties = this.properties.getCluster();
			RedisClusterConfiguration config = new RedisClusterConfiguration(clusterProperties.getNodes());

			if (clusterProperties.getMaxRedirects() != null) {
				config.setMaxRedirects(clusterProperties.getMaxRedirects());
			}
			return config;
		}

		private List<RedisNode> createSentinels(RedisProperties.Sentinel sentinel) {
			List<RedisNode> nodes = new ArrayList<RedisNode>();
			for (String node : StringUtils.commaDelimitedListToStringArray(sentinel.getNodes())) {
				try {
					String[] parts = StringUtils.split(node, ":");
					Assert.state(parts.length == 2, "Must be defined as 'host:port'");
					nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
				} catch (RuntimeException ex) {
					throw new IllegalStateException("Invalid redis sentinel " + "property '" + node + "'", ex);
				}
			}
			return nodes;
		}

		private JedisConnectionFactory createJedisConnectionFactory() {
			JedisPoolConfig poolConfig = this.properties.getPool() != null ? jedisPoolConfig() : new JedisPoolConfig();

			if (getSentinelConfig() != null) {
				return new JedisConnectionFactory(getSentinelConfig(), poolConfig);
			}
			if (getClusterConfiguration() != null) {
				return new JedisConnectionFactory(getClusterConfiguration(), poolConfig);
			}
			return new JedisConnectionFactory(poolConfig);
		}

		private JedisPoolConfig jedisPoolConfig() {
			JedisPoolConfig config = new JedisPoolConfig();
			RedisProperties.Pool props = this.properties.getPool();
			config.setMaxTotal(props.getMaxActive());
			config.setMaxIdle(props.getMaxIdle());
			config.setMinIdle(props.getMinIdle());
			config.setMaxWaitMillis(props.getMaxWait());
			return config;
		}

	}

	@Bean
	public StringRedisTemplate bpRedisTemplate(JedisConnectionFactory redisConnectionFactory) {

		StringRedisTemplate redisTemplate = new StringRedisTemplate(redisConnectionFactory);

		// 设置序列化类
		GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

		redisTemplate.afterPropertiesSet();

		return redisTemplate;

	}

	@Bean
	public KeyGenerator wiselyKeyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};

	}

	@Bean
	@Primary
	public CacheManager cacheManager(RedisTemplate<String, String> bpRedisTemplate) {

		RedisCacheManager cacheManager = new RedisCacheManager(bpRedisTemplate);
		// 使用自定义key名
		cacheManager.setUsePrefix(true);
		return cacheManager;
	}


	/**
	 * 秒级别的缓存
	 * @param bpRedisTemplate
	 * @return
	 */
	@Bean(name = "cacheManagerSecond")
	public CacheManager cacheManagerSecond(RedisTemplate bpRedisTemplate) {
		Integer second = 60;

		RedisCacheManager cacheManager = new RedisCacheManager(bpRedisTemplate);
		// 使用自定义key名
		cacheManager.setUsePrefix(true);
		//second 秒过期
		cacheManager.setDefaultExpiration(second);

		return cacheManager;
	}

	/**
	 * 5分钟缓存管理
	 * @param bpRedisTemplate
	 * @return
	 */
	@Bean(name = "cacheManagerFiveMinute")
	public CacheManager cacheManagerTenMinute(RedisTemplate bpRedisTemplate) {

		RedisCacheManager cacheManager = new RedisCacheManager(bpRedisTemplate);
		// 使用自定义key名
		cacheManager.setUsePrefix(true);
		//分钟过期
		cacheManager.setDefaultExpiration(300);

		return cacheManager;
	}

	/**
	 * 保存一天的缓存
	 *
	 * @return
	 */
	@Bean(name = "daysCacheManager")
	public CacheManager daysCacheManager(StringRedisTemplate stringRedisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(stringRedisTemplate);
		// 使用自定义key名
		cacheManager.setUsePrefix(true);
		cacheManager.setDefaultExpiration(60 * 60 * 24);
		return cacheManager;
	}

	/**
	 * 保存30天的缓存
	 *
	 * @return
	 */
	@Bean(name = "days30CacheManager")
	public CacheManager days30CacheManager(StringRedisTemplate stringRedisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(stringRedisTemplate);
		// 使用自定义key名
		cacheManager.setUsePrefix(true);
		cacheManager.setDefaultExpiration(60 * 60 * 24 * 30);
		return cacheManager;
	}



}
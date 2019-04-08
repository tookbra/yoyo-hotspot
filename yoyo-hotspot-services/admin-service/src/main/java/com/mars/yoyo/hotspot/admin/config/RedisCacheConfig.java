package com.mars.yoyo.hotspot.admin.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisShardInfo;

/**
 * Created by mac on 2017/9/8.
 */
@Configuration
@ConfigurationProperties(prefix="spring.redis")
public class RedisCacheConfig {

    @Setter
    private String host;

    @Setter
    private int port;

    @Setter
    private String password;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisShardInfo shardInfo = new JedisShardInfo(host, port);
        shardInfo.setPassword(password);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(shardInfo);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisCacheTemplate(JedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisHelper afcsRedisCache(RedisTemplate<String, Object> redisCacheTemplate) {
        RedisHelper redisCache = new RedisHelper(redisCacheTemplate);
        return redisCache;
    }

}

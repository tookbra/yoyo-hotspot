package com.mars.yoyo.hotspot.cache;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.util.List;

/**
 * @author tookbra
 * @date 2018/4/16
 * @description
 */
@Configuration
@ConditionalOnClass(Redisson.class)
@AutoConfigureBefore(CacheAutoConfiguration.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

    @Autowired
    RedissonProperties redissonProperties;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(name="redisson.config.type")
    RedissonClient redissonClient() {
        Config config = new Config();
        switch (redissonProperties.getType()) {
            case SINGLE:
                createSingle(config);
                break;
            case CLUSTER:
                createCluster(config);
                break;
            default:
                throw new IllegalArgumentException("illegal parameter: " + redissonProperties.getType());
        }

        return Redisson.create(config);
    }

    private void createSingle(Config config) {
        config.useSingleServer()
                .setPassword(redissonProperties.getPassword())
                .setAddress(redissonProperties.getAddress())
                .setTimeout(redissonProperties.getTimeout())
                .setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize());
    }

    private void createCluster(Config config) {
        List<URI> nodeAddresses = redissonProperties.getNodeAddresses();

        int size = nodeAddresses.size();
        String[] address = new String[size];
        for(int i=0; i<size; i++){
            address[i] = nodeAddresses.get(i).toString();
        }
        config.useClusterServers().addNodeAddress(address);
    }
}

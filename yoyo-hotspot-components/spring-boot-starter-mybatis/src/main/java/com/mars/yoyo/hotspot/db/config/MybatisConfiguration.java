package com.mars.yoyo.hotspot.db.config;

import com.mars.yoyo.hotspot.db.datasource.DataSourceContextHolder;
import com.mars.yoyo.hotspot.db.datasource.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.apache.bcel.util.ClassLoaderRepository;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;


/**
 * @author tookbra
 * @date 2018/5/17
 * @description
 */
@Slf4j
@Configuration
@AutoConfigureAfter({DataSourceConfiguration.class})
public class MybatisConfiguration extends MybatisAutoConfiguration {

    private static Log logger = LogFactory.getLog(MybatisConfiguration.class);


    @Resource(name = "masterDataSource")
    private DataSource masterDataSource;

    @Resource(name = "slave1DataSource")
    private DataSource slave1DataSource;


    public MybatisConfiguration(MybatisProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider,
                                ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider, ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
        super(properties, interceptorsProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        return super.sqlSessionFactory(roundRobinDataSouceProxy());
    }

    private AbstractRoutingDataSource roundRobinDataSouceProxy() {
        DynamicDataSource proxy = new DynamicDataSource();
        Map<Object, Object> targetDataResources = new ClassLoaderRepository.SoftHashMap();
        targetDataResources.put(DataSourceContextHolder.DbType.MASTER, masterDataSource);
        targetDataResources.put(DataSourceContextHolder.DbType.SLAVE1, slave1DataSource);
        proxy.setDefaultTargetDataSource(masterDataSource);
        proxy.setTargetDataSources(targetDataResources);
        return proxy;
    }
}

package com.mars.yoyo.hotspot.mifi.runner;

import com.google.common.base.Joiner;
import com.mars.yoyo.hotspot.mifi.cache.ConfigDictionaryCache;
import com.mars.yoyo.hotspot.mifi.cache.DeliveryChannelCache;
import com.mars.yoyo.hotspot.mifi.dao.SysDictionaryMapper;
import com.mars.yoyo.hotspot.mifi.domain.DeliveryChannel;
import com.mars.yoyo.hotspot.mifi.domain.Product;
import com.mars.yoyo.hotspot.mifi.domain.SysDictionary;
import com.mars.yoyo.hotspot.mifi.service.DeliveryChannelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author tookbra
 * @date 2018/6/3
 * @description
 */
@Slf4j
public class CacheInitRunner implements CommandLineRunner {

    @Autowired
    DeliveryChannelService deliveryChannelService;

    @Autowired
    DeliveryChannelCache deliveryChannelCache;

    @Autowired
    ConfigDictionaryCache configDictionaryCache;

    @Autowired
    SysDictionaryMapper sysDictionaryMapper;

    @Override
    public void run(String... args) throws Exception {

        log.info("delivery channel cache init begin");
        List<DeliveryChannel> deliveryChannelList = deliveryChannelService.findAll();
        if (CollectionUtils.isEmpty(deliveryChannelList)) {
            log.warn("delivery channel cache init over because the list is empty");
            return;
        }
        deliveryChannelList.forEach(deliveryChannel ->
                deliveryChannelCache.put(deliveryChannel)
        );
        log.info("delivery channel cache init end");

        log.info("config dictionary  of cache init begin");
        Example dictionaryExample = new Example(SysDictionary.class);
        dictionaryExample.createCriteria().andEqualTo("status", 1);
        List<SysDictionary> configDictionaries = sysDictionaryMapper.selectByExample(dictionaryExample);
        if (org.springframework.util.CollectionUtils.isEmpty(configDictionaries)) {
            log.warn("config dictionary init over because the configDictionaries is empty");
            return;
        }
        configDictionaries.forEach(dictionary ->
                configDictionaryCache.putDictionaryName(Joiner.on("_").join(dictionary.getType(), dictionary.getCode()),
                        dictionary.getValue())
        );
        log.info("config dictionary  of cache init end");
    }
}

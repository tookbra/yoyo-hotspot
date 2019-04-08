package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.BaseTest;
import com.mars.yoyo.hotspot.util.SeqUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tookbra
 * @date 2018/5/24
 * @description
 */
public class IdGeneratorServiceTest extends BaseTest {


    @Test
    public void testGeneratorId() throws Exception {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 1; i++) {
            cachedThreadPool.execute(() -> {
                while (true)
                    System.out.println(SeqUtil.generatorId(1));
            });
        }
        System.in.read();
    }
}

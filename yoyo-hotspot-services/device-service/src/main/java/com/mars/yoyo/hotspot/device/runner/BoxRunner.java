package com.mars.yoyo.hotspot.device.runner;

import com.mars.yoyo.hotspot.device.BoxServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Component
public class BoxRunner implements CommandLineRunner {

    @Autowired
    BoxServer boxServer;

    @Override
    public void run(String... strings) throws Exception {
        boxServer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                boxServer.stop();
            }
        }));
    }
}

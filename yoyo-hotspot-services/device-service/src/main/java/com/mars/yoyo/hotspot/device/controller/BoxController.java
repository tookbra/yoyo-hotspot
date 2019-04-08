package com.mars.yoyo.hotspot.device.controller;

import com.mars.yoyo.hotspot.device.service.BoxService;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tookbra
 * @date 2018/7/2
 * @description
 */
@RestController
@RequestMapping("/boxs")
public class BoxController {

    @Autowired
    BoxService boxService;

    @GetMapping("/inventory")
    RestResult findInventory() {
        boxService.queryInventory("RL1A03171200007");
        return RestResult.success("");
    }
}

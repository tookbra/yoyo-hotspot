package com.mars.yoyo.hotspot.mifi.web;

import com.mars.yoyo.hotspot.mifi.dto.input.ActivateNotifyDto;
import com.mars.yoyo.hotspot.mifi.dto.input.CdrNotifyDto;
import com.mars.yoyo.hotspot.mifi.dto.input.UsageNotifyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tookbra
 * @date 2018/9/11
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/webhooks/flow")
public class FlowController {

    /**
     * 达量通知
     * @return
     */
    @PostMapping("/usage/notify")
    public String usageNotify(UsageNotifyDto usageNotifyDto) {
        log.info("flow usage notify {}", usageNotifyDto.toString());
        return "success";
    }

    /**
     * 话单通知
     * @param cdrNotifyDto
     * @return
     */
    @PostMapping("/cdr/notify")
    public String cdrNotify(CdrNotifyDto cdrNotifyDto) {
        log.info("flow cdr notify {}", cdrNotifyDto.toString());
        return "success";
    }

    /**
     * 激活通知
     * @param activateNotifyDto
     * @return
     */
    @PostMapping("/activate/notify")
    public String activateNotify(ActivateNotifyDto activateNotifyDto) {
        log.info("flow activate notify {}", activateNotifyDto.toString());
        return "success";
    }
}

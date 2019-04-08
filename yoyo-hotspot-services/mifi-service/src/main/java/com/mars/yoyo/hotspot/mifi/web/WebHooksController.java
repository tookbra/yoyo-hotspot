package com.mars.yoyo.hotspot.mifi.web;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.mars.yoyo.hotspot.mifi.service.OrderService;
import com.paypal.api.payments.Event;
import com.paypal.api.payments.Sale;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * @author tookbra
 * @date 2018/6/12
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/webhooks")
public class WebHooksController {

    @Autowired
    OrderService orderService;

    @PostMapping("/paypal")
    @ResponseStatus(value = HttpStatus.OK)
    String getPaypalHooks(@RequestBody Event event) {
        log.info("webhooks recive ={}", event.toJSON());
        Map<String, Object> map = (Map<String, Object>)event.getResource();
        String state = (String) map.get("state");
        if(StringUtils.isNotBlank(state)) {
            if("completed".equals(state)) {
                String paymentId = (String)map.get("parent_payment");
                orderService.notifyPaypal(paymentId);
            }
        }
        return "";
    }

    @PostMapping("/wechat")
    @ResponseStatus(value = HttpStatus.OK)
    String getWechatHooks(HttpServletRequest request, HttpServletResponse response) {
        try {
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlResult);
            log.info("webchat notify, msg={}", result.toString());
            if(result.getReturnCode().equals(WxPayConstants.ResultCode.SUCCESS)) {
                orderService.notifyWechat(result.getOutTradeNo());
            } else {
                log.info("wechat pay error, msg={}", result.getReturnMsg());
            }
            return WxPayNotifyResponse.success("success");
        } catch (Exception e) {
            log.info("erro={}", e.getMessage());
            e.printStackTrace();
            return WxPayNotifyResponse.fail("error");
        }
    }

}

package com.mars.yoyo.hotspot.cmp.service;

import com.mars.yoyo.hotspot.cmp.BaseTest;
import com.mars.yoyo.hotspot.cmp.config.rest.CmpResult;
import com.mars.yoyo.hotspot.cmp.config.rest.dto.input.SendOrderDto;
import com.mars.yoyo.hotspot.cmp.config.rest.dto.output.AmountOutputDto;
import com.mars.yoyo.hotspot.cmp.config.rest.dto.output.MarginOutputDto;
import com.mars.yoyo.hotspot.cmp.config.rest.dto.output.SendMsgOutputDto;
import com.mars.yoyo.hotspot.cmp.config.rest.dto.output.SmsMsgStatusOutputDto;
import com.mars.yoyo.hotspot.cmp.service.cmp.CmpService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author tookbra
 * @date 2018/5/12
 * @description
 */
public class CmpServiceTest extends BaseTest {

    @Autowired
    CmpService cmpService;

    @Test
    public void sendOrderTest() {
        SendOrderDto sendOrderDto = new SendOrderDto();
        sendOrderDto.setCardNo("1064708738373");
        sendOrderDto.setProductId("cfcd208495d565ef66e7dff9f98764da");
        cmpService.sendOrder(sendOrderDto);
    }

    @Test
    public void getAmountTest() {
        CmpResult<AmountOutputDto> result = cmpService.getAmount();
        System.out.println(result.toString());
    }

    @Test
    public void getChaxunTest() {
        CmpResult<MarginOutputDto> result = cmpService.getChaxun("1064708738373");
        System.out.println(result.toString());
    }

    @Test
    public void sendMsgTest() {
        CmpResult<SendMsgOutputDto> result = cmpService.sendMsg("1064708738374", "这是一条测试短信1");
        System.out.println(result.toString());
        //20180514151507072207
    }

    @Test
    public void getSmsMsgStatusTest() {
        CmpResult<SmsMsgStatusOutputDto> result = cmpService.getSmsMsgStatus("20180514151507072207");
        System.out.println(result.toString());
    }

    @Test
    public void startTest() {
        CmpResult result = cmpService.start("1064708738373");
        System.out.println(result.toString());
    }

    @Test
    public void stopTest() {
        CmpResult result = cmpService.stop("1064708738373");
        System.out.println(result.toString());
    }
}

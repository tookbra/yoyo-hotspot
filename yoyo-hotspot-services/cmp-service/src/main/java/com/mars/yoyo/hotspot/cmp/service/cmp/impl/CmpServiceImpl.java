package com.mars.yoyo.hotspot.cmp.service.cmp.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mars.yoyo.hotspot.cmp.config.rest.CmpProperties;
import com.mars.yoyo.hotspot.cmp.config.rest.CmpResult;
import com.mars.yoyo.hotspot.cmp.config.rest.dto.output.*;
import com.mars.yoyo.hotspot.cmp.config.rest.dto.input.SendOrderDto;
import com.mars.yoyo.hotspot.cmp.service.cmp.CmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tookbra
 * @date 2018/5/12
 * @description
 */
@Slf4j
@Service
public class CmpServiceImpl implements CmpService {

    @Autowired
    CmpProperties cmpProperties;

    @Autowired
    RestTemplate cmpTemplate;

    @Override
    public CmpResult<SendOrderOutputDto> sendOrder(SendOrderDto sendOrderDto) {
        Map<String, Object> map = new HashMap(4);
        map.put("product_id", sendOrderDto.getProductId());
        map.put("cardno", sendOrderDto.getCardNo());
        map.put("iseffect", sendOrderDto.getIseffect());
        map.put("out_trade_no", sendOrderDto.getOutTradeNo());
        String json = cmpTemplate.getForObject(cmpProperties.getRequestUrl().getSendOrder(), String.class, map);
        CmpResult<SendOrderOutputDto> result = JSON.parseObject(json,
                new TypeReference<CmpResult<SendOrderOutputDto>>(){});
        return result;
    }

    @Override
    public CmpResult<AmountOutputDto> getAmount() {
        Map<String, Object> map = new HashMap(0);
        String json = cmpTemplate.getForObject(cmpProperties.getRequestUrl().getAmount(), String.class, map);
        CmpResult<AmountOutputDto> result = JSON.parseObject(json,
                new TypeReference<CmpResult<AmountOutputDto>>(){});
        return result;
    }

    @Override
    public CmpResult<MarginOutputDto> getChaxun(String cardNo) {
        Map<String, Object> map = new HashMap(1);
        map.put("cardno", cardNo);
        String json = cmpTemplate.getForObject(cmpProperties.getRequestUrl().getChaxun(), String.class, map);
        CmpResult<MarginOutputDto> result = JSON.parseObject(json, new TypeReference<CmpResult<MarginOutputDto>>(){});
        return result;
    }

    @Override
    public CmpResult<SendMsgOutputDto> sendMsg(String cardNo, String content) {
        Map<String, Object> map = new HashMap(2);
        map.put("cardno", cardNo);
        map.put("content", content);
        String json = cmpTemplate.getForObject(cmpProperties.getRequestUrl().getSendMsg(), String.class, map);
        CmpResult<SendMsgOutputDto> result = JSON.parseObject(json, new TypeReference<CmpResult<SendMsgOutputDto>>(){});
        return result;
    }

    @Override
    public CmpResult<SmsMsgStatusOutputDto> getSmsMsgStatus(String msgId) {
        Map<String, Object> map = new HashMap(1);
        map.put("msgid", msgId);
        String json = cmpTemplate.getForObject(cmpProperties.getRequestUrl().getMsgStatus(), String.class, map);
        CmpResult<SmsMsgStatusOutputDto> result = JSON.parseObject(json, new TypeReference<CmpResult<SmsMsgStatusOutputDto>>(){});
        return result;
    }

    @Override
    public CmpResult start(String cardNo) {
        Map<String, Object> map = new HashMap(1);
        map.put("cardno", cardNo);
        String json = cmpTemplate.getForObject(cmpProperties.getRequestUrl().getStart(), String.class, map);
        CmpResult result = JSON.parseObject(json, CmpResult.class);
        return result;
    }

    @Override
    public CmpResult stop(String cardNo) {
        Map<String, Object> map = new HashMap(1);
        map.put("cardno", cardNo);
        String json = cmpTemplate.getForObject(cmpProperties.getRequestUrl().getStop(), String.class, map);
        CmpResult result = JSON.parseObject(json, CmpResult.class);
        return result;
    }
}

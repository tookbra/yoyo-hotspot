package com.mars.yoyo.hotspot.cmp.service.cmp;

import com.mars.yoyo.hotspot.cmp.config.rest.CmpResult;
import com.mars.yoyo.hotspot.cmp.config.rest.dto.output.*;
import com.mars.yoyo.hotspot.cmp.config.rest.dto.input.SendOrderDto;

/**
 * @author tookbra
 * @date 2018/5/12
 * @description
 */
public interface CmpService {

    /**
     * 充值订单
     * @param sendOrderDto
     * @return SendOrderOutputDto
     */
    CmpResult<SendOrderOutputDto> sendOrder(SendOrderDto sendOrderDto);

    /**
     * 获取账号余额
     */
    CmpResult<AmountOutputDto> getAmount();

    /**
     * 物联网卡余量查询
     * @param cardNo 物联网卡号
     */
    CmpResult<MarginOutputDto> getChaxun(String cardNo);

    /**
     * 发送短信消息
     * @param cardNo 物联网卡号
     * @param content 短信内容
     */
    CmpResult<SendMsgOutputDto> sendMsg(String cardNo, String content);

    /**
     * 消息短信ID
     * @param msgId
     * @return
     */
    CmpResult<SmsMsgStatusOutputDto> getSmsMsgStatus(String msgId);

    /**
     * 物联网卡复机
     * @param cardNo 物联网卡号
     */
    CmpResult start(String cardNo);

    /**
     * 物联网卡停机
     * @param cardNo 物联网卡号
     */
    CmpResult stop(String cardNo);
}

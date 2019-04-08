package com.mars.yoyo.hotspot.mifi.service.impl;

import com.mars.yoyo.hotspot.mifi.dao.PayOrderMapper;
import com.mars.yoyo.hotspot.mifi.domain.PayOrder;
import com.mars.yoyo.hotspot.mifi.service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @author tookbra
 * @date 2018/7/1
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class PayOrderServiceImpl implements PayOrderService {

    @Resource
    PayOrderMapper payOrderMapper;

    @Override
    public PayOrder findByOrderNo(String orderNo) {
        Example example = new Example(PayOrder.class);
        example.createCriteria().andEqualTo("orderId", orderNo);
        PayOrder payOrder = payOrderMapper.selectOneByExample(example);
        return payOrder;
    }
}

package com.mars.yoyo.hotspot.ms.service.impl;

import com.mars.yoyo.hotspot.ms.dao.SmsTemplateMapper;
import com.mars.yoyo.hotspot.ms.domain.SmsTemplate;
import com.mars.yoyo.hotspot.ms.service.SmsTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class SmsTemplateServiceImpl implements SmsTemplateService {

    @Resource
    SmsTemplateMapper smsTemplateMapper;

    @Override
    public SmsTemplate getByCode(String code) {
        Example example = new Example(SmsTemplate.class);
        example.createCriteria().andEqualTo("state", 1).andEqualTo("code", code);
        SmsTemplate smsTemplate = smsTemplateMapper.selectOneByExample(example);
        return smsTemplate;
    }

    @Override
    public List<SmsTemplate> getEanbled() {
        Example example = new Example(SmsTemplate.class);
        example.createCriteria().andEqualTo("state", 1);
        List<SmsTemplate> list = smsTemplateMapper.selectByExample(example);
        return list;
    }
}

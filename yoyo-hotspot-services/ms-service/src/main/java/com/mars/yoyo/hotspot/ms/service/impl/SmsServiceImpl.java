package com.mars.yoyo.hotspot.ms.service.impl;

import com.alibaba.fastjson.JSON;
import com.mars.yoyo.hotspot.exception.BizFeignException;
import com.mars.yoyo.hotspot.ms.cache.SmsCaptchaCache;
import com.mars.yoyo.hotspot.ms.cache.SmsTemplateCache;
import com.mars.yoyo.hotspot.ms.config.SmsProperties;
import com.mars.yoyo.hotspot.ms.constant.SmsConstant;
import com.mars.yoyo.hotspot.ms.dao.SmsCaptchaMapper;
import com.mars.yoyo.hotspot.ms.dao.SmsLogMapper;
import com.mars.yoyo.hotspot.ms.dao.SmsMoMapper;
import com.mars.yoyo.hotspot.ms.dao.SmsReqMapper;
import com.mars.yoyo.hotspot.ms.domain.*;
import com.mars.yoyo.hotspot.ms.dto.input.SmsDto;
import com.mars.yoyo.hotspot.ms.dto.input.SmsInputDto;
import com.mars.yoyo.hotspot.ms.dto.input.SmsReportDto;
import com.mars.yoyo.hotspot.ms.dto.output.SmsReqOutput;
import com.mars.yoyo.hotspot.ms.enums.SmsTypeEnum;
import com.mars.yoyo.hotspot.ms.service.SmsService;
import com.mars.yoyo.hotspot.util.DateUtil;
import com.mars.yoyo.hotspot.util.PhoneUtil;
import com.mars.yoyo.hotspot.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class SmsServiceImpl implements SmsService {

    @Resource
    SmsLogMapper smsLogMapper;

    @Resource
    SmsCaptchaMapper smsCaptchaMapper;

    @Resource
    SmsMoMapper smsMoMapper;

    @Resource
    SmsReqMapper smsReqMapper;

    @Resource
    SmsProperties smsProperties;

    @Resource
    RestTemplate restTemplate;

    @Autowired
    SmsTemplateCache smsTemplateCache;

    @Autowired
    SmsCaptchaCache smsCaptchaCache;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendSms(SmsInputDto smsInputDto) {
        SmsTemplate smsTemplate = getSmsTemp(smsInputDto.getType());
        if(smsInputDto.getType().equals(SmsTypeEnum.REG.name())
                || smsInputDto.getType().equals(SmsTypeEnum.REG_EN.name())) {
            sendCaptcha(smsInputDto, smsTemplate);
        } else {
            sendSms(smsInputDto, smsTemplate);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void report(SmsReportDto smsReportDto) {
        if(smsReportDto.getMsgtype() == SmsConstant.SMS_TYPE_MO) {
            SmsMo smsMo = new SmsMo();
            smsMo.setPhone(smsReportDto.getPhone());
            smsMo.setContent(smsReportDto.getContent());
            smsMo.setSubId(smsReportDto.getSubid());
            smsMo.setReceiveTime(DateUtil.parsePureDateTime(smsReportDto.getReceivetime()));
            smsMoMapper.insertSelective(smsMo);
        }

        if(smsReportDto.getMsgtype() == SmsConstant.SMS_TYPE_REPORT) {
            Example example = new Example(SmsLog.class);
            example.createCriteria()
                    .andEqualTo("phone", smsReportDto.getPhone())
                    .andEqualTo("reqId", smsReportDto.getReqid());
            SmsReq smsReq = smsReqMapper.selectByPrimaryKey(smsReportDto.getReqid());
            if(smsReq != null) {
                smsReq.setReceiveTime(DateUtil.parsePureDateTime(smsReportDto.getReceivetime()));
                smsReq.setSendTime(DateUtil.parsePureDateTime(smsReportDto.getReceivetime()));
                smsReq.setState(smsReportDto.getState());
                smsReqMapper.updateByPrimaryKeySelective(smsReq);
            }
        }
    }

    /**
     * 发送验证码
     * @param smsInputDto
     * @param smsTemplate 短信模板
     */
    private void sendCaptcha(SmsInputDto smsInputDto, SmsTemplate smsTemplate) {
        String now = DateUtil.formatPureDateTime(new Date());
        Map<String, Object> map = new HashMap<>(1);
        String captcha = StringUtil.generateRandomCode(4);
        map.put("code", captcha);
        String content = parseTemplate(smsTemplate.getContent(), map);

        SmsDto smsDto = convertSmsDto(smsInputDto.getPhonePre(), smsInputDto.getPhone(), now, content);
        SmsCaptcha smsCaptcha = new SmsCaptcha();
        smsCaptcha.setPhone(smsInputDto.getPhone());
        smsCaptcha.setContent(content);
        smsCaptcha.setCaptcha(captcha);
        smsCaptcha.setSmsType(SmsTypeEnum.REG.name());
        smsCaptcha.setTemplateId(smsTemplate.getId());
        SmsReqOutput smsD = sendSms(smsDto);
        if(!smsD.getReqCode().equals(SmsConstant.SUCCESS_CODE)) {
            //短信发送失败
            throw new BizFeignException("短信发送失败");
        }
        smsCaptcha.setReqId(smsD.getReqId());
        smsCaptchaMapper.insertSelective(smsCaptcha);
        smsCaptchaCache.putCaptcha(smsInputDto.getType(), smsInputDto.getPhone(), captcha);
        SmsReq smsReq = new SmsReq();
        smsReq.setReqId(smsD.getReqId());
        addSmsReq(smsReq);
    }

    private void addSmsReq(SmsReq smsReq) {
        smsReqMapper.insertSelective(smsReq);
    }

    /**
     * 发送短信
     * @param smsInputDto
     * @param smsTemplate
     */
    private void sendSms(SmsInputDto smsInputDto, SmsTemplate smsTemplate) {
//        validatePhone(smsInputDto.getPhone());

        String now = DateUtil.formatPureDateTime(new Date());
        String content = smsTemplate.getContent();
        if(smsInputDto.getParamMap() != null) {
            content = parseTemplate(smsTemplate.getContent(), smsInputDto.getParamMap());
        }
        SmsDto smsDto = convertSmsDto(smsInputDto.getPhonePre(), smsInputDto.getPhone(), now, content);

        SmsLog smsLog = new SmsLog();
        smsLog.setUserId(1);
        smsLog.setPhone(smsInputDto.getPhone());
        smsLog.setContent(content);
        smsLog.setTemplateId(smsTemplate.getId());
        smsLog.setSmsType(smsInputDto.getType());
        SmsReqOutput smsD = sendSms(smsDto);
        if(!smsD.getReqCode().equals(SmsConstant.SUCCESS_CODE)) {
            //短信发送失败
            throw new BizFeignException("短信发送失败");
        }
        smsLog.setReqId(smsD.getReqId());
        smsLogMapper.insertSelective(smsLog);

        SmsReq smsReq = new SmsReq();
        smsReq.setReqId(smsD.getReqId());
        addSmsReq(smsReq);
    }

    private SmsTemplate getSmsTemp(String smsType) {
        SmsTemplate smsTemplate = smsTemplateCache.getSmsTemp(smsType);
        if (smsTemplate == null) {
            log.warn("缓存中无短信模版，smsType = {}", smsType);
            throw new BizFeignException("");
        }
        return smsTemplate;
    }

    private SmsReqOutput sendSms(SmsDto smsDto) {
        MultiValueMap<String, String> variables = new LinkedMultiValueMap<String, String>();
        variables.add("name", smsDto.getName());
        variables.add("pwd", smsDto.getPwd());
        if(smsDto.getPhonePre().equals("86")) {
            variables.add("phone", smsDto.getPhone());
        } else {
            variables.add("phone", "00" + smsDto.getPhonePre() + smsDto.getPhone());
        }
        variables.add("content", smsDto.getContent());
        variables.add("mttime", smsDto.getMttime());
        variables.add("rpttype", String.valueOf(smsDto.getRpttype()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(variables, headers);
        String result = restTemplate.postForEntity(smsProperties.getUrl(), entity, String.class).getBody();
        log.info("send sms result={}", result);
        if (StringUtils.isEmpty(result)) {
            log.error("短信无返回结果");
            throw new BizFeignException("短信发送失败");
        }

//        String json = smsClient.sendSms(smsDto);
        SmsReqOutput smsReqOutput = JSON.parseObject(result, SmsReqOutput.class);
        smsReqOutput = new SmsReqOutput();
        smsReqOutput.setReqCode(SmsConstant.SUCCESS_CODE);
        smsReqOutput.setReqId(String.valueOf(System.currentTimeMillis()));
        return smsReqOutput;
    }

    private String parseTemplate(String content, Map<String, Object> map){
        Set<Map.Entry<String, Object>> sets = map.entrySet();
        for(Map.Entry<String, Object> entry : sets) {
            String regex = "\\$\\{" + entry.getKey() + "\\}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);
            content = matcher.replaceAll(entry.getValue().toString());
        }
        return content;
    }

    private void validatePhone(String phone) {
        if(!PhoneUtil.isMobileExact(phone)) {
            throw new BizFeignException("手机格式错误");
        }
    }

    private SmsDto convertSmsDto(String phonePre, String phone, String now, String content) {
        SmsDto smsDto = new SmsDto();
        smsDto.setPhonePre(phonePre);
        smsDto.setName(smsProperties.getName());
        smsDto.setPwd(DigestUtils.md5Hex(smsProperties.getPwd() +  now));
        smsDto.setPhone(phone);
        smsDto.setContent(content);
        smsDto.setMttime(now);
        return smsDto;
    }
}

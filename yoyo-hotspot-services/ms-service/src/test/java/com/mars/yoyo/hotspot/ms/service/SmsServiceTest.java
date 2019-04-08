package com.mars.yoyo.hotspot.ms.service;

import com.mars.yoyo.hotspot.ms.BaseTest;
import com.mars.yoyo.hotspot.ms.cache.SmsCaptchaCache;
import com.mars.yoyo.hotspot.ms.dto.input.SmsInputDto;
import com.mars.yoyo.hotspot.ms.dto.input.SmsReportDto;
import com.mars.yoyo.hotspot.util.DateUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tookbra
 * @date 2018/5/16
 * @description
 */
public class SmsServiceTest {

    @Autowired
    SmsService smsService;

    @Autowired
    SmsCaptchaCache smsCaptchaCache;

    @Test
    public void sendSmsTest() {
        SmsInputDto smsInputDto = new SmsInputDto();
        smsInputDto.setPhone("15906695726");
        Map<String, Object> map = new HashMap<>();
        map.put("code", 123456);
        smsInputDto.setParamMap(map);
        smsInputDto.setType("REG");
        smsService.sendSms(smsInputDto);
    }

    @Test
    public void smsMoTest() {
        SmsReportDto smsReportDto = new SmsReportDto();
        smsReportDto.setMsgtype(0);
        smsReportDto.setContent("测试上行");
        smsReportDto.setSubid("01");
        smsReportDto.setPhone("15906695726");
        smsReportDto.setReceivetime("20180517201222");
        smsService.report(smsReportDto);
    }

    @Test
    public void smsReportTest() {
        SmsReportDto smsReportDto = new SmsReportDto();
        smsReportDto.setMsgtype(2);
        smsReportDto.setPhone("15906695726");
        smsReportDto.setReqid("1527523946959");
        smsReportDto.setReceivetime("20180517201222");
        smsReportDto.setSendtime("20180517201222");
        smsReportDto.setState("0");
        smsService.report(smsReportDto);
    }

    @Test
    public void convertTest() {
        String content = "[I'm A Fish Mefi] Dear Customer, thank you for using. As of ${end_month} / ${end_day} /${end_year}, you have used the package of “${productName}” with ${day} day ${hour} hours ${min} minutes for the device of No.${rentNo}, the accumulated settlement amount";
        Map<String, Object> map = new HashMap<>();
        map.put("begin_year", "2018");
        map.put("begin_month", "01");
        map.put("begin_day", "02");
        map.put("begin_hour", "11");

        map.put("end_year", "2018");
        map.put("end_month", "01");
        map.put("end_day", "02");
        map.put("end_hour", "11");

        map.put("productName", "test");
        map.put("day", 1);
        map.put("hour", 2);
        map.put("min", 3);

        map.put("rentNo", "231d");
        map.put("amount", 100);
        map.put("orderNo", "342");
        Set<Map.Entry<String, Object>> sets = map.entrySet();
        for(Map.Entry<String, Object> entry : sets) {
            String regex = "\\$\\{" + entry.getKey() + "\\}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);
            content = matcher.replaceAll(entry.getValue().toString());
        }
        System.out.println(content);
    }

    @Test
    public void convertTest1() {
        String content = "[I'm A Fish Mefi] Dear Customer, your verification code is:  ${code} and please do not share it with others!";
        Map<String, Object> map = new HashMap<>();
        map.put("code", "123");
        Set<Map.Entry<String, Object>> sets = map.entrySet();
        for(Map.Entry<String, Object> entry : sets) {
            String regex = "\\$\\{" + entry.getKey() + "\\}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);
            content = matcher.replaceAll(entry.getValue().toString());
        }
        System.out.println(content);
    }

}

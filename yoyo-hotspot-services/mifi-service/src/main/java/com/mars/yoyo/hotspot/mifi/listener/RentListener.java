package com.mars.yoyo.hotspot.mifi.listener;

import com.mars.yoyo.hotspot.mifi.client.DeviceClient;
import com.mars.yoyo.hotspot.mifi.client.SmsClient;
import com.mars.yoyo.hotspot.mifi.constant.CommonConstant;
import com.mars.yoyo.hotspot.mifi.domain.FlowOrder;
import com.mars.yoyo.hotspot.mifi.domain.Lease;
import com.mars.yoyo.hotspot.mifi.domain.User;
import com.mars.yoyo.hotspot.mifi.dto.LeaseDto;
import com.mars.yoyo.hotspot.mifi.dto.input.SmsInputDto;
import com.mars.yoyo.hotspot.mifi.enums.FlowPackageEnum;
import com.mars.yoyo.hotspot.mifi.enums.ProductTypeEnum;
import com.mars.yoyo.hotspot.mifi.event.RentEvent;
import com.mars.yoyo.hotspot.mifi.event.ReturnEvent;
import com.mars.yoyo.hotspot.mifi.event.SmsEvent;
import com.mars.yoyo.hotspot.mifi.pojo.DeviceFlow;
import com.mars.yoyo.hotspot.mifi.service.FlowOrderService;
import com.mars.yoyo.hotspot.mifi.service.FlowService;
import com.mars.yoyo.hotspot.mifi.service.LeaseService;
import com.mars.yoyo.hotspot.mifi.service.UserService;
import com.mars.yoyo.hotspot.result.RestResult;
import com.mars.yoyo.hotspot.util.DateUtil;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author tookbra
 * @date 2018/6/14
 * @description
 */
@Slf4j
public class RentListener{

    @Autowired
    DeviceClient deviceClient;

    @Autowired
    SmsClient smsClient;

    @Autowired
    UserService userService;

    @Autowired
    LeaseService leaseService;

    @Autowired
    FlowService flowService;

    @Autowired
    FlowOrderService flowOrderService;

    @EventListener(value = RentEvent.class)
    public void rentEvent(RentEvent rentEvent) {
        if (!HystrixRequestContext.isCurrentThreadInitialized()) {
            HystrixRequestContext.initializeContext();
        }
        log.info("receive event={}", rentEvent.toString());
        String deviceId = rentEvent.getDeviceId();
        RestResult restResult = deviceClient.rent(deviceId);
        if(restResult.isSuccess()) {
            log.info("send rent success.deviceId={}, leaseId={}", deviceId, rentEvent.getLeaseId());
//            sendSms(deviceId, rentEvent.getLeaseId());
        }
    }

    public void sendSms(String deviceId, int leaseId) {
        Lease lease = leaseService.findLeaseById(leaseId);
        log.info("receive event leaseId={}", leaseId);
        log.info("receive event lease={}", lease.toString());
        if(lease != null) {
            log.info("begin to send rent sms");
            SmsEvent smsEvent = new SmsEvent();
            smsEvent.setUserId(lease.getUserId());
            smsEvent.setType(lease.getLangEn() ? CommonConstant.SMS_TYPE_LEASE_EN : CommonConstant.SMS_TYPE_LEASE);
            Map<String, Object> paramMap = new HashMap<>(2);
            paramMap.put("wifi", "mefi" + lease.getPowerBankId().substring(lease.getPowerBankId().length() - 4));
            paramMap.put("pwd", lease.getPassowrd());
            smsEvent.setParamMap(paramMap);
            this.sendSmsEvent(smsEvent);
            log.info("end to send rent sms");

            this.enableFlow(lease);
        }
        log.info("send rent request success, deviceId={}", deviceId);
    }

    @Async
    @EventListener(value = SmsEvent.class)
    public void sendSmsEvent(SmsEvent smsEvent) {
        log.info("receive sms event={}", smsEvent.toString());

        User user = userService.getByUserId(smsEvent.getUserId());
        if(user != null && user.getState() == false) {
            SmsInputDto smsInputDto = new SmsInputDto();
            smsInputDto.setPhone("+" + user.getCountryAreaCode() + user.getPhone());
            smsInputDto.setType(smsEvent.getType());
            if(smsEvent.getParamMap() != null) {
                smsInputDto.setParamMap(smsEvent.getParamMap());
            }
            if (!HystrixRequestContext.isCurrentThreadInitialized()) {
                HystrixRequestContext.initializeContext();
            }
            smsClient.sendSms(smsInputDto);
        }
    }

    @Async
    @EventListener(value = ReturnEvent.class)
    public void sendSmsEvent(ReturnEvent returnEvent) {
        log.info("return sms event={}", returnEvent.toString());
        LeaseDto lease = returnEvent.getLease();
        DateTime now = new DateTime();

        User user = userService.getByUserId(lease.getUserId());
        log.info("user lang={}", user.getEn());

        Map<String, Object> paramMap = new HashMap<>(16);
        DateTime rentTime = DateUtil.dateToDateTime(lease.getRentTime());
        paramMap.put("begin_year", rentTime.getYear());
        paramMap.put("begin_month", rentTime.getMonthOfYear());
        paramMap.put("begin_day", rentTime.getDayOfMonth());
        paramMap.put("begin_hour", rentTime.getHourOfDay());

        paramMap.put("end_year", now.getYear());
        paramMap.put("end_month", now.getMonthOfYear());
        paramMap.put("end_day", now.getDayOfMonth());
        paramMap.put("end_hour", now.getHourOfDay());

        paramMap.put("productName",lease.getProductName());
        if(user.getEn()) {
            paramMap.put("end_month", now.monthOfYear().getAsShortText(Locale.ENGLISH));
            paramMap.put("productName", ProductTypeEnum.getMsgEn(lease.getProductType()).getMsgEn());
        }

        Map<String, Long> diff = DateUtil.getDiff(now.toDate(),lease.getRentTime());
        if(diff == null) {
            paramMap.put("day", 0);
            paramMap.put("hour", 0);
            paramMap.put("min", 0);
        } else {
            paramMap.put("day", diff.get("day"));
            paramMap.put("hour", diff.get("hour"));
            paramMap.put("min", diff.get("min"));
        }
        paramMap.put("rentNo", lease.getLeaseNo());
        if(lease.getAmount() != null) {
            paramMap.put("amount", lease.getAmount().setScale(2, RoundingMode.HALF_UP));
        }

        paramMap.put("orderNo", lease.getOrderNo());

        SmsEvent smsEvent = new SmsEvent();
        smsEvent.setUserId(lease.getUserId());
        if(lease.getProductType() == CommonConstant.PRODUCT_TYPE_HOUR) {
            smsEvent.setType(user.getEn() ? CommonConstant.SMS_TYPE_RETURN_HOUR_EN : CommonConstant.SMS_TYPE_RETURN_HOUR);
        } else {
            if(lease.getExpectedReturnTime().before(now.toDate())) {
                smsEvent.setType(user.getEn() ? CommonConstant.SMS_TYPE_RETURN_TIME_OUT_EN : CommonConstant.SMS_TYPE_RETURN_TIME_OUT);
            } else {
                smsEvent.setType(user.getEn() ? CommonConstant.SMS_TYPE_RETURN_NOT_TIME_OUT_EN : CommonConstant.SMS_TYPE_RETURN_NOT_TIME_OUT);
            }
        }
        log.info("return device item true; deviceId={}, powerBankId={}", lease.getDeviceId(), lease.getPowerBankId());
        log.info("return device item param, {}", paramMap);
        smsEvent.setParamMap(paramMap);
        this.disableFlow(lease);
        this.sendSmsEvent(smsEvent);
    }

    private void enableFlow(Lease lease) {
        String imei = lease.getPowerBankId().substring(1, lease.getPowerBankId().length());
        FlowOrder flowOrder = new FlowOrder();
        flowOrder.setPwd(lease.getPassowrd());
        flowOrder.setImei(imei);
        flowOrder.setTransId(String.valueOf(System.currentTimeMillis()));
        flowOrder.setLeaseId(lease.getId());
        String pkgId = flowOrderService.convertPkgId(lease);
        flowOrder.setPkgTypeId(pkgId);
        flowOrderService.enableFlow(flowOrder);
    }

    private void disableFlow(LeaseDto leaseDto) {
        List<FlowOrder> flowOrders = flowOrderService.findByImei(leaseDto.getPowerBankId().substring(1));
        flowOrders.forEach(flowOrder -> {
            flowOrderService.closeFlow(flowOrder);
        });

    }
}

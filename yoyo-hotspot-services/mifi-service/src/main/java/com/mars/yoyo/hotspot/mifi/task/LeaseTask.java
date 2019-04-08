package com.mars.yoyo.hotspot.mifi.task;

import com.mars.yoyo.hotspot.mifi.constant.CommonConstant;
import com.mars.yoyo.hotspot.mifi.domain.Lease;
import com.mars.yoyo.hotspot.mifi.domain.User;
import com.mars.yoyo.hotspot.mifi.enums.ProductTypeEnum;
import com.mars.yoyo.hotspot.mifi.event.SmsEvent;
import com.mars.yoyo.hotspot.mifi.service.LeaseService;
import com.mars.yoyo.hotspot.mifi.service.ProductService;
import com.mars.yoyo.hotspot.mifi.service.UserService;
import com.mars.yoyo.hotspot.util.DateUtil;
import com.mars.yoyo.hotspot.util.SeqUtil;
import com.mars.yoyo.hotspot.util.TransferUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author tookbra
 * @date 2018/7/26
 * @description
 */
@Slf4j
@Component
public class LeaseTask {

    @Autowired
    LeaseService leaseService;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Scheduled(cron="0 0/5 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void noticeExpirationReminder() {
        log.info("begin to scan lease");
        List<Lease> list = leaseService.findLease();
        for(Lease t : list) {
            DateTime now = new DateTime();
            User user = userService.getByUserId(t.getUserId());
            if(user == null) {
                continue;
            }
            if(t.getProductType() == CommonConstant.PRODUCT_TYPE_HOUR) {
                BigDecimal amount = leaseService.calculatedAmount(user.getEn(), t.getProductType(),
                        t.getRentTime(), DateTime.now().toDate());
                if(amount.intValue() > 30 && !t.getSmsCap()) {
                    // 小时封顶提醒
                    SmsEvent smsEvent = new SmsEvent();
                    smsEvent.setUserId(t.getUserId());
                    smsEvent.setType(user.getEn() ? CommonConstant.SMS_TYPE_RENT_ARREARS_EN : CommonConstant.SMS_TYPE_RENT_ARREARS);
                    applicationContext.publishEvent(smsEvent);

                    t.setSmsCap(true);
                    t.setModifyTime(DateTime.now().toDate());
                    leaseService.saveLease(t);
                }
            } else {
                DateTime time = DateUtil.dateToDateTime(t.getExpectedReturnTime());
                Map<String, Object> paramMap = new HashMap<>(16);
                paramMap.put("productName", t.getProductName());
                paramMap.put("month", time.getMonthOfYear());
                paramMap.put("day", time.getDayOfMonth());
                paramMap.put("hour", time.getHourOfDay());

                if(user.getEn()) {
                    paramMap.put("month", now.monthOfYear().getAsShortText(Locale.ENGLISH));
                    paramMap.put("productName", ProductTypeEnum.getMsgEn(t.getProductType()).getMsgEn());
                }

                SmsEvent smsEvent = new SmsEvent();
                smsEvent.setUserId(t.getUserId());
                smsEvent.setParamMap(paramMap);
                if(!t.getSmsExpiring() && DateUtil.getIntervalHour(now.toDate(), t.getExpectedReturnTime()) == 1) {
                    // 最后一小时，短信提示快到期
                    smsEvent.setType(user.getEn() ? CommonConstant.SMS_TYPE_RENT_EXPIRING_EN : CommonConstant.SMS_TYPE_RENT_EXPIRING);
                    applicationContext.publishEvent(smsEvent);

                    t.setSmsExpiring(true);
                    t.setModifyTime(DateTime.now().toDate());
                    leaseService.saveLease(t);
                } else if (!t.getExpiredSms() && now.toDate().after(t.getExpectedReturnTime())) {
                    // 到期提醒
                    smsEvent.setType(user.getEn() ? CommonConstant.SMS_TYPE_RENT_MATURITY_EN : CommonConstant.SMS_TYPE_RENT_MATURITY);
                    applicationContext.publishEvent(smsEvent);

                    if(t.getProductType() == CommonConstant.PRODUCT_TYPE_DAY
                            || t.getProductType() == CommonConstant.PRODUCT_TYPE_MONTH) {
                        //包天、月到期自动转成小时
                        Lease lease = new Lease();
                        TransferUtil.transfer(t, lease);
                        lease.setId(null);
                        lease.setRentTime(DateTime.now().toDate());
                        lease.setExpectedReturnTime(null);
                        lease.setReturnTime(null);
                        lease.setLastLeaseNo(t.getLeaseNo());
                        String orderNo = SeqUtil.generatorId(t.getUserId());
                        lease.setOrderNo(orderNo);
                        lease.setLeaseNo(orderNo);
                        lease.setReturned(false);
                        lease.setProductId(1);
                        lease.setSmsExpiring(false);
                        lease.setProductType(CommonConstant.PRODUCT_TYPE_HOUR);
                        if(user.getEn()) {
                            lease.setProductName("Hourly Package");
                        } else {
                            lease.setProductName("小时借");
                        }
                        t.setOvered(true);
                        t.setReturned(true);
                        leaseService.addLease(lease);
                    }

                    t.setExpiredSms(true);
                    t.setModifyTime(DateTime.now().toDate());
                    leaseService.saveLease(t);
                }
            }
        }
        log.info("end to scan lease");
    }
}

package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.BaseTest;
import com.mars.yoyo.hotspot.mifi.dao.LeaseMapper;
import com.mars.yoyo.hotspot.mifi.domain.Lease;
import com.mars.yoyo.hotspot.mifi.dto.LeaseDto;
import com.mars.yoyo.hotspot.mifi.event.ReturnEvent;
import com.mars.yoyo.hotspot.util.StringUtil;
import org.assertj.core.util.Lists;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Assert;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author tookbra
 * @date 2018/7/28
 * @description
 */
public class LeaseServiceTest extends BaseTest {

    @Autowired
    LeaseService leaseService;

    @Resource
    LeaseMapper leaseMapper;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void returnDeviceTest() throws IOException {
        leaseService.returnDevice("RL2A10180400111", "5930170066308965200");
        System.in.read();
    }

    @Test
    public void test() {
        Example example = new Example(Lease.class);
        example.createCriteria().andEqualTo("powerBankId", "123")
                .andIn("state", Lists.newArrayList(0,2));
        leaseMapper.selectOneByExample(example);
    }

    @Test
    public void copy() {
        Lease lease = new Lease();
        lease.setDeviceId("test");
        LeaseDto leaseDto = new LeaseDto();
        BeanUtils.copyProperties(lease, leaseDto);
        ReturnEvent returnEvent = new ReturnEvent(this, leaseDto);
        applicationContext.publishEvent(returnEvent);
    }

    @Test
    public void addLease() {
        Lease lease = new Lease();
        lease.setUserId(1);
        lease.setDeviceId("1");
        lease.setPowerBankId("2");
        lease.setProductId(1);
        lease.setProductType(1);
        lease.setProductName("2");
        lease.setPassowrd("22");
        lease.setLeaseNo("33");
        lease.setRentTime(new Date());
        lease.setReturned(false);
        lease.setAddress("33");
        lease.setLangEn(false);
        lease.setOvered(false);
        lease.setState(1);
        lease.setExpiredSms(false);
        lease.setSmsExpiring(false);
        lease.setSmsCap(false);
        lease.setSlot(0);
        leaseService.addLease(lease);
        System.out.println("========================"+ lease.getId());
    }

    @Test
    public void findNotOver() {
        List<Lease> leases = leaseService.findNotOver();
        Assert.notEmpty(leases, "");
    }

    @Test
    public void leaseTest() {
        Lease lease = new Lease();
        lease.setUserId(1);
        lease.setDeviceId("tee");
        lease.setProductId(2);
        lease.setProductType(3);
        lease.setLeaseNo("33");
        lease.setOrderNo("333");
        lease.setProductName("22");
        lease.setRentTime(DateTime.now().toDate());
        lease.setAddress("");

        lease.setPassowrd(StringUtil.generateRandomCode(8));
        lease.setPowerBankId("tee");
        leaseService.addLease(lease);
        System.out.println("===================" + lease.getId());
    }
}

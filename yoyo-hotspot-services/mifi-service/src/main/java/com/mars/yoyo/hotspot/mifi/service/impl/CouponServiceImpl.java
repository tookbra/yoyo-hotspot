package com.mars.yoyo.hotspot.mifi.service.impl;

import com.mars.yoyo.hotspot.exception.BizFeignException;
import com.mars.yoyo.hotspot.mifi.dao.CouponMapper;
import com.mars.yoyo.hotspot.mifi.dao.UserCouponMapper;
import com.mars.yoyo.hotspot.mifi.domain.Coupon;
import com.mars.yoyo.hotspot.mifi.domain.UserAccount;
import com.mars.yoyo.hotspot.mifi.domain.UserCoupon;
import com.mars.yoyo.hotspot.mifi.pojo.UserCouponPojo;
import com.mars.yoyo.hotspot.mifi.service.CouponService;
import com.mars.yoyo.hotspot.mifi.service.UserAccountService;
import com.mars.yoyo.hotspot.mifi.vo.CouponVo;
import com.mars.yoyo.hotspot.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tookbra
 * @date 2018/5/17
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    UserCouponMapper userCouponMapper;

    @Autowired
    UserAccountService userAccountService;

    @Override
    public Coupon getByChannel(int channelId, int type) {
        Example example = new Example(Coupon.class);
        example.createCriteria().andEqualTo("status", 1)
                .andEqualTo("channel", channelId).andEqualTo("conditions", type);
        Coupon coupon = couponMapper.selectOneByExample(example);
        return coupon;
    }

    @Override
    public Coupon getByChannel(int type) {
        Example example = new Example(Coupon.class);
        example.createCriteria().andEqualTo("status", 1)
                .andEqualTo("conditions", type);
        Coupon coupon = couponMapper.selectOneByExample(example);
        return coupon;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCoupon(UserCoupon userCoupon) {
        userCouponMapper.insertSelective(userCoupon);
    }

    @Override
    public void saveUserCoupon(UserCoupon userCoupon) {
        userCouponMapper.updateByPrimaryKeySelective(userCoupon);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void usedCoupon(int userCouponId) {
        UserCoupon userCoupon = this.findByUserCouponId(userCouponId);
        if(userCoupon == null) {
            throw new BizFeignException("biz.rent.coupon.not.found");
        }
        Date now = DateTime.now().toDate();
        userCoupon.setState(true);
        userCoupon.setUseTime(now);
        userCoupon.setMofidyTime(now);
        userCouponMapper.updateByPrimaryKeySelective(userCoupon);

        UserAccount userAccount = userAccountService.getUser(userCoupon.getUserId());
        userAccount.setCouponNum(userAccount.getCouponNum() - 1);
        userAccountService.saveAccount(userAccount);
    }

    @Override
    public UserCoupon findByUserCouponId(int userCouponId) {
        Example example = new Example(UserCoupon.class);
        example.createCriteria().andEqualTo("id", userCouponId);
        UserCoupon userCoupon = userCouponMapper.selectOneByExample(example);
        return userCoupon;
    }

    @Override
    public List<CouponVo> getEnabledByUserId(int userId) {
        List<CouponVo> couponVoList = new ArrayList<>();

        List<UserCouponPojo> list = userCouponMapper.selectEnableByUserId(userId);

        for(UserCouponPojo userCoupon : list) {
            CouponVo couponVo = new CouponVo();
            if(userCoupon != null) {
                couponVo.setId(userCoupon.getCouponId());
                if(userCoupon.getLangEn()) {
                    couponVo.setMoney(userCoupon.getMoney());
                } else {
                    couponVo.setMoney(userCoupon.getMoney());
                }
                couponVo.setEndTime(userCoupon.getEndDate());
                if (userCoupon.getStatus() == 1 && userCoupon.getEndDate().before(new Date())) {
                    couponVo.setExpired(true);
                } else {
                    couponVo.setExpired(false);
                }
                couponVoList.add(couponVo);
            }
        }
        return couponVoList;
    }

    @Override
    public UserCouponPojo getByUserCouponId(int userId, int userCouponId) {
        UserCouponPojo userCouponPojo = userCouponMapper.selectByUserCouponId(userId, userCouponId);
        return userCouponPojo;
    }

    @Override
    public List<UserCoupon> findByUserId(int userId, int couponId) {
        Example example = new Example(UserCoupon.class);
        example.createCriteria().andEqualTo("userId", userId);
        List<UserCoupon> list = userCouponMapper.selectByExample(example);
        return list;
    }


}

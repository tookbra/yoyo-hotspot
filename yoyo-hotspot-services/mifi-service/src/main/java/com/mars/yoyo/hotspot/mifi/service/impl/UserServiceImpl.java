package com.mars.yoyo.hotspot.mifi.service.impl;

import com.mars.yoyo.hotspot.exception.BizFeignException;
import com.mars.yoyo.hotspot.mifi.cache.SmsCaptchaCache;
import com.mars.yoyo.hotspot.mifi.dao.UserMapper;
import com.mars.yoyo.hotspot.mifi.domain.Coupon;
import com.mars.yoyo.hotspot.mifi.domain.User;
import com.mars.yoyo.hotspot.mifi.domain.UserAccount;
import com.mars.yoyo.hotspot.mifi.domain.UserCoupon;
import com.mars.yoyo.hotspot.mifi.dto.output.UserOutputDto;
import com.mars.yoyo.hotspot.mifi.service.CouponService;
import com.mars.yoyo.hotspot.mifi.service.UserAccountService;
import com.mars.yoyo.hotspot.mifi.service.UserService;
import com.mars.yoyo.hotspot.util.StringUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import com.mars.yoyo.hotspot.mifi.constant.CommonConstant;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    SmsCaptchaCache smsCaptchaCache;

    @Autowired
    CouponService couponService;

    @Override
    public User getByUserId(int userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user;
    }

    @Override
    public User getByPhone(String phone) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("phone", phone);
        User user = userMapper.selectOneByExample(example);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public User addUser(User user) {
        userMapper.insertSelective(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User saveUser(User user) {
        user.setModifyTime(DateTime.now().toDate());
        userMapper.updateByPrimaryKeySelective(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BigDecimal bindUser(String phone, String code) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("phone", phone).andEqualTo("certified", 1);
        User user = userMapper.selectOneByExample(example);
        if(user != null) {
            throw new BizFeignException("手机号已被使用");
        }

        String captcha = smsCaptchaCache.getCaptcha("REG", phone);
        if(StringUtil.isBlank(captcha)) {
            throw new BizFeignException("验证码不存在或已失效，请重新请求发送");
        }
        if (!Objects.equals(captcha, code)) {
            throw new BizFeignException("短信验证码不匹配");
        }

        user.setPhone(phone);
        user.setCertified(true);
        user.setModifyTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);

        //是否送优惠券 酒店没，餐饮有
        //从二维码信息中获取扫码的信息渠道
        String channel = "";
//        Coupon coupon = couponService.getByChannel(channel, CommonConstant.COUPON_BIND_PHONE_CHANNEL);
//        if(coupon != null) {
//            UserCoupon userCoupon = new UserCoupon();
//            userCoupon.setCouponId(coupon.getId());
//            userCoupon.setUserId(0);
//            return coupon.getMoney();
//        }

        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(user.getId());
        userAccount.setBalance(BigDecimal.ZERO);
        userAccountService.addAccount(userAccount);

        return BigDecimal.ZERO;
    }

    @Override
    public void bindWx(int userId, String code) {

    }
}

package com.mars.yoyo.hotspot.mifi.service.impl;

import com.mars.yoyo.hotspot.exception.BizFeignException;
import com.mars.yoyo.hotspot.mifi.cache.DeliveryChannelCache;
import com.mars.yoyo.hotspot.mifi.cache.SmsCaptchaCache;
import com.mars.yoyo.hotspot.mifi.cache.WechatCache;
import com.mars.yoyo.hotspot.mifi.client.AuthClient;
import com.mars.yoyo.hotspot.mifi.constant.CommonConstant;
import com.mars.yoyo.hotspot.mifi.dao.UserAuthMapper;
import com.mars.yoyo.hotspot.mifi.domain.*;
import com.mars.yoyo.hotspot.mifi.dto.AppWxLoginDto;
import com.mars.yoyo.hotspot.mifi.dto.input.LoginInputDto;
import com.mars.yoyo.hotspot.mifi.dto.output.UserOutputDto;
import com.mars.yoyo.hotspot.mifi.enums.AuthTypeEnum;
import com.mars.yoyo.hotspot.mifi.service.*;
import com.mars.yoyo.hotspot.result.RestResult;
import com.mars.yoyo.hotspot.util.NumberUtil;
import com.mars.yoyo.hotspot.util.StringUtil;
import com.vdurmont.emoji.EmojiParser;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author tookbra
 * @date 2018/5/24
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    UserAuthMapper userAuthMapper;

    @Autowired
    UserService userService;

    @Autowired
    SmsCaptchaCache smsCaptchaCache;

    @Autowired
    DeliveryChannelCache deliveryChannelCache;

    @Autowired
    CouponService couponService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    DeliveryChannelService deliveryChannelService;

    @Autowired
    WechatCache wechatCache;

    @Autowired
    AuthClient authClient;

    @Value("${wechat.url}")
    String couponUrl;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public UserOutputDto validate(LoginInputDto loginInputDto) {
        log.info("loginInputDto={}", loginInputDto.toString());
        UserOutputDto userOutputDto = new UserOutputDto();
        String smsCode = "";
        if(loginInputDto.getLang().equals(CommonConstant.LANG_EN)) {
            smsCode = smsCaptchaCache.getCaptcha("REG_EN", loginInputDto.getUsername());
        } else {
            smsCode = smsCaptchaCache.getCaptcha("REG", loginInputDto.getUsername());
        }

        Optional.ofNullable(smsCode).orElseThrow(() -> new BizFeignException("biz.sms.captcha.not.blank"));
        if (!Objects.equals(loginInputDto.getCaptcha(), smsCode)) {
            throw new BizFeignException("biz.sms.captcha.not.match");
        }

        //登陆
        User user = this.login(loginInputDto.getRequestIp(), loginInputDto.getUsername(),
                loginInputDto.getCountryAreaCode(), loginInputDto.getCode(), loginInputDto.getLang());

        //是否送优惠券 酒店没，餐饮有
        //从二维码信息中获取扫码的信息渠道
        DeliveryChannel deliveryChannel = null;
        if(StringUtils.isNotBlank(loginInputDto.getDeliveryChannel())) {
            Device device = deviceService.getByToken(loginInputDto.getDeliveryChannel());
            if(device != null) {
                deliveryChannel = deliveryChannelService.findById(device.getDeliveryChannel());
                if (deliveryChannel == null) {
                    throw new BizFeignException("biz.delivery.channel");
                }
            }
        }

//        DeliveryChannel deliveryChannel = Optional.ofNullable(deliveryChannelCache.get(loginInputDto.getDeliveryChannel()))
//                .orElseThrow(() -> new BizFeignException("biz.delivery.channel"));
//        if(deliveryChannel != null && deliveryChannel.getActivityed()) {
//            Coupon coupon = couponService.getByChannel(deliveryChannel.getId(), CommonConstant.COUPON_BIND_PHONE_CHANNEL);
//            if (coupon != null) {
//                UserCoupon userCoupon = new UserCoupon();
//                List<UserCoupon> userCouponList = couponService.findByUserId(user.getId(), coupon.getId());
//                if(CollectionUtils.isEmpty(userCouponList)) {
//                    userCoupon.setCouponId(coupon.getId());
//                    userCoupon.setUserId(user.getId());
//                    couponService.addCoupon(userCoupon);
//                    userOutputDto.setCouponed(true);
//                    userOutputDto.setCouponMoney(coupon.getMoney());
//
//                    UserAccount userAccount = userAccountService.getUser(user.getId());
//                    userAccount.setCouponNum(userAccount.getCouponNum() + 1);
//                    userAccountService.saveAccount(userAccount);
//                }
//            }
//        }

        Coupon coupon = couponService.getByChannel(CommonConstant.COUPON_BIND_PHONE_CHANNEL);
        if (coupon != null) {
            UserCoupon userCoupon = new UserCoupon();
            List<UserCoupon> userCouponList = couponService.findByUserId(user.getId(), coupon.getId());
            if(CollectionUtils.isEmpty(userCouponList)) {
                userCoupon.setCouponId(coupon.getId());
                userCoupon.setUserId(user.getId());
                couponService.addCoupon(userCoupon);
                userOutputDto.setCouponed(true);
                userOutputDto.setCouponMoney(coupon.getMoney());

                UserAccount userAccount = userAccountService.getUser(user.getId());
                userAccount.setCouponNum(userAccount.getCouponNum() + 1);
                userAccountService.saveAccount(userAccount);
            }
        }

        if(user.getDeposited()) {
            userOutputDto.setDeposit(true);
        }
        userOutputDto.setUsername(user.getName());
        userOutputDto.setId(user.getId());
        userOutputDto.setEn(user.getEn());
        userOutputDto.setCouponUrl(couponUrl + "#/my/coupons");

        smsCaptchaCache.removeCaptcha("REG:" + loginInputDto.getUsername());
        return userOutputDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public UserOutputDto login(LoginInputDto loginInputDto) {
        UserOutputDto userOutputDto = new UserOutputDto();
        String smsCode = smsCaptchaCache.getCaptcha("REG", loginInputDto.getUsername());
        Optional.ofNullable(smsCode).orElseThrow(() -> new BizFeignException("biz.sms.captcha.not.blank"));
        if (!Objects.equals(loginInputDto.getCaptcha(), smsCode)) {
            throw new BizFeignException("biz.sms.captcha.not.match");
        }
        //登陆
        User user = this.login(loginInputDto.getRequestIp(), loginInputDto.getUsername(),
                loginInputDto.getCountryAreaCode(), loginInputDto.getCode(), loginInputDto.getLang());
        if(user == null) {
            throw new BizFeignException("");
        } else if(user.getState() == true) {
            throw new BizFeignException("biz.user.disabled");
        }
        return userOutputDto;
    }

    @Override
    public UserAuth findByUserId(int userId, String type) {
        Example example = new Example(UserAuth.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("authType", type);
        UserAuth userAuth = userAuthMapper.selectOneByExample(example);
        return userAuth;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindWx(int userId, String code) {
        if(StringUtils.isNotBlank(code)) {
            WxMpUser wxMpUser = wechatCache.getUser(code);
            if(wxMpUser != null) {
                UserAuth userAuth = this.findByUserId(userId, "wechat");
                if (userAuth == null) {
                    userAuth = new UserAuth();
                    userAuth.setUserId(userId);
                    userAuth.setAuthType("wechat");
                    userAuth.setOpenId(wxMpUser.getOpenId());
                    userAuth.setSecret(wxMpUser.getUnionId());
                    userAuthMapper.insertSelective(userAuth);
                } else {
                    if(!userAuth.getSecret().equals(wxMpUser.getUnionId())) {
                        throw new BizFeignException("biz.user.binded");
                    }
                }
                User user = userService.getByUserId(userId);
                if(user != null) {
                    user.setName(EmojiParser.parseToAliases(wxMpUser.getNickname()));
                    user.setHeadimgurl(wxMpUser.getHeadImgUrl());
                    userService.saveUser(user);
                }
//                wechatCache.deleteToken(code);
            }
        }
    }

    @Override
    public String wxLogin(String unionId) {
        Example example = new Example(UserAuth.class);
        example.createCriteria().andEqualTo("secret", unionId);
        UserAuth userAuth = userAuthMapper.selectOneByExample(example);
        if(userAuth != null) {
            User user = userService.getByUserId(userAuth.getUserId());
            if(user != null && !user.getState()) {
                RestResult restResult = authClient.wxLogin(user.getName(), user.getId());
                if(restResult.success) {
                    return restResult.getMsg();
                }
            }
        }
        return "";
    }

    @Override
    public AppWxLoginDto wxLoginByUnionId(String unionId) {
        AppWxLoginDto appWxLoginDto = new AppWxLoginDto();
        Example example = new Example(UserAuth.class);
        example.createCriteria().andEqualTo("secret", unionId);
        UserAuth userAuth = userAuthMapper.selectOneByExample(example);
        if(userAuth != null) {
            User user = userService.getByUserId(userAuth.getUserId());
            if(user != null && !user.getState()) {
                appWxLoginDto.setUserId(user.getId());
                appWxLoginDto.setPhone(user.getPhone());
                RestResult restResult = authClient.wxLogin(user.getName(), user.getId());
                if(restResult.success) {
                    appWxLoginDto.setToken(restResult.getMsg());
                }
            }
        }
        return appWxLoginDto;
    }

    @Override
    public String validate(String unionId, String token) {
        Example example = new Example(UserAuth.class);
        example.createCriteria().andEqualTo("secret", unionId);
        UserAuth userAuth = userAuthMapper.selectOneByExample(example);
        if(userAuth != null) {
            User user = userService.getByUserId(userAuth.getUserId());
            if(user != null && !user.getState()) {
                RestResult restResult = authClient.wxValidate(user.getName(), user.getId(), token);
                if(restResult.success) {
                    return restResult.getMsg();
                }
            }
        }
        throw new BizFeignException("");
    }

    /**
     *
     * @param requestIp 请求ip
     * @param loginName 手机号
     * @param countryAreaCode 国家编码
     * @param code 微信码
     * @param lang 语言
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public User login(String requestIp, String loginName, String countryAreaCode, String code, String lang) {
        User user = userService.getByPhone(loginName);
        if(user == null) {
            user = new User();
            user.setPhone(loginName);
            user.setDeposited(false);
            user.setCertified(true);
            Date now = new Date();
            user.setName(loginName);
            user.setRegisterTime(now);
            user.setLastLoginIp(requestIp);
            user.setRegisterIp(requestIp);
            user.setCountryAreaCode(countryAreaCode);
            if(lang.equals(CommonConstant.LANG_CH)) {
                user.setEn(false);
            } else {
                user.setEn(true);
            }
            userService.addUser(user);

            UserAccount userAccount = new UserAccount();
            userAccount.setUserId(user.getId());
            userAccount.setBalance(BigDecimal.ZERO);
            userAccountService.addAccount(userAccount);
        } else  {
            if(user.getState() == true) {
                throw new BizFeignException("biz.user.disabled");
            }
            user.setLastLoginIp(requestIp);
            user.setModifyTime(DateTime.now().toDate());
            userService.saveUser(user);
        }

        if(StringUtils.isNotBlank(code)) {
            this.bindWx(user.getId(), code);
        }
        return user;
    }


}

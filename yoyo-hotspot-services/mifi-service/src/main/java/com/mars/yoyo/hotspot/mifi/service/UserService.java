package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.domain.User;
import com.mars.yoyo.hotspot.mifi.dto.output.UserOutputDto;

import java.math.BigDecimal;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
public interface UserService {

    /**
     * 获取用户信息
     * @param userId 用户id
     * @return {@link User}
     */
    User getByUserId(int userId);

    /**
     * 获取用户信息
     * @param phone 手机号码
     * @return {@link User}
     */
    User getByPhone(String phone);

    /**
     * 增加用户
     * @param user
     * @return
     */
    User addUser(User user);

    /**
     * 保存用户
     * @param user
     * @return
     */
    User saveUser(User user);

    /**
     * 绑定手机
     * @param phone 手机号码
     * @param code 短信验证码
     * @return 返回优惠券金额
     */
    BigDecimal bindUser(String phone, String code);

    /**
     * 绑定微信
     * @param userId 用户id
     * @param code 微信code
     */
    void bindWx(int userId, String code);


}

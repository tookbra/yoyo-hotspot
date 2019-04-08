package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.domain.User;
import com.mars.yoyo.hotspot.mifi.domain.UserAuth;
import com.mars.yoyo.hotspot.mifi.dto.AppWxLoginDto;
import com.mars.yoyo.hotspot.mifi.dto.input.LoginInputDto;
import com.mars.yoyo.hotspot.mifi.dto.output.UserOutputDto;

/**
 * @author tookbra
 * @date 2018/5/24
 * @description
 */
public interface UserAuthService {

    /**
     * 验证用户名密码
     * @return
     */
    UserOutputDto validate(LoginInputDto loginInputDto);

    /**
     * 用户登陆
     * @param loginInputDto
     * @return
     */
    UserOutputDto login(LoginInputDto loginInputDto);

    /**
     * 查询
     * @param userId
     * @param type
     * @return
     */
    UserAuth findByUserId(int userId, String type);

    /**
     * 绑定微信
     * @param userId 用户id
     * @param code 微信code
     */
    void bindWx(int userId, String code);

    /**
     * 微信登陆
     * @param unionId
     * @return
     */
    String wxLogin(String unionId);

    /**
     * 微信登陆
     * @param unionId
     * @return
     */
    AppWxLoginDto wxLoginByUnionId(String unionId);

    /**
     * 微信验证token
     * @param unionId
     * @param token
     * @return
     */
    String validate(String unionId, String token);
}

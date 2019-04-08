package com.mars.yoyo.hotspot.auth.service;

import com.mars.yoyo.hotspot.auth.domain.UserAuth;
import com.mars.yoyo.hotspot.auth.dto.input.LoginInputDto;
import com.mars.yoyo.hotspot.auth.util.jwt.JWTInfo;
import com.mars.yoyo.hotspot.auth.vo.LoginVo;

/**
 * @author tookbra
 * @date 2018/5/24
 * @description
 */
public interface AuthService {

    /**
     * 登陆
     * @param loginInputDto
     * @return
     */
    LoginVo login(LoginInputDto loginInputDto) throws Exception;

    /**
     * 绑定
     * @param loginInputDto
     * @return
     */
    LoginVo bind(LoginInputDto loginInputDto) throws Exception;

    /**
     * 验证
     * @param token
     * @throws Exception
     */
    UserAuth validate(String token) throws Exception;

    String wxLogin(String name, Integer id) throws Exception;

    String validateWx(String name, Integer userId, String token) throws Exception;
}

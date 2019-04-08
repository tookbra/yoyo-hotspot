package com.mars.yoyo.hotspot.auth.service.impl;

import com.mars.yoyo.hotspot.auth.cache.AuthTokenCache;
import com.mars.yoyo.hotspot.auth.client.UserClient;
import com.mars.yoyo.hotspot.auth.domain.UserAuth;
import com.mars.yoyo.hotspot.auth.dto.input.LoginInputDto;
import com.mars.yoyo.hotspot.auth.dto.output.UserOutputDto;
import com.mars.yoyo.hotspot.auth.service.AuthService;
import com.mars.yoyo.hotspot.auth.util.jwt.IJWTInfo;
import com.mars.yoyo.hotspot.auth.util.jwt.JWTHelper;
import com.mars.yoyo.hotspot.auth.util.jwt.JWTInfo;
import com.mars.yoyo.hotspot.auth.util.jwt.JwtTokenUtil;
import com.mars.yoyo.hotspot.auth.vo.LoginVo;
import com.mars.yoyo.hotspot.exception.UserInvalidException;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author tookbra
 * @date 2018/5/24
 * @description
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserClient userClient;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    AuthTokenCache authTokenCache;

    @Override
    public LoginVo login(LoginInputDto loginInputDto) throws Exception {
        RestResult<UserOutputDto> restResult = userClient.login(loginInputDto);
        LoginVo loginVo = convertLoginVo(restResult);
        return loginVo;
    }

    @Override
    public LoginVo bind(LoginInputDto loginInputDto) throws Exception {
        RestResult<UserOutputDto> restResult = userClient.validate(loginInputDto);
        LoginVo loginVo = convertLoginVo(restResult);
        return loginVo;
    }

    @Override
    public UserAuth validate(String token) throws Exception {
        IJWTInfo jwtInfo = jwtTokenUtil.getInfoFromToken(token);
        if(jwtInfo == null) {
            log.info("jwtInfo is null. token={}", token);
            return null;
        }
        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(jwtInfo.getId());
        userAuth = authTokenCache.getUserToken(userAuth);
        if(userAuth == null) {
            log.info("user auth is null. token={}", token);
            return null;
        }
        if(!Objects.equals(userAuth.getSecret(), token)) {
            log.info("cache user token not equals token. userToken={}, token={}", userAuth.getSecret(), token);
            return null;
        }
        return userAuth;
    }


    @Override
    public String wxLogin(String name, Integer id) throws Exception {
        UserAuth userAuth = authTokenCache.getUserToken(id.toString());
        if(userAuth != null) {
            return userAuth.getSecret();
        }
        String token = jwtTokenUtil.generateToken(new JWTInfo(name, id + "", ""));
        userAuth = new UserAuth();
        userAuth.setUserId(id.toString());
        userAuth.setSecret(token);
        authTokenCache.putUserToken(userAuth);
        return token;
    }

    @Override
    public String validateWx(String name, Integer userId, String token) throws Exception {
        log.info("name={}, userId={}, token={}", name, userId, token);
        UserAuth userAuth = authTokenCache.getUserToken(userId.toString());
        if(userAuth != null) {
            if (userAuth.getSecret().equals(token)) {
                return userAuth.getSecret();
            } else {
                token = jwtTokenUtil.generateToken(new JWTInfo(name, userId + "", ""));
                userAuth = new UserAuth();
                userAuth.setUserId(userId.toString());
                userAuth.setSecret(token);
                authTokenCache.putUserToken(userAuth);
                return token;
            }
        }
        return "";
    }

    private LoginVo convertLoginVo(RestResult<UserOutputDto> restResult) throws Exception {
        UserOutputDto userOutputDto = restResult.getData();
        if(restResult.isSuccess() && userOutputDto.getId() != null) {
            LoginVo loginVo = new LoginVo();
            String token = jwtTokenUtil.generateToken(new JWTInfo(userOutputDto.getUsername(), userOutputDto.getId() + "", ""));
            loginVo.setToken(token);
            loginVo.setMoney(userOutputDto.getCouponMoney());
            loginVo.setCouponed(userOutputDto.isCouponed());
            loginVo.setDeposit(userOutputDto.isDeposit());
            loginVo.setEn(userOutputDto.isEn());
            loginVo.setCouponUrl(userOutputDto.getCouponUrl());
            loginVo.setUserId(userOutputDto.getId());
            UserAuth userAuth = new UserAuth();
            userAuth.setUserId(userOutputDto.getId().toString());
            userAuth.setSecret(token);
            authTokenCache.putUserToken(userAuth);
            return loginVo;
        }
        throw new UserInvalidException("biz.user.not.found");
    }
}

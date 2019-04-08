package com.mars.yoyo.hotspot.auth.controller;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.mars.yoyo.hotspot.auth.domain.UserAuth;
import com.mars.yoyo.hotspot.auth.dto.input.LoginInputDto;
import com.mars.yoyo.hotspot.auth.service.AuthService;
import com.mars.yoyo.hotspot.auth.vo.LoginAppVo;
import com.mars.yoyo.hotspot.auth.vo.LoginVo;
import com.mars.yoyo.hotspot.exception.BizFeignException;
import com.mars.yoyo.hotspot.result.RestResult;
import com.mars.yoyo.hotspot.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author tookbra
 * @date 2018/5/24
 * @description
 */
@Slf4j
@RestController
public class TokenController {

    @Autowired
    AuthService authService;


    /**
     * 登陆
     * @param loginInputDto
     * @param result
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public RestResult login(@Valid LoginInputDto loginInputDto,
                            BindingResult result, HttpServletRequest request) throws Exception {
        String requestIp = WebUtil.getClientIP(request);
        loginInputDto.setRequestIp(requestIp);

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber number = phoneUtil.parseAndKeepRawInput("+86" + loginInputDto.getUsername(), "");
        boolean isNumberValid = phoneUtil.isValidNumber(number);
        if(!isNumberValid) {
            throw new BizFeignException("phone.Illegal.error");
        }
        loginInputDto.setUsername(String.valueOf(number.getNationalNumber()));
        loginInputDto.setLang("zh_CN");
        LoginVo loginVo = authService.bind(loginInputDto);
        LoginAppVo loginAppVo = new LoginAppVo();
        BeanUtils.copyProperties(loginVo, loginAppVo);
        return RestResult.success(loginAppVo);
    }

    /**
     * 获取token
     * @param loginInputDto
     * @param result
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/bind")
    public RestResult createAuthenticationToken(@Valid LoginInputDto loginInputDto,
                                                BindingResult result, HttpServletRequest request) throws Exception {
        String requestIp = WebUtil.getClientIP(request);
        loginInputDto.setRequestIp(requestIp);

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber number = phoneUtil.parseAndKeepRawInput(loginInputDto.getUsername(), "");
        boolean isNumberValid = phoneUtil.isValidNumber(number);
        if(!isNumberValid) {
            throw new BizFeignException("phone.Illegal.error");
        }
        loginInputDto.setUsername(String.valueOf(number.getNationalNumber()));
        loginInputDto.setCountryAreaCode(String.valueOf(number.getCountryCode()));

        LoginVo loginVo = authService.bind(loginInputDto);
        return RestResult.success(loginVo);
    }

    /**
     * 验证token
     * @param token
     * @return
     * @throws Exception
     */
    @GetMapping("/validate")
    public RestResult validate(@RequestParam String token) throws Exception {
        log.info("validate token={}", token);
        UserAuth userAuth = authService.validate(token);
        if(userAuth == null) {
            return RestResult.error("");
        }
        return RestResult.success(userAuth);
    }

    @PostMapping("/wx/login")
    public RestResult wxLogin(String userName, Integer id) throws Exception {
        String token = authService.wxLogin(userName, id);
        if(StringUtils.isBlank(token)) {
            return RestResult.error("");
        }
        return RestResult.success(token);
    }

    @PostMapping("/wx/validate")
    public RestResult wxValidate(String userName ,Integer userId, String token) throws Exception {
        String newToken = authService.validateWx(userName, userId, token);
        if(StringUtils.isBlank(newToken)) {
            return RestResult.error("请重新登录");
        }
        return RestResult.success(newToken);
    }

//    @PostMapping("/wx/validate")
//    public RestResult validate(String token) {
//        UserAuth userAuth = authService.validate(token);
//    }
}

package com.mars.yoyo.hotspot.security.gate.filter;

import com.mars.yoyo.hotspot.result.RestResult;
import com.mars.yoyo.hotspot.security.gate.client.AuthClient;
import com.mars.yoyo.hotspot.security.gate.constant.RedisConstants;
import com.mars.yoyo.hotspot.security.gate.dto.output.JWTInfo;
import com.mars.yoyo.hotspot.security.gate.dto.output.UserAuth;
import com.mars.yoyo.hotspot.security.gate.util.SignUtil;
import com.mars.yoyo.hotspot.util.DateUtil;
import com.mars.yoyo.hotspot.util.StringUtil;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author tookbra
 * @date 2018/5/25
 * @description
 */
@Slf4j
public class SignFilter extends AbstractFilter {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    AuthClient authClient;

    @Value("${zuul.prefix}")
    private String zuulPrefix;

    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterOrder.SIGN_FILTER;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

        HttpServletRequest request = ctx.getRequest();

        final String requestUri = request.getRequestURI().substring(zuulPrefix.length());
        if (isStartWith(requestUri)) {
            log.info("requestUri={} 默认通过，不校验", requestUri);
            setCtxSuccess(ctx);
            return null;
        }

        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        if(StringUtils.isBlank(token)) {
            log.info("user not login");
            setCtxFailed(ctx, "非法请求", HttpStatus.UNAUTHORIZED.value());
            return null;
        }

        //验证token有效性，得到用户信息
        String userId = "";
        RestResult<UserAuth> restResult = authClient.validate(token);
        if(restResult.isSuccess() && restResult.getData() != null) {
            UserAuth userAuth = restResult.getData();
            ctx.addZuulRequestHeader("userId", userAuth.getUserId());
            userId = userAuth.getUserId();
        } else {
            if(StringUtils.isNotBlank(token)) {
                // 自定义 431 token失效
                log.info("user token is not validate. token={}", token);
                setCtxFailed(ctx, "token失效", HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE.value());
                return null;
            } else {
                log.info("user token is not validate. token={}", token);
                setCtxFailed(ctx, "非法请求", HttpStatus.UNAUTHORIZED.value());
                return null;
            }
        }
        validateParam(ctx, userId);
        return null;
    }

    @Deprecated
    private void validateParam(RequestContext ctx, String userId) {
        HttpServletRequest request = ctx.getRequest();
        // 获取时间戳
        String timestamp = request.getHeader("timestamp");
        // 获取随机字符串
        String nonce = request.getHeader("nonce");
        // 获取请求地址
        String url = request.getHeader("url");
        // 获取签名
        String signature = request.getHeader("signature");
        // token
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }

        if(StringUtils.isAnyBlank(signature, timestamp, nonce, url)) {
            log.info("signature, timestamp, nonce, url is blank");
            setCtxFailed(ctx, "非法请求", HttpStatus.OK.value());
            return;
        }

        // 判断请求的url参数是否正确
        if (!request.getRequestURI().equals(url)){
            log.info("request uri not equal url. requestUrl={}, url={}", request.getRequestURI(), url);
            setCtxFailed(ctx, "非法请求", HttpStatus.OK.value());
            return;
        }

        // 判断时间是否大于60秒
        if(DateUtil.nowSubSecond(Long.parseLong(timestamp)) > 60) {
            log.info("request time out. now={}, timestamp={}", System.currentTimeMillis(), timestamp);
            setCtxFailed(ctx, "请求超时", HttpStatus.OK.value());
        }

        // 判断该用户的nonceStr参数是否已经在redis中
        if (haveNonce(Integer.parseInt(userId), nonce)){
            //请求仅一次有效（防止短时间内的重放攻击）
            log.info("request nonce. userId={}, once={}", userId, nonce);
            setCtxFailed(ctx, "请勿重复提交", HttpStatus.OK.value());
            return;
        }

        // 对请求头参数进行签名
        String sign = SignUtil.createSign(request.getParameterMap(), token, timestamp, nonce, url);

        // 如果签名验证不通过
        if (!signature.equals(sign)) {
            setCtxFailed(ctx, "非法请求", HttpStatus.OK.value());
            //非法请求（防止请求参数被篡改）
            return;
        }
        // 将本次用户请求的nonceStr参数存到redis中设置60秒后自动删除
        saveNonce(Integer.parseInt(userId), nonce);
    }

    private boolean haveNonce(int userId, String nonce) {
        String userNonceKey = RedisConstants.getNonceKey(userId, nonce);
        log.info("user nonce key = {}", userNonceKey);
        RBucket rBucket = redissonClient.getBucket(userNonceKey);
        Object obj = rBucket.get();
        if(obj != null) {
            return true;
        }
        return false;
    }

    private void saveNonce(int userId, String nonce) {
        String userNonceKey = RedisConstants.getNonceKey(userId, nonce);
        RBucket rBucket = redissonClient.getBucket(userNonceKey);
        rBucket.set(nonce, 60, TimeUnit.SECONDS);
    }

}

package com.mars.yoyo.hotspot.admin.aspect;

import com.mars.yoyo.hotspot.admin.params.SessionParameter;
import com.mars.yoyo.hotspot.admin.session.SessionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * token 拦截
 *
 * @author admin
 * @create 2018/9/2
 */
@Slf4j
@Aspect
@Component
@Order(-1)
public class AccessTokenAspect {

    public AccessTokenAspect() {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + AccessTokenAspect.class.getName() + " loaded");
    }

    @Pointcut("execution(* com.mars.yoyo.hotspot.admin.web..*Controller.*(..))")
    public void accessTokenPointCut() {
    }

    @Around("accessTokenPointCut()")
    public Object token(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return pjp.proceed();
        }
        HttpServletRequest request = requestAttributes.getRequest();
        SessionParameter sessionParameter = null;
        for (Object arg : args) {
            if (arg instanceof SessionParameter) {
                sessionParameter = (SessionParameter) arg;//这里假设参数中只有一个会话参数
                break;
            }
        }
        if (sessionParameter != null) {
            //防止前端注入，强制覆盖
            String accessToken = SessionHandler.getAccessToken(request);
            if (StringUtils.isEmpty(accessToken)) {
                throw new RuntimeException("-104", new Throwable("用户未登录"));
            }

            // 获取会话
            String sessionIdentity = SessionHandler.getSessionIdentity(accessToken);
            if (sessionIdentity == null) {
                throw new RuntimeException("-104", new Throwable("用户未登录"));
            }

            // 设置会话对象
            sessionParameter.setSessionIdentity(sessionIdentity);
        }
        return pjp.proceed();
    }


}

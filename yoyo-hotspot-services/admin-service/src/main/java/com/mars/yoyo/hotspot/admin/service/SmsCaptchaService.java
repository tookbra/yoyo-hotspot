package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.SmsCaptchaView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;

/**
 * 短信验证码
 *
 * @author admin
 * @create 2018/6/6
 * @since 1.0.0
 */
public interface SmsCaptchaService {

    /**
     * 分页查询短信
     * @return
     */
    ListResultEx<SmsCaptchaView> getSmsCaptchaList(CommonParameter parameter);

}

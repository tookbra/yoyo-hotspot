package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.SmsLogView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;

/**
 * 短信记录接口
 *
 * @author admin
 * @create 2018/5/14
 * @since 1.0.0
 */
public interface SmsLogService {

    /**
     * 分页查询短信
     * @return
     */
    ListResultEx<SmsLogView> getSmsLogList(CommonParameter parameter);

}

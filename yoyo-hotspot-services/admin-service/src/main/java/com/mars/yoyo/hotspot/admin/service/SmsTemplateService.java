package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.SmsTemplateParameter;
import com.mars.yoyo.hotspot.admin.resutls.SmsTemplateView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.result.RestResult;

/**
 * 短信模版
 *
 * @author admin
 * @create 2018/5/23
 */
public interface SmsTemplateService {

    /**
     * 获取短信模版列表
     * @return
     */
    ListResultEx<SmsTemplateView> getSmsTemplateList(CommonParameter parameter);

    /**
     * 新增短信模版
     * @param parameter
     * @return
     */
    RestResult addSmsTemplate(SmsTemplateParameter parameter);

    /**
     * 删除短信模版
     * @param templateId
     * @return
     */
    RestResult deleteSmsTemplate(Integer templateId);

    /**
     * 更新短信模版
     * @param parameter
     * @return
     */
    RestResult updateSmsTemplate(SmsTemplateParameter parameter);

    /**
     * 查询短信模版
     * @param templateId
     * @return
     */
    RestResult getSmsTemplateById(Integer templateId);

}

package com.mars.yoyo.hotspot.admin.web.sms;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.SmsLogView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.SmsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 短信日志
 *
 * @author admin
 * @create 2018/5/23
 */
@Controller
@RequestMapping("/smsLog")
public class SmsLogController {

    @Autowired
    private SmsLogService smsLogService;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "sms/list";
    }

    /**
     * 分页查询短信
     * @return
     */
    @ResponseBody
    @GetMapping("/listSmsLog")
    public ListResultEx<SmsLogView> getSmsLogList(CommonParameter parameter) {
        return smsLogService.getSmsLogList(parameter);
    }

}

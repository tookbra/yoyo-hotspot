package com.mars.yoyo.hotspot.admin.web.sms;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.SmsCaptchaView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.SmsCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 短信验证码记录列表
 *
 * @author admin
 * @create 2018/6/6
 */
@Controller
@RequestMapping("/captcha")
public class SmsCaptchaController {

    @Autowired
    private SmsCaptchaService smsCaptchaService;

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
    @GetMapping("/listSmsCaptcha")
    public ListResultEx<SmsCaptchaView> listSmsCaptcha(CommonParameter parameter) {
        return smsCaptchaService.getSmsCaptchaList(parameter);
    }

}

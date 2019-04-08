package com.mars.yoyo.hotspot.admin.web.sms;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.SmsTemplateParameter;
import com.mars.yoyo.hotspot.admin.resutls.SmsTemplateView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.SmsTemplateService;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 短信模版
 *
 * @author admin
 * @create 2018/5/24
 */
@Controller
@RequestMapping("/template")
public class SmsTemplateController {

    @Autowired
    private SmsTemplateService smsTemplateService;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "sms/template/list";
    }


    /**
     * 获取短信模版列表
     * @return
     */
    @ResponseBody
    @GetMapping("/listTemplates")
    public ListResultEx<SmsTemplateView> listTemplates(CommonParameter parameter) {
        return smsTemplateService.getSmsTemplateList(parameter);
    }

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preAdd")
    public String preAdd(){
        return "sms/template/add";
    }

    /**
     * 新增短信模版
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public RestResult addSmsTemplate(SmsTemplateParameter parameter) {
        return smsTemplateService.addSmsTemplate(parameter);
    }

    /**
     * 删除短信模版
     * @param templateId
     * @return
     */
    @ResponseBody
    @PostMapping("/delete/{templateId}")
    public RestResult deleteSmsTemplate(@PathVariable Integer templateId) {
        return smsTemplateService.deleteSmsTemplate(templateId);
    }

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preEdit/{templateId}")
    public String preEdit(ModelMap modelMap, @PathVariable Integer templateId){
        RestResult result = smsTemplateService.getSmsTemplateById(templateId);
        if (result.getData() != null) {
            modelMap.addAttribute("template", result.getData());
        } else {
            return result.getMsg();
        }
        return "sms/template/edit";
    }

    /**
     * 更新短信模版
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public RestResult updateSmsTemplate(SmsTemplateParameter parameter) {
        return smsTemplateService.updateSmsTemplate(parameter);
    }

}

package com.mars.yoyo.hotspot.admin.web.activity;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.QueryItemRedParameter;
import com.mars.yoyo.hotspot.admin.params.RedEnvelopeParameter;
import com.mars.yoyo.hotspot.admin.resutls.RedEnvelopeItemView;
import com.mars.yoyo.hotspot.admin.resutls.RedEnvelopeView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.admin.service.RedEnvelopeService;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 红包记录接口
 *
 * @author admin
 * @create 2018/5/17
 */
@Controller
@RequestMapping("/redEnvelope")
public class RedEnvelopeController {

    @Autowired
    private RedEnvelopeService redEnvelopeService;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "redEnvelope/list";
    }

    /**
     * 分页查询红包
     * @return
     */
    @ResponseBody
    @GetMapping("/listRedEnvelopes")
    public ListResultEx<RedEnvelopeView> listRedEnvelopes(CommonParameter parameter) {
        return redEnvelopeService.getRedEnvelopeList(parameter);
    }

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preAdd")
    public String preAdd(ModelMap modelMap){
        return "redEnvelope/add";
    }

    /**
     * 添加红包记录
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public RestResult addRedEnvelope(RedEnvelopeParameter parameter) {
        return redEnvelopeService.addRedEnvelope(parameter);
    }

    /**
     * 删除红包记录（逻辑删除）
     * @param redEnvelopeId
     * @return
     */
    @ResponseBody
    @PostMapping("/delete/{redEnvelopeId}")
    public RestResult deleteRedEnvelope(@PathVariable Integer redEnvelopeId) {
        return redEnvelopeService.deleteRedEnvelope(redEnvelopeId);
    }

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preEdit/{redEnvelopeId}")
    public String preEdit(ModelMap modelMap, @PathVariable Integer redEnvelopeId){
        RestResult result = redEnvelopeService.getRedEnvelopeById(redEnvelopeId);
        if (result.getData() != null) {
            modelMap.addAttribute("redEnvelope", result.getData());
        } else {
            return result.getMsg();
        }
        return "redEnvelope/edit";
    }

    /**
     * 更新红包信息
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public RestResult updateRedEnvelope(RedEnvelopeParameter parameter) {
        return redEnvelopeService.updateRedEnvelope(parameter);
    }


    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/listItem/{redEnvelopeId}")
    public String listItem(ModelMap modelMap, @PathVariable Integer redEnvelopeId){
        modelMap.addAttribute("redEnvelopeId", redEnvelopeId);
        return "redEnvelope/item";
    }

    /**
     * 分页查询小红包
     * @param parameter
     * @return
     */
    @ResponseBody
    @GetMapping("/listItemRedEnvelopes")
    public ListResultEx<RedEnvelopeItemView> listItemRedEnvelopes(QueryItemRedParameter parameter) {
        return redEnvelopeService.listItemRedEnvelopes(parameter);
    }

}

package com.mars.yoyo.hotspot.admin.web.channel;

import com.mars.yoyo.hotspot.admin.params.ChannelParameter;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.ChannelView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.admin.service.DeliveryChannelService;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备投放渠道管理
 *
 * @author admin
 * @create 2018/6/5
 */
@Controller
@RequestMapping("/channel")
public class DeliveryChannelController {

    @Autowired
    private DeliveryChannelService deliveryChannelService;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "channel/list";
    }

    /**
     * 分页查询
     * @param parameter
     * @return
     */
    @ResponseBody
    @GetMapping("/listChannels")
    public ListResultEx<ChannelView> listChannels(CommonParameter parameter) {
        return deliveryChannelService.listChannels(parameter);
    }

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preAdd")
    public String preAdd(){
        return "channel/add";
    }

    /**
     * 添加
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public RestResult addChannel(ChannelParameter parameter) {
        return deliveryChannelService.addChannel(parameter);
    }

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preEdit/{channelId}")
    public String preEdit(ModelMap modelMap, @PathVariable Integer channelId){
        RestResult result = deliveryChannelService.getChannelById(channelId);
        if (null == result.getData()) {
            return result.getMsg();
        }
        modelMap.addAttribute("channel", result.getData());

        return "channel/edit";
    }

    /**
     * 更新
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public RestResult updateChannel(ChannelParameter parameter) {
        return deliveryChannelService.updateChannel(parameter);
    }

    /**
     * 删除信息
     * @param channelId
     * @return
     */
    @ResponseBody
    @PostMapping("/delete/{channelId}")
    public RestResult deleteChannel(@PathVariable Integer channelId) {
        return deliveryChannelService.deleteChannel(channelId);
    }

    /**
     * 获取信息
     * @param channelId
     * @return
     */
    @ResponseBody
    @PostMapping("/getChannelById")
    public RestResult getChannelById(Integer channelId) {
        return deliveryChannelService.getChannelById(channelId);
    }

}

package com.mars.yoyo.hotspot.admin.web.user;

import com.mars.yoyo.hotspot.admin.params.PageParameter;
import com.mars.yoyo.hotspot.admin.resutls.UserRedEnvelopeView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.UserRedEnvelopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 用户的红包
 *
 * @author admin
 * @create 2018/5/16
 */
@Controller
@RequestMapping("/user")
public class UserRedEnvelopeController {

    @Autowired
    private UserRedEnvelopeService userRedEnvelopeService;

    /**
     * 查询红包
     * @return
     */
    @GetMapping("/redEnvelope/{userId}")
    public String coupon(ModelMap modelMap, @PathVariable Integer userId){
        modelMap.addAttribute("userId", userId);
        return "user/redEnvelope";
    }

    /**
     * 用户红包
     * @param userId
     * @return
     */
    @ResponseBody
    @GetMapping("/listUserRedEnvelope/{userId}")
    public ListResultEx<UserRedEnvelopeView> listUserRedEnvelope(@PathVariable Integer userId, PageParameter parameter) {
        return userRedEnvelopeService.getUserRedEnvelopeList(userId, parameter);
    }

}

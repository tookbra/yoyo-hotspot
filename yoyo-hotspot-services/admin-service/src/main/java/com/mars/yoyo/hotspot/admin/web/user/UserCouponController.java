package com.mars.yoyo.hotspot.admin.web.user;

import com.mars.yoyo.hotspot.admin.params.PageParameter;
import com.mars.yoyo.hotspot.admin.resutls.UserCouponView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户的优惠券
 *
 * @author admin
 * @create 2018/5/16
 */
@Controller
@RequestMapping("/user")
public class UserCouponController {

    @Autowired
    private UserCouponService userCouponService;

    /**
     * 查询优惠券
     * @return
     */
    @GetMapping("/coupon/{userId}")
    public String coupon(ModelMap modelMap, @PathVariable Integer userId){
        modelMap.addAttribute("userId", userId);
        return "user/coupon";
    }

    /**
     * 获取到用户的优惠券
     * @param userId
     * @return
     */
    @ResponseBody
    @GetMapping("/listUserCoupons/{userId}")
    public ListResultEx<UserCouponView> listUserCoupons(@PathVariable Integer userId, PageParameter parameter) {
        return userCouponService.getUserCouponList(userId, parameter);
    }

}

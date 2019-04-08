package com.mars.yoyo.hotspot.admin.web.activity;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.CouponParameter;
import com.mars.yoyo.hotspot.admin.resutls.CouponView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.admin.service.CouponService;
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
 * 优惠券接口
 *
 * @author admin
 * @create 2018/5/17
 */
@Controller
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "coupon/list";
    }

    /**
     * 优惠券查询列表
     * @return
     */
    @ResponseBody
    @GetMapping("/listCoupons")
    public ListResultEx<CouponView> listCoupons(CommonParameter parameter) {
        return couponService.listCoupons(parameter);
    }

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preAdd")
    public String preAdd(ModelMap modelMap){
        return "coupon/add";
    }

    /**
     * 添加优惠券
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public RestResult addCoupon(CouponParameter parameter) {
        return couponService.addCouponInfo(parameter);
    }

    /**
     * 删除
     * @param couponId
     * @return
     */
    @ResponseBody
    @PostMapping("/delete/{couponId}")
    public RestResult delete(@PathVariable Integer couponId) {
        return couponService.deleteCoupon(couponId);
    }

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preEdit/{couponId}")
    public String preEdit(ModelMap modelMap, @PathVariable Integer couponId){
        RestResult result = couponService.getCouponById(couponId);
        if (result.getData() != null) {
            modelMap.addAttribute("coupon", result.getData());
        } else {
            return result.getMsg();
        }
        return "coupon/edit";
    }

    /**
     * 更新优惠券信息
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public RestResult update(CouponParameter parameter) {
        return couponService.updateCoupon(parameter);
    }

    /**
     * 查询优惠券信息
     * @param couponId
     * @return
     */
    @ResponseBody
    @GetMapping("/getCouponById")
    public RestResult getCouponById(Integer couponId) {
        return couponService.getCouponById(couponId);
    }

}

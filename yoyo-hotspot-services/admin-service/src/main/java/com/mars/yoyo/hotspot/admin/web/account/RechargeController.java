package com.mars.yoyo.hotspot.admin.web.account;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.UserRechargeView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.AccountRechangeSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户账户充值记录
 *
 * @author admin
 * @create 2018/6/6
 */
@Controller
@RequestMapping("/recharge")
public class RechargeController {

    @Autowired
    private AccountRechangeSerivce accountRechangeSerivce;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "account/recharge";
    }

    /**
     * 用户账户充值记录
     * @return
     */
    @ResponseBody
    @GetMapping("/listRecharges")
    public ListResultEx<UserRechargeView> listRecharges(CommonParameter parameter) {
        return accountRechangeSerivce.getRechargeList(parameter);
    }


}

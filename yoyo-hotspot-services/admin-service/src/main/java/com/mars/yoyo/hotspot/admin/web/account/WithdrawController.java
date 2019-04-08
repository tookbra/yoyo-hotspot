package com.mars.yoyo.hotspot.admin.web.account;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.WithdrawParameter;
import com.mars.yoyo.hotspot.admin.resutls.UserAccountWithdrawView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.admin.service.AccountWithdrawService;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户提现记录
 *
 * @author admin
 * @create 2018/6/25
 */
@Controller
@RequestMapping("/withdraw")
public class WithdrawController {

    @Autowired
    private AccountWithdrawService accountWithdrawService;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "account/withdraw";
    }

    /**
     * 分页提现记录
     * @return
     */
    @ResponseBody
    @GetMapping("/listWithdraws")
    public ListResultEx<UserAccountWithdrawView> listWithdraws(CommonParameter parameter) {
        return accountWithdrawService.getAccountWithdraws(parameter);
    }

    /**
     * 设置提现状态
     * @param parameter
     * @return
     */
    @PostMapping("/updateStatusById")
    public RestResult updateStatusById(WithdrawParameter parameter) {
        return accountWithdrawService.updateStatusById(parameter);
    }

}

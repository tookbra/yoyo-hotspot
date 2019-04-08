package com.mars.yoyo.hotspot.admin.web.account;

import com.mars.yoyo.hotspot.admin.resutls.UserAccountView;
import com.mars.yoyo.hotspot.admin.service.UserAccountService;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 账户接口
 *
 * @author admin
 * @create 2018/5/16
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserAccountService userAccountService;

    /**
     * 查询用户账户信息
     * @return
     */
    @GetMapping("/getAccountInfo/{userId}")
    public String getAccountInfo(ModelMap modelMap, @PathVariable Integer userId){
        RestResult<UserAccountView> result = userAccountService.getUserAccount(userId);
        if (result.getData() != null) {
            modelMap.addAttribute("account", result.getData());
        } else {
            return result.getMsg();
        }
        return "user/account";
    }

}

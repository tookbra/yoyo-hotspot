package com.mars.yoyo.hotspot.admin.web;

import com.mars.yoyo.hotspot.admin.params.LoginParameter;
import com.mars.yoyo.hotspot.admin.params.UpdatePasswordParameter;
import com.mars.yoyo.hotspot.admin.resutls.AdminView;
import com.mars.yoyo.hotspot.admin.resutls.MenuView;
import com.mars.yoyo.hotspot.admin.service.LoginService;
import com.mars.yoyo.hotspot.admin.service.MenuService;
import com.mars.yoyo.hotspot.admin.service.StatisticsService;
import com.mars.yoyo.hotspot.admin.session.SessionHandler;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 登录管理
 * Created by admin on 2018/5/8.
 */
@Slf4j
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 登陆页
     * @return
     */
    @GetMapping({"/", " login"})
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    /**
     * 登录接口
     * @param parameter
     * @return
     */
    @PostMapping(value = "/login")
    public String login(HttpServletResponse response, ModelMap modelMap,  LoginParameter parameter) {
        RestResult<AdminView> result = loginService.login(parameter);
        if (result.getData() == null) {
           return "login";
        }
        AdminView adminView = result.getData();

        // 设置
        SessionHandler sessionHandler = new SessionHandler();
        sessionHandler.login(adminView.getId(), response);

        // 设置返回信息
        modelMap.addAttribute("admin", adminView);

        // 查询用户菜单
        RestResult<List<MenuView>> listResult = menuService.getMenuListByAdminId(adminView.getId());
        if (listResult.isSuccess()) {
            modelMap.addAttribute("menus", listResult.getData());
        }

        return "index";
    }

    /**
     * 首页统计数据
     * @param modelMap
     * @return
     */
    @GetMapping("/main")
    public String main(ModelMap modelMap){
        RestResult<Map<String, Object>> result = statisticsService.getTotalRentAndDeposit();
        if (result.getData() != null) {
            Map<String, Object> resultMap = result.getData();
            modelMap.addAttribute("rentNum", resultMap.get("rentNum"));
            modelMap.addAttribute("deposit", resultMap.get("deposit"));
        }
        return "main";
    }

    /**
     * 更新密码
     * @return
     */
    @GetMapping(value = "/updatePwd")
    public String updatePwd() {
        return "system/admin/rest";
    }

    /**
     * 密码重置
     * @param parameter
     * @return
     */
    @PostMapping("/restPwd")
    public RestResult restPwd(UpdatePasswordParameter parameter) {
        return loginService.restPwd(parameter);
    }

    /**
     * 忘记密码
     * @param parameter
     * @return
     */
    @PostMapping("/fogetPwd")
    public RestResult fogetPwd(UpdatePasswordParameter parameter) {
        return loginService.fogetPwd(parameter);
    }

    /**
     * 退出
     */
    @GetMapping("/logout")
    public String logout (HttpServletRequest request, HttpServletResponse response) {
        SessionHandler.logout(request, response);
        return "redirect:/";
    }

}

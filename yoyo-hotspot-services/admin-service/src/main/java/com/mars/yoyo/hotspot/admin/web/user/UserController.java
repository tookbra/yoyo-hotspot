package com.mars.yoyo.hotspot.admin.web.user;

import com.mars.yoyo.hotspot.admin.params.UserParameter;
import com.mars.yoyo.hotspot.admin.params.UserUpdateParameter;
import com.mars.yoyo.hotspot.admin.resutls.UserView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.admin.service.UserService;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 用户接口
 *
 * @author admin
 * @create 2018/5/16
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "user/list";
    }

    /**
     * 查询用户列表
     * @param parameter
     * @return
     */
    @ResponseBody
    @GetMapping("/listUserView")
    public ListResultEx<UserView> listUserView(UserParameter parameter) {
        return userService.getUserList(parameter);
    }

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/getUserInfo")
    public ResultEx getUserInfo(Integer userId) {
        return userService.getUserInfoById(userId);
    }

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preEdit/{userId}")
    public String preEdit(ModelMap modelMap, @PathVariable Integer userId){
        ResultEx result = userService.getUserInfoById(userId);
        if (result.getData() != null) {
            modelMap.addAttribute("user", result.getData());
        } else {
            return result.getRetMsg();
        }
        return "user/edit";
    }

    /**
     * 更新用户信息
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/update", name = "更新用户信息")
    public RestResult update(UserUpdateParameter parameter) {
        return userService.updateUserInfo(parameter);
    }

}

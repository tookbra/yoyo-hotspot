package com.mars.yoyo.hotspot.admin.web.system;

import com.mars.yoyo.hotspot.admin.params.AdminUserParameter;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.AdminView;
import com.mars.yoyo.hotspot.admin.resutls.RoleView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.AdminService;
import com.mars.yoyo.hotspot.admin.service.RoleService;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理人员
 *
 * @author admin
 * @create 2018/5/8
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "system/admin/list";
    }

    /**
     * 查询管理员列表
     * @return
     */
    @ResponseBody
    @GetMapping("/listAmdminView")
    public ListResultEx<AdminView> listAmdminView(CommonParameter parameter) {
        return adminService.getAdminList(parameter);
    }

    /**
     * 跳转添加页面
     * @return
     */
    @GetMapping("/preAdd")
    public String add(ModelMap modelMap) {
        RestResult<List<RoleView>> result = roleService.getAllRoleList();
        modelMap.addAttribute("roles", result.getData());

        return "system/admin/add";
    }

    /**
     * 添加管理员
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public RestResult addAdmin(AdminUserParameter parameter) {
        return adminService.addAdminUser(parameter);
    }

    /**
     * 跳转修改页面
     * @param modelMap
     * @param adminId
     * @return
     */
    @GetMapping("/preEdit/{adminId}")
    public String preEdit(ModelMap modelMap, @PathVariable Integer adminId) {
        RestResult<AdminView> result = adminService.getAdminViewById(adminId);
        modelMap.addAttribute("admin", result.getData());

        RestResult<List<RoleView>> roleResult = roleService.getAllRoleList();
        modelMap.addAttribute("roles", roleResult.getData());

        return "system/admin/edit";
    }

    /**
     * 修改
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public RestResult updateAdmin(AdminUserParameter parameter) {
        return adminService.updateAdmin(parameter);
    }

    /**
     * 删除
     * @param adminId
     * @return
     */
    @ResponseBody
    @PostMapping("/delete/{adminId}")
    public RestResult deleteAdmin(@PathVariable Integer adminId) {
        return adminService.deleteAdmin(adminId);
    }


}

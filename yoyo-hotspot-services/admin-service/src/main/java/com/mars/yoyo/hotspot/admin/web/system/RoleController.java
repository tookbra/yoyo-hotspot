package com.mars.yoyo.hotspot.admin.web.system;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.RoleParameter;
import com.mars.yoyo.hotspot.admin.resutls.MenuView;
import com.mars.yoyo.hotspot.admin.resutls.RoleView;
import com.mars.yoyo.hotspot.admin.resutls.ZtreeView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.MenuService;
import com.mars.yoyo.hotspot.admin.service.RoleService;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色
 *
 * @author admin
 * @create 2018/5/23
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "system/role/list";
    }

    /**
     * 角色列表
     * @return
     */
    @ResponseBody
    @GetMapping("/listRoleView")
    public ListResultEx<RoleView> listRoleView(CommonParameter parameter) {
        return roleService.getRoleList(parameter);
    }

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preAdd")
    public String preAdd(){
        return "system/role/add";
    }

    /**
     * 新增角色
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public RestResult addRole(RoleParameter parameter) {
        return roleService.addRole(parameter);
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @ResponseBody
    @PostMapping("/delete/{roleId}")
    public RestResult deleteRole(@PathVariable Integer roleId) {
        return roleService.deleteRole(roleId);
    }

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preEdit/{roleId}")
    public String preEdit(ModelMap modelMap, @PathVariable Integer roleId){
        RestResult<RoleView> result = roleService.getRoleViewById(roleId);
        modelMap.addAttribute("role", result.getData());

        return "system/role/edit";
    }

    /**
     * 更新角色
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public RestResult updateRole(RoleParameter parameter) {
        return roleService.updateRole(parameter);
    }


    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preGrant/{roleId}")
    public String preGrant(ModelMap modelMap, @PathVariable Integer roleId){
        modelMap.addAttribute("roleId", roleId);
        return "system/role/grant";
    }

    /**
     * 查询角色下的菜单树形结构
     * @param roleId
     * @return
     */
    @ResponseBody
    @GetMapping("/getTreeMenuByRoleId/{roleId}")
    public List<ZtreeView> getTreeMenuByRoleId(@PathVariable Integer roleId) {
        RestResult<List<MenuView>> result = menuService.getTreeMenuListByRolId(roleId);
        if (null == result.getData()) {
            return null;
        }
        List<ZtreeView> resulTreeNodes = new ArrayList<>();
        ZtreeView node;
        for (MenuView menuView : result.getData()) {
            node = new ZtreeView();
            node.setId(Long.valueOf(menuView.getId()));
            node.setpId(Long.valueOf(menuView.getParentId()));
            node.setName(menuView.getTitle());
            node.setChecked(menuView.isCheck());

            resulTreeNodes.add(node);
        }
        return resulTreeNodes;
    }

    /**
     * 设置角色菜单权限
     * @return
     */
    @ResponseBody
    @PostMapping("/grant/{roleId}")
    public RestResult grantRole(@PathVariable Integer roleId, @RequestParam(required = false) List<Integer> menuIds) {
        if (CollectionUtils.isEmpty(menuIds)) {
            return RestResult.error("菜单未勾选");
        }
        RoleParameter parameter = new RoleParameter();
        parameter.setId(roleId);
        parameter.setMenuIds(menuIds);
        return roleService.grantRole(parameter);
    }

}

package com.mars.yoyo.hotspot.admin.web.system;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.MenuParameter;
import com.mars.yoyo.hotspot.admin.resutls.MenuView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.MenuService;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 菜单接口
 *
 * @author admin
 * @create 2018/5/23
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "system/menu/list";
    }

    /**
     * 菜单列表
     * @return
     */
    @ResponseBody
    @GetMapping("/listPageMenus")
    public ListResultEx<MenuView> listPageMenus(CommonParameter parameter) {
        return menuService.getMenuList(parameter);
    }

    /**
     * 跳转添加页面
     * @return
     */
    @GetMapping("/preAdd")
    public String preAdd(ModelMap modelMap) {
        RestResult result = menuService.getAllMenuList();
        if (null == result.getData()) {
            return result.getMsg();
        }
        modelMap.addAttribute("menus", result.getData());

        return "system/menu/add";
    }

    /**
     * 新增菜单
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public RestResult addMenu(MenuParameter parameter) {
        return menuService.addMenu(parameter);
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @ResponseBody
    @PostMapping("/delete/{menuId}")
    public RestResult deleteMenu(@PathVariable Integer menuId) {
        return menuService.deleteMenu(menuId);
    }


    /**
     * 跳转添加页面
     * @return
     */
    @GetMapping("/preEdit/{menuId}")
    public String preEdit(ModelMap modelMap, @PathVariable Integer menuId) {
        RestResult<MenuView> result = menuService.getMenuViewById(menuId);
        if (null == result.getData()) {
            return result.getMsg();
        }
        modelMap.addAttribute("menu", result.getData());

        // 所有菜单
        RestResult allResult = menuService.getAllMenuList();
        if (null == allResult.getData()) {
            return allResult.getMsg();
        }
        modelMap.addAttribute("allMenus", allResult.getData());

        return "system/menu/edit";
    }

    /**
     * 更新菜单
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public RestResult updateMenu(MenuParameter parameter) {
        return menuService.updateMenu(parameter);
    }

    /**
     * 根据角色Id，查询菜单
     * @param roleId
     * @return
     */
    @GetMapping("/getMenuListByRoleId")
    public RestResult getMenuListByRoleId(Integer roleId) {
        return menuService.getMenuListByRoleId(roleId);
    }

}

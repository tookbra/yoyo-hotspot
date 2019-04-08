package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.MenuParameter;
import com.mars.yoyo.hotspot.admin.resutls.MenuView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.result.RestResult;

import java.util.List;

/**
 * 菜单接口
 *
 * @author admin
 * @create 2018/5/14
 * @since 1.0.0
 */
public interface MenuService {

    /**
     * 菜单列表
     * @return
     */
    ListResultEx<MenuView> getMenuList(CommonParameter parameter);

    /**
     * 新增菜单
     * @param parameter
     * @return
     */
    RestResult addMenu(MenuParameter parameter);

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    RestResult deleteMenu(Integer menuId);

    /**
     * 更新菜单
     * @param parameter
     * @return
     */
    RestResult updateMenu(MenuParameter parameter);

    /**
     * 查询菜单
     * @param menuId
     * @return
     */
    RestResult<MenuView>  getMenuViewById(Integer menuId);

    /**
     * 获取所有菜单
     * @return
     */
    RestResult<List<MenuView>> getAllMenuList();

    /**
     * 获取所有菜单（树形菜单-用于角色设置菜单）
     * @return
     */
    RestResult<List<MenuView>> getTreeMenuListByRolId(Integer roleId);

    /**
     * 查询当前帐号的菜单(树形结构)
     * @param adminId
     * @return
     */
    RestResult<List<MenuView>> getMenuListByAdminId(Integer adminId);

    /**
     * 根据角色Id，查询菜单
     * @param roleId
     * @return
     */
    RestResult<List<MenuView>> getMenuListByRoleId(Integer roleId);
}

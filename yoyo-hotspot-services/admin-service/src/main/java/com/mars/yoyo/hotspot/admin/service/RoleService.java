package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.RoleParameter;
import com.mars.yoyo.hotspot.admin.resutls.RoleView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.result.RestResult;

import java.util.List;

/**
 * 角色接口
 *
 * @author admin
 * @create 2018/5/14
 * @since 1.0.0
 */
public interface RoleService {

    /**
     * 角色列表
     * @return
     */
    ListResultEx<RoleView> getRoleList(CommonParameter parameter);

    /**
     * 新增角色
     * @param parameter
     * @return
     */
    RestResult addRole(RoleParameter parameter);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    RestResult deleteRole(Integer roleId);

    /**
     * 更新角色
     * @param parameter
     * @return
     */
    RestResult updateRole(RoleParameter parameter);

    /**
     * 设置角色的菜单权限
     * @param parameter
     * @return
     */
    RestResult grantRole(RoleParameter parameter);

    /**
     * 根据帐号Id，查询角色信息
     * @param adminId
     * @return
     */
    RestResult getRoleByAdminId(Integer adminId);

    /**
     * 查询全部的角色
     * @return
     */
    RestResult<List<RoleView>> getAllRoleList();

    /**
     * 查询角色信息
     * @param roleId
     * @return
     */
    RestResult getRoleViewById(Integer roleId);

}


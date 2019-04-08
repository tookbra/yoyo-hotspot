package com.mars.yoyo.hotspot.admin.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mars.yoyo.hotspot.admin.dao.SysAdminRoleMapper;
import com.mars.yoyo.hotspot.admin.dao.SysRoleMapper;
import com.mars.yoyo.hotspot.admin.dao.SysRoleMenuMapper;
import com.mars.yoyo.hotspot.admin.entity.SysAdminRole;
import com.mars.yoyo.hotspot.admin.entity.SysRole;
import com.mars.yoyo.hotspot.admin.entity.SysRoleMenu;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.RoleParameter;
import com.mars.yoyo.hotspot.admin.resutls.RoleView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.RoleService;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 角色
 *
 * @author admin
 * @create 2018/5/20
 */
@Slf4j
@Service
@Transactional(readOnly = false)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysAdminRoleMapper sysAdminRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public ListResultEx<RoleView> getRoleList(CommonParameter parameter) {
        ListResultEx<RoleView> resultEx = new ListResultEx<>();

        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        Example example = new Example(SysRole.class);
        if (!StringUtils.isEmpty(parameter.getSearchText())) {
            example.createCriteria().andLike("name", "%" + parameter.getSearchText() + "%");
        }
        List<SysRole> sysRoleList = sysRoleMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = sysRoleMapper.selectCount(null);

        List<RoleView> roleViewList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sysRoleList)) {
            RoleView roleView;
            for (SysRole sysRole : sysRoleList) {
                roleView = new RoleView();
                BeanUtils.copyProperties(sysRole, roleView);
                roleViewList.add(roleView);
            }
        }
        resultEx.setDataList(roleViewList);
        resultEx.setTotalCount(total);

        return resultEx.makeSuccessResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult addRole(RoleParameter parameter) {
        if (StringUtils.isEmpty(parameter.getName())) {
            return RestResult.error("参数不能为空");
        }
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(parameter, sysRole);
        Date now = new Date();
        sysRole.setCreateTime(now);
        sysRole.setModifyTime(now);

        sysRoleMapper.insertSelective(sysRole);

        return RestResult.success("新增角色完成");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult deleteRole(Integer roleId) {
        if(null == roleId) {
            return RestResult.error("参数不能为空");
        }
        // 删除角色菜单的信息
        Example example = new Example(SysRoleMenu.class);
        example.createCriteria().andEqualTo("roleId", roleId);
        sysRoleMenuMapper.deleteByExample(example);

        // 删除角色
        sysRoleMapper.deleteByPrimaryKey(roleId);

        return RestResult.success("删除角色完成");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult updateRole(RoleParameter parameter) {
        if (null == parameter.getId()) {
            return RestResult.error("参数不能为空");
        }
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(parameter.getId());
        if (null == sysRole) {
            return RestResult.error("角色不存在");
        }
        BeanUtils.copyProperties(parameter, sysRole, "id");

        sysRoleMapper.updateByPrimaryKeySelective(sysRole);

        return RestResult.success("更新角色完成");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult grantRole(RoleParameter parameter) {
        if (CollectionUtils.isEmpty(parameter.getMenuIds())) {
            return RestResult.error("未勾选菜单，请勾选");
        }
        List<Integer> updateMenuIds = new ArrayList<>(parameter.getMenuIds());

        // 查询角色中现有的菜单
        Example example = new Example(SysRoleMenu.class);
        example.createCriteria().andEqualTo("roleId", parameter.getId());
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(sysRoleMenuList)) {
            // 提取现有的菜单
            List<Integer> menuIds = Lists.transform(sysRoleMenuList, new Function<SysRoleMenu, Integer>() {
                @Override
                public Integer apply(@NullableDecl SysRoleMenu sysRoleMenu) {
                    return Integer.valueOf(sysRoleMenu.getMenuId());
                }
            });

            // step1：新的菜单
            updateMenuIds.removeAll(menuIds);

            // step2：被删除的菜单
            menuIds.removeAll(parameter.getMenuIds());

            // 删除被更新去掉的菜单
            if (CollectionUtils.isNotEmpty(menuIds)) {
                Example delExample = new Example(SysRoleMenu.class);
                delExample.createCriteria().andEqualTo("roleId", parameter.getId()).andIn("menuId", menuIds);

                sysRoleMenuMapper.deleteByExample(delExample);
            }
        }

        // 插入新的选择的菜单
        if (CollectionUtils.isNotEmpty(updateMenuIds)) {
            List<SysRoleMenu> newSysRoleMenus = new ArrayList<>();
            SysRoleMenu sysRoleMenu;
            for (Integer menuId : updateMenuIds) {
                sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(parameter.getId());
                sysRoleMenu.setMenuId(menuId);
                newSysRoleMenus.add(sysRoleMenu);
            }
            sysRoleMenuMapper.insertList(newSysRoleMenus);
        }
        return RestResult.success("设置角色菜单权限完成");
    }

    @Override
    public RestResult<RoleView> getRoleByAdminId(Integer adminId) {
        Example example = new Example(SysAdminRole.class);
        example.createCriteria().andEqualTo("adminId", adminId);
        SysAdminRole sysAdminRole = sysAdminRoleMapper.selectOneByExample(example);
        if (null == sysAdminRole) {
            return null;
        }
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(sysAdminRole.getRoleId());
        if (null == sysRole) {
            return null;
        }
        RoleView roleView = new RoleView();
        BeanUtils.copyProperties(sysRole, roleView);

        return RestResult.success(roleView);
    }

    @Override
    public RestResult<List<RoleView>> getAllRoleList() {
        List<SysRole> roleList = sysRoleMapper.selectAll();
        if (CollectionUtils.isEmpty(roleList)) {
            return RestResult.error("角色列表为空");
        }
        RoleView roleView;
        List<RoleView> roleViewList = new ArrayList<>();
        for (SysRole sysRole : roleList) {
            roleView = new RoleView();
            BeanUtils.copyProperties(sysRole, roleView);
            roleViewList.add(roleView);
        }
        return RestResult.success(roleViewList);
    }

    @Override
    public RestResult<RoleView> getRoleViewById(Integer roleId) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
        if (null == sysRole) {
            return null;
        }
        RoleView roleView = new RoleView();
        BeanUtils.copyProperties(sysRole, roleView);

        return RestResult.success(roleView);
    }

}

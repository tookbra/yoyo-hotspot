package com.mars.yoyo.hotspot.admin.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.mars.yoyo.hotspot.admin.dao.SysAdminRoleMapper;
import com.mars.yoyo.hotspot.admin.dao.SysMenuMapper;
import com.mars.yoyo.hotspot.admin.dao.SysRoleMenuMapper;
import com.mars.yoyo.hotspot.admin.entity.SysAdminRole;
import com.mars.yoyo.hotspot.admin.entity.SysMenu;
import com.mars.yoyo.hotspot.admin.entity.SysRoleMenu;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.MenuParameter;
import com.mars.yoyo.hotspot.admin.resutls.MenuView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.MenuService;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * 菜单接口实现
 *
 * @author admin
 * @create 2018/5/20
 */
@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysAdminRoleMapper sysAdminRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public ListResultEx<MenuView> getMenuList(CommonParameter parameter) {
        ListResultEx<MenuView> resultEx = new ListResultEx<>();

        Example example = new Example(SysMenu.class);
        example.setOrderByClause("parent_id, sort asc");
        if (!StringUtils.isEmpty(parameter.getSearchText())) {
            example.createCriteria().andLike("title", "%" + parameter.getSearchText() + "%");
        }
        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        List<SysMenu> sysMenuList = sysMenuMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = sysMenuMapper.selectCountByExample(example);

        List<MenuView> menuViewList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sysMenuList)) {
            MenuView menuView;
            for (SysMenu sysMenu : sysMenuList) {
                menuView = new MenuView();
                BeanUtils.copyProperties(sysMenu, menuView);

                // 查询父菜单
                SysMenu parent = sysMenuMapper.selectByPrimaryKey(sysMenu.getParentId());
                if (parent != null) {
                    menuView.setParentTitle(parent.getTitle());
                }

                menuViewList.add(menuView);
            }
        }
        resultEx.setDataList(menuViewList);
        resultEx.setTotalCount(total);

        return resultEx.makeSuccessResult();
    }

    @Override
    public RestResult addMenu(MenuParameter parameter) {
        if (StringUtils.isEmpty(parameter.getTitle())) {
            return RestResult.error("菜单标题不能为空");
        }
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(parameter, sysMenu);
        Date now = new Date();
        sysMenu.setCreateTime(now);
        sysMenu.setModifyTime(now);

        sysMenuMapper.insertSelective(sysMenu);

        return RestResult.success("添加菜单完成");
    }

    @Override
    public RestResult deleteMenu(Integer menuId) {
        if (null == menuId) {
            return RestResult.error("参数不能为空");
        }
        sysMenuMapper.deleteByPrimaryKey(menuId);

        return RestResult.success("删除菜单完成");
    }

    @Override
    public RestResult updateMenu(MenuParameter parameter) {
        if (StringUtils.isEmpty(parameter.getId())) {
            return RestResult.error("菜单参数不能为空");
        }
        Date now = new Date();

        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(parameter.getId());
        if (null == sysMenu) {
            sysMenu = new SysMenu();
            BeanUtils.copyProperties(parameter, sysMenu);
            sysMenu.setCreateTime(now);
            sysMenu.setModifyTime(now);
            sysMenuMapper.insertSelective(sysMenu);
        } else {
            BeanUtils.copyProperties(parameter, sysMenu);
            sysMenu.setModifyTime(now);
            sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
        }
        return RestResult.success("更新菜单完成");
    }

    @Override
    public RestResult<MenuView> getMenuViewById(Integer menuId) {
        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(menuId);
        if (null == sysMenu) {
            return RestResult.error("当前菜单不存在");
        }
        MenuView menuView = new MenuView();
        BeanUtils.copyProperties(sysMenu, menuView);

        return RestResult.success(menuView);
    }

    @Override
    public RestResult<List<MenuView>> getAllMenuList() {
        List<SysMenu> sysMenuList = sysMenuMapper.selectAll();
        if (CollectionUtils.isEmpty(sysMenuList)) {
            return RestResult.error("菜单列表为空");
        }
        List<MenuView> menuViewList = new ArrayList<>();
        MenuView menuView;
        for (SysMenu sysMenu : sysMenuList) {
            menuView = new MenuView();
            BeanUtils.copyProperties(sysMenu, menuView);
            menuViewList.add(menuView);
        }
        // 设置树形菜单
        List<MenuView> treeMenus = this.createTree(menuViewList);
        return RestResult.success(treeMenus);
    }

    @Override
    public RestResult<List<MenuView>> getTreeMenuListByRolId(Integer roleId) {
        // step1：首先查询所有菜单
        List<SysMenu> listAll = sysMenuMapper.selectAll();
        if (CollectionUtils.isEmpty(listAll)) {
            return RestResult.error("菜单列表为空");
        }
        Map<Integer, SysMenu> id2Map = new HashMap<>();

        // step2：查询当前角色的菜单
        Example roleExample = new Example(SysRoleMenu.class);
        roleExample.createCriteria().andEqualTo("roleId", roleId);

        List<SysRoleMenu> listRole = sysRoleMenuMapper.selectByExample(roleExample);
        if (CollectionUtils.isNotEmpty(listRole)) {
            List<Integer> menuIds = Lists.transform(listRole, new Function<SysRoleMenu, Integer>() {
                @Override
                public Integer apply(@NullableDecl SysRoleMenu sysRoleMenu) {
                    return Integer.valueOf(sysRoleMenu.getMenuId());
                }
            });

            // 查询菜单的信息
            Example example = new Example(SysMenu.class);
            example.createCriteria().andIn("id", menuIds);
            List<SysMenu> sysMenuList = sysMenuMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(sysMenuList)) {
                return RestResult.error("菜单列表为空..");
            }
            // 将集合转换为map
            id2Map = Maps.uniqueIndex(sysMenuList.iterator(), new Function<SysMenu , Integer>() {
                @Override
                public Integer apply(SysMenu input) {
                    return input.getId();
                }
            });
        }

        // step3：设置角色勾选的菜单
        List<MenuView> menuViewList = new ArrayList<>();
        MenuView menuView;
        for (SysMenu sysMenu : listAll) {
            menuView = new MenuView();
            BeanUtils.copyProperties(sysMenu, menuView);
            if (id2Map != null && id2Map.get(sysMenu.getId()) != null) {
                menuView.setCheck(true);
            }
            menuViewList.add(menuView);
        }
        return RestResult.success(menuViewList);
    }

    @Override
    public RestResult<List<MenuView>> getMenuListByAdminId(Integer adminId) {
        Example example = new Example(SysAdminRole.class);
        example.createCriteria().andEqualTo("adminId", adminId);
        SysAdminRole sysAdminRole = sysAdminRoleMapper.selectOneByExample(example);
        if (null == sysAdminRole) {
            return RestResult.error("当前帐号无角色分配");
        }
        RestResult<List<MenuView>> result = this.getMenuListByRoleId(sysAdminRole.getRoleId());
        if (null == result.getData()) {
            return RestResult.error(result.getMsg());
        }
        List<MenuView> treeMenus = this.createTree(result.getData());

        return RestResult.success(treeMenus);
    }

    /**
     * 构建树形结构
     * @param menuViews
     * @return
     */
    private List<MenuView> createTree(List<MenuView> menuViews) {
        // step1：将所有的菜单转换为parentId 对应 菜单的map
        ListMultimap<Integer, MenuView> parentMultiMap = Multimaps.index(menuViews.iterator(), new Function<MenuView, Integer>() {
            @Override
            public Integer apply(MenuView input) {
                return input.getParentId();
            }
        });

        // step2：将一级菜单获取出来，即父类id为0
        List<MenuView> parents = parentMultiMap.get(0);
        if (CollectionUtils.isEmpty(parents)) {
            return null;
        }

        // step3：构建子节点
        for (MenuView parent : parents) {
            setChild(parent, parentMultiMap);
        }
        return parents;
    }

    /**
     * 设置子节点
     * @param menuView
     * @param parentMultiMap
     */
    private void setChild(MenuView menuView, ListMultimap<Integer, MenuView> parentMultiMap) {
        // 获取到当前节点下的子节点
        List<MenuView> childs = parentMultiMap.get(menuView.getId());
        if (CollectionUtils.isEmpty(childs)) {
            return;
        }
        for (MenuView child : childs) {
            setChild(child, parentMultiMap);
        }
        menuView.setChilds(childs);
    }

    @Override
    public RestResult<List<MenuView>> getMenuListByRoleId(Integer roleId) {
        if (null == roleId) {
            return RestResult.error("参数不能为空");
        }
        Example roleMenu = new Example(SysRoleMenu.class);
        roleMenu.createCriteria().andEqualTo("roleId", roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectByExample(roleMenu);
        if (CollectionUtils.isEmpty(sysRoleMenuList)) {
            return RestResult.error("菜单列表为空.");
        }

        List<Integer> menuIds = Lists.transform(sysRoleMenuList, new Function<SysRoleMenu, Integer>() {
            @Override
            public Integer apply(@NullableDecl SysRoleMenu sysRoleMenu) {
                return Integer.valueOf(sysRoleMenu.getMenuId());
            }
        });

        Example example = new Example(SysMenu.class);
        example.createCriteria().andIn("id", menuIds);
        List<SysMenu> sysMenuList = sysMenuMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(sysMenuList)) {
            return RestResult.error("菜单列表为空..");
        }

        List<MenuView> menuViewList = new ArrayList<>();
        MenuView menuView;
        for (SysMenu sysMenu : sysMenuList) {
            menuView = new MenuView();
            BeanUtils.copyProperties(sysMenu, menuView);
            menuViewList.add(menuView);
        }

        return RestResult.success(menuViewList);
    }
}

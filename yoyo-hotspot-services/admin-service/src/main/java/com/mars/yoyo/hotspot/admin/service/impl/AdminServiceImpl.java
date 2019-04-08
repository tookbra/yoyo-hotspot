package com.mars.yoyo.hotspot.admin.service.impl;

import com.mars.yoyo.hotspot.admin.dao.SysAdminMapper;
import com.mars.yoyo.hotspot.admin.dao.SysAdminRoleMapper;
import com.mars.yoyo.hotspot.admin.dao.SysRoleMapper;
import com.mars.yoyo.hotspot.admin.entity.SysAdmin;
import com.mars.yoyo.hotspot.admin.entity.SysAdminRole;
import com.mars.yoyo.hotspot.admin.entity.SysRole;
import com.mars.yoyo.hotspot.admin.params.AdminUserParameter;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.AdminView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.AdminService;
import com.mars.yoyo.hotspot.admin.utils.MD5Utils;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
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
 * 管理后台用户接口实现
 *
 * @author admin
 * @create 2018/5/8
 */
@Slf4j
@Service
@Transactional(readOnly = false)
public class AdminServiceImpl implements AdminService {

    @Autowired
    private SysAdminMapper sysAdminMapper;

    @Autowired
    private SysAdminRoleMapper sysAdminRoleMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public ListResultEx<AdminView> getAdminList(CommonParameter parameter) {
        ListResultEx<AdminView> resultEx = new ListResultEx<>();

        // 设置查询条件
        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        Example example = new Example(SysAdmin.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(parameter.getSearchText())) {
            criteria.andLike("name", "%" + parameter.getSearchText() + "%");
        }
        List<SysAdmin> sysAdminList = sysAdminMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = sysAdminMapper.selectCountByExample(example);

        // 设置结果返回参数
        List<AdminView> adminViewList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sysAdminList)) {
            AdminView adminView;
            for (SysAdmin sysAdmin : sysAdminList) {
                adminView = new AdminView();
                BeanUtils.copyProperties(sysAdmin, adminView);

                // 查询管理员角色
                SysAdminRole sysAdminRole = sysAdminRoleMapper.selectByPrimaryKey(sysAdmin.getId());
                SysRole sysRole = sysRoleMapper.selectByPrimaryKey(sysAdminRole.getRoleId());
                if (sysRole != null) {
                    adminView.setRoleId(sysRole.getId());
                    adminView.setRoleName(sysRole.getName());
                }
                adminViewList.add(adminView);
            }
        }

        resultEx.setDataList(adminViewList);
        resultEx.setTotalCount(total);
        return resultEx.makeSuccessResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult addAdminUser(AdminUserParameter parameter) {
        if (StringUtils.isEmpty(parameter.getName())) {
            return RestResult.error("管理员名称参数不能为空");
        }
        if (StringUtils.isEmpty(parameter.getPassword())) {
            return RestResult.error("管理员登录密码参数不能为空");
        }
        if (null == parameter.getRoleId()) {
            return RestResult.error("管理员的角色未选择");
        }

        // 查询当前帐号是否已在数据库中
        Example example = new Example(SysAdmin.class);
        example.createCriteria().andEqualTo("name", parameter.getName());
        SysAdmin existAdmin = sysAdminMapper.selectOneByExample(example);
        if (existAdmin != null) {
            return RestResult.error("帐号名称已被使用，请重新输入名称");
        }

        // 设置密码和加密盐值
        String salt = MD5Utils.generateSalt();
        // 对密码进行加密
        String password = MD5Utils.digest(parameter.getPassword(), salt);

        SysAdmin sysAdmin = new SysAdmin();
        BeanUtils.copyProperties(parameter, sysAdmin);
        // 设置密码和加密盐值
        sysAdmin.setPassword(password);
        sysAdmin.setSalt(salt);
        Date now = new Date();
        sysAdmin.setCreateTime(now);
        sysAdmin.setModifyTime(now);

        sysAdminMapper.insertSelective(sysAdmin);

        SysAdminRole sysAdminRole = new SysAdminRole();
        sysAdminRole.setAdminId(sysAdmin.getId());
        sysAdminRole.setRoleId(parameter.getRoleId());
        sysAdminRoleMapper.insertSelective(sysAdminRole);

        return RestResult.success("添加管理员账号完成");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult deleteAdmin(Integer adminId) {
        // 参数判断
        if (null == adminId) {
            return RestResult.error("参数不能为空");
        }

        // 删除管理员角色
        Example example = new Example(SysAdminRole.class);
        example.createCriteria().andEqualTo("adminId", adminId);
        sysAdminRoleMapper.deleteByExample(example);
        // 删除管理员
        sysAdminMapper.deleteByPrimaryKey(adminId);

        return RestResult.success("删除管理员完成");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult updateAdmin(AdminUserParameter parameter) {
        if (null == parameter.getId()) {
            return RestResult.error("参数不能为空");
        }
        Date now = new Date();

        SysAdmin sysAdmin = sysAdminMapper.selectByPrimaryKey(parameter.getId());
        if (null == sysAdmin) {
            sysAdmin = new SysAdmin();
            BeanUtils.copyProperties(parameter, sysAdmin);
            sysAdmin.setCreateTime(now);
            sysAdmin.setModifyTime(now);
            sysAdminMapper.insertSelective(sysAdmin);

            if (parameter.getRoleId() != null) {
                SysAdminRole sysAdminRole = new SysAdminRole();
                sysAdminRole.setAdminId(sysAdmin.getId());
                sysAdminRole.setRoleId(parameter.getRoleId());
                sysAdminRoleMapper.insertSelective(sysAdminRole);
            }
        } else {
            BeanUtils.copyProperties(parameter, sysAdmin);
            sysAdmin.setModifyTime(new Date());
            sysAdminMapper.updateByPrimaryKeySelective(sysAdmin);

            if (parameter.getRoleId() != null) {
                Example example = new Example(SysAdminRole.class);
                example.createCriteria().andEqualTo("adminId", parameter.getId());
                SysAdminRole sysAdminRole = sysAdminRoleMapper.selectOneByExample(example);
                if (sysAdminRole != null) {
                    sysAdminRole.setRoleId(parameter.getRoleId());
                    sysAdminRoleMapper.updateByPrimaryKeySelective(sysAdminRole);
                }
            }
        }

        return RestResult.success("修改管理员完成");
    }

    @Override
    public RestResult<AdminView> getAdminViewById(Integer adminId) {
        SysAdmin sysAdmin = sysAdminMapper.selectByPrimaryKey(adminId);
        if (null == sysAdmin) {
            return RestResult.error("帐号不存在");
        }
        AdminView adminView = new AdminView();
        BeanUtils.copyProperties(sysAdmin, adminView);

        // 查询管理员角色
        SysAdminRole sysAdminRole = sysAdminRoleMapper.selectByPrimaryKey(sysAdmin.getId());
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(sysAdminRole.getRoleId());
        if (sysRole != null) {
            adminView.setRoleId(sysRole.getId());
            adminView.setRoleName(sysRole.getName());
        }
        return RestResult.success(adminView);
    }
}

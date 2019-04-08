package com.mars.yoyo.hotspot.admin.service.impl;

import com.mars.yoyo.hotspot.admin.dao.SysAdminMapper;
import com.mars.yoyo.hotspot.admin.dao.SysAdminRoleMapper;
import com.mars.yoyo.hotspot.admin.dao.SysRoleMapper;
import com.mars.yoyo.hotspot.admin.entity.SysAdmin;
import com.mars.yoyo.hotspot.admin.entity.SysAdminRole;
import com.mars.yoyo.hotspot.admin.entity.SysRole;
import com.mars.yoyo.hotspot.admin.params.LoginParameter;
import com.mars.yoyo.hotspot.admin.params.UpdatePasswordParameter;
import com.mars.yoyo.hotspot.admin.resutls.AdminView;
import com.mars.yoyo.hotspot.admin.service.LoginService;
import com.mars.yoyo.hotspot.admin.utils.MD5Utils;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * 登录接口实现
 *
 * @author admin
 * @create 2018/5/16
 */
@Slf4j
@Service("loginService")
@Transactional(readOnly = true)
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysAdminMapper sysAdminMapper;

    @Autowired
    private SysAdminRoleMapper sysAdminRoleMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public RestResult<AdminView> login(LoginParameter parameter) {
        // 判断登录的帐号和密码
        if (StringUtils.isBlank(parameter.getName())) {
            return RestResult.error("登录的帐号名称不能为空");
        }
        if (StringUtils.isBlank(parameter.getPassword())) {
            return RestResult.error("登录的密码不能为空");
        }

        // 查询帐号是否存在
        Example example = new Example(SysAdmin.class);
        example.createCriteria().andEqualTo("name", parameter.getName());
        SysAdmin sysAdmin = sysAdminMapper.selectOneByExample(example);
        if (null == sysAdmin) {
            return RestResult.error("当前帐号不存在");
        }
        String password = MD5Utils.digest(parameter.getPassword(), sysAdmin.getSalt());
        if (!password.equals(sysAdmin.getPassword())) {
            return RestResult.error("登录密码错误，请重新登录");
        }

        AdminView adminView = new AdminView();
        BeanUtils.copyProperties(sysAdmin, adminView);

        // 查询账号的角色
        Example adminRoleExp = new Example(SysAdminRole.class);
        adminRoleExp.createCriteria().andEqualTo("adminId", adminView.getId());
        SysAdminRole sysAdminRole = sysAdminRoleMapper.selectOneByExample(adminRoleExp);
        if (sysAdminRole != null) {
            // 根据角色Id，查询角色
            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(sysAdminRole.getRoleId());
            if (sysRole != null) {
                adminView.setRoleName(sysRole.getName());
            }
        }
        return RestResult.success(adminView);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult restPwd(UpdatePasswordParameter parameter) {
        // 判断登录的帐号和密码
        if (StringUtils.isBlank(parameter.getOldPassword())) {
            return RestResult.error("旧密码不能为空");
        }
        if (StringUtils.isBlank(parameter.getNewPassword())) {
            return RestResult.error("新密码不能为空");
        }
        if (StringUtils.isBlank(parameter.getConfirmPassword())) {
            return RestResult.error("确认密码不能为空");
        }

        // 查询帐号是否存在
        SysAdmin sysAdmin = sysAdminMapper.selectByPrimaryKey(parameter.getSessionIdentity());
        if (null == sysAdmin) {
            return RestResult.error("当前帐号不存在");
        }

        String password = MD5Utils.digest(parameter.getOldPassword(), sysAdmin.getSalt());
        if (!password.equals(sysAdmin.getPassword())) {
            return RestResult.error("旧密码错误，请重新输入旧密码");
        }

        // 设置密码和加密盐值
        String salt = MD5Utils.generateSalt();
        // 对密码进行加密
        String newPwd = MD5Utils.digest(parameter.getNewPassword(), salt);

        sysAdmin.setPassword(newPwd);
        sysAdmin.setSalt(salt);
        sysAdmin.setModifyTime(new Date());

        sysAdminMapper.updateByPrimaryKeySelective(sysAdmin);

        return RestResult.success("修改密码成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult fogetPwd(UpdatePasswordParameter parameter) {
        // 判断登录的帐号和密码
        if (StringUtils.isBlank(parameter.getNewPassword())) {
            return RestResult.error("新密码不能为空");
        }
        // 查询帐号是否存在
        SysAdmin sysAdmin = sysAdminMapper.selectByPrimaryKey(parameter.getSessionIdentity());
        if (null == sysAdmin) {
            return RestResult.error("当前帐号不存在");
        }
        // 设置密码和加密盐值
        String salt = MD5Utils.generateSalt();
        // 对密码进行加密
        String newPwd = MD5Utils.digest(parameter.getNewPassword(), salt);

        sysAdmin.setPassword(newPwd);
        sysAdmin.setSalt(salt);
        sysAdmin.setModifyTime(new Date());

        sysAdminMapper.updateByPrimaryKeySelective(sysAdmin);

        return RestResult.success("修改密码成功");
    }
}

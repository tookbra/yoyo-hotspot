package com.mars.yoyo.hotspot.admin.service.impl;

import com.mars.yoyo.hotspot.admin.dao.UserAccountMapper;
import com.mars.yoyo.hotspot.admin.dao.UserMapper;
import com.mars.yoyo.hotspot.admin.entity.User;
import com.mars.yoyo.hotspot.admin.entity.UserAccount;
import com.mars.yoyo.hotspot.admin.resutls.UserAccountView;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.admin.service.UserAccountService;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * 用户账户接口实现
 *
 * @author admin
 * @create 2018/5/16
 */
@Slf4j
@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Override
    public RestResult<UserAccountView> getUserAccount(Integer userId) {
        // 查询用户帐号信息
        Example example = new Example(UserAccount.class);
        example.createCriteria().andEqualTo("userId", userId);
        UserAccount userAccount = userAccountMapper.selectOneByExample(example);
        if (null == userAccount) {
            return RestResult.error("用户账户不存在");
        }
        // 设置结果返回参数
        UserAccountView userAccountView = new UserAccountView();
        BeanUtils.copyProperties(userAccount, userAccountView);

        // 查询会员信息
        User user = userMapper.selectByPrimaryKey(userId);
        if (null == user) {
            return RestResult.error("会员信息不存在");
        }
        userAccountView.setName(user.getName());
        userAccountView.setPhone(user.getPhone());

        return RestResult.success(userAccountView);
    }

}

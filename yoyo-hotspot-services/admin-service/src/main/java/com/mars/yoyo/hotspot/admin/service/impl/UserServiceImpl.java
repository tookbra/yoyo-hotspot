package com.mars.yoyo.hotspot.admin.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mars.yoyo.hotspot.admin.dao.UserAuthMapper;
import com.mars.yoyo.hotspot.admin.dao.UserMapper;
import com.mars.yoyo.hotspot.admin.entity.User;
import com.mars.yoyo.hotspot.admin.entity.UserAuth;
import com.mars.yoyo.hotspot.admin.params.UserParameter;
import com.mars.yoyo.hotspot.admin.params.UserUpdateParameter;
import com.mars.yoyo.hotspot.admin.resutls.UserView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.admin.service.UserService;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户接口实现
 *
 * @author admin
 * @create 2018/5/14
 */
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAuthMapper userAuthMapper;


    @Override
    public ListResultEx<UserView> getUserList(UserParameter parameter) {
        ListResultEx<UserView> resultEx = new ListResultEx<>();

        // 设置查询条件
        Example example = new Example(User.class);
        example.setOrderByClause("register_time desc");
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNoneBlank(parameter.getSearchText())) {
            criteria.andLike("name", "%" + parameter.getSearchText() + "%")
                    .orLike("phone", "%" + parameter.getSearchText() + "%");
        }
        if (StringUtils.isNoneBlank(parameter.getStartTime())) {
            criteria.andGreaterThanOrEqualTo("registerTime", parameter.getStartTime());
        }
        if (StringUtils.isNotBlank(parameter.getEndTime())) {
            criteria.andLessThan("registerTime", parameter.getEndTime());
        }
        if (parameter.getState() != null) {
            criteria.andEqualTo("state", parameter.getState());
        }

        // 设置分页参数
        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());

        // 分页查询用户列表
        List<User> userList = userMapper.selectByExampleAndRowBounds(example, rowBounds);
        // 查询返回结果的总数
        Integer total = userMapper.selectCountByExample(example);

        // 转换为返回结果参数列表
        List<UserView> userViewList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userList)) {
            UserView userView;
            for (User user : userList) {
                userView = new UserView();
                BeanUtils.copyProperties(user, userView);

                userViewList.add(userView);
            }
        }

        resultEx.setDataList(userViewList);
        resultEx.setTotalCount(total);
        return resultEx.makeSuccessResult();
    }

    /**
     * 查询用户来源
     * @param userList
     * @return
     */
    private Map<Integer, UserAuth> getUserAuthMaps(List<User> userList) {
        List<Integer> userIds = Lists.transform(userList, new Function<User, Integer>() {
            @Override
            public Integer apply(@NullableDecl User user) {
                return Integer.valueOf(user.getId());
            }
        });

        // 查询用户的认证信息
        Example example = new Example(UserAuth.class);
        example.createCriteria().andIn("userId", userIds);

        List<UserAuth> userAuths = userAuthMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(userAuths)) {
            return null;
        }

        // 将优惠券集合转换为map
        Map<Integer, UserAuth> userAuthMap = Maps.uniqueIndex(userAuths.iterator(), new Function<UserAuth, Integer>() {
            @Override
            public Integer apply(@NullableDecl UserAuth userAuth) {
                return userAuth.getUserId();
            }
        });

        return userAuthMap;
    }

    @Override
    public ResultEx getUserInfoById(Integer userId) {
        ResultEx resultEx = new ResultEx();
        // 参数判定
        if (null == userId) {
            return resultEx.makeFailedResult("参数不能为空");
        }
        // 查询用户是否存在
        User user = userMapper.selectByPrimaryKey(userId);
        if (null == user) {
            return resultEx.makeFailedResult("用户不存在");
        }
        // 设置返回的参数
        UserView userView = new UserView();
        BeanUtils.copyProperties(user, userView);

        resultEx.setData(userView);
        return resultEx.makeSuccessResult();
    }

    @Override
    public RestResult updateUserInfo(UserUpdateParameter parameter) {
        // 参数判定
        if (null == parameter.getId()) {
            return RestResult.error("参数不能为空");
        }
        // 查询用户是否存在
        User user = userMapper.selectByPrimaryKey(parameter.getId());
        if (null == user) {
            return RestResult.error("用户不存在");
        }
        // 设置修改的参数
        BeanUtils.copyProperties(parameter, user);

        // 更新用户信息
        userMapper.updateByPrimaryKeySelective(user);

        return RestResult.success("更新完成");
    }
}

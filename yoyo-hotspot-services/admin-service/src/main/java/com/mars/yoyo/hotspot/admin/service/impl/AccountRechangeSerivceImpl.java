package com.mars.yoyo.hotspot.admin.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mars.yoyo.hotspot.admin.dao.UserAccountRechargeMapper;
import com.mars.yoyo.hotspot.admin.dao.UserMapper;
import com.mars.yoyo.hotspot.admin.entity.User;
import com.mars.yoyo.hotspot.admin.entity.UserAccountRecharge;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.UserRechargeView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.AccountRechangeSerivce;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 充值记录接口实现
 *
 * @author admin
 * @create 2018/6/6
 */
@Slf4j
@Service
public class AccountRechangeSerivceImpl implements AccountRechangeSerivce {

    @Autowired
    private UserAccountRechargeMapper userAccountRechargeMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ListResultEx<UserRechargeView> getRechargeList(CommonParameter parameter) {
        ListResultEx<UserRechargeView> resultEx = new ListResultEx<>();

        // 设置分页参数
        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        // 设置查询参数
        Example example = new Example(UserAccountRecharge.class);
        example.setOrderByClause("create_time desc");
        if (StringUtils.isNotBlank(parameter.getSearchText())) {
            example.createCriteria().andLike("orderNo", "%" + parameter.getSearchText() + "%");
        }
        List<UserAccountRecharge> rechargeList = userAccountRechargeMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = userAccountRechargeMapper.selectCountByExample(example);


        // 抽取用户userId
        List<Integer> userIds = Lists.transform(rechargeList, new Function<UserAccountRecharge, Integer>() {
            @Override
            public Integer apply(@NullableDecl UserAccountRecharge userAccountRecharge) {
                return Integer.valueOf(userAccountRecharge.getUserId());
            }
        });
        Map<Integer, User> userMap = this.getUserMap(userIds);

        // 设置返回结果
        List<UserRechargeView> userRechargeViews = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(rechargeList)) {
            UserRechargeView userRechargeView;
            for (UserAccountRecharge recharge : rechargeList) {
                userRechargeView = new UserRechargeView();
                BeanUtils.copyProperties(recharge, userRechargeView);

                // 设置用户信息
                if (userMap != null && userMap.get(recharge.getUserId()) != null) {
                    User user = userMap.get(recharge.getUserId());
                    userRechargeView.setUserName(user.getName());
                    userRechargeView.setPhone(user.getPhone());
                }
                userRechargeViews.add(userRechargeView);
            }
        }
        resultEx.setDataList(userRechargeViews);
        resultEx.setTotalCount(total);
        return resultEx.makeSuccessResult();
    }

    /**
     * 获取用户对应ID的Map
     * @param userIds
     * @return
     */
    private Map<Integer, User> getUserMap(List<Integer> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return null;
        }
        // 查询用户
        Example example = new Example(User.class);
        example.createCriteria().andIn("id", userIds);
        List<User> users = userMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }

        // 将集合转换为Map
        Map<Integer, User> userMap = Maps.uniqueIndex(users.iterator(), new Function<User, Integer>() {
            @Override
            public Integer apply(@NullableDecl User user) {
                return user.getId();
            }
        });

        return userMap;
    }

}

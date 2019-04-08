package com.mars.yoyo.hotspot.admin.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mars.yoyo.hotspot.admin.dao.PayOrderMapper;
import com.mars.yoyo.hotspot.admin.dao.UserAccountMapper;
import com.mars.yoyo.hotspot.admin.dao.UserAccountWithdrawMapper;
import com.mars.yoyo.hotspot.admin.dao.UserMapper;
import com.mars.yoyo.hotspot.admin.entity.PayOrder;
import com.mars.yoyo.hotspot.admin.entity.User;
import com.mars.yoyo.hotspot.admin.entity.UserAccount;
import com.mars.yoyo.hotspot.admin.entity.UserAccountWithdraw;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.WithdrawParameter;
import com.mars.yoyo.hotspot.admin.resutls.UserAccountWithdrawView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.AccountWithdrawService;
import com.mars.yoyo.hotspot.result.RestResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
 * 提现接口实现
 *
 * @author admin
 * @create 2018/6/25
 */
@Service("accountWithdrawService")
public class AccountWithdrawServiceImpl implements AccountWithdrawService {

    @Autowired
    private UserAccountWithdrawMapper userAccountWithdrawMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Override
    public ListResultEx<UserAccountWithdrawView> getAccountWithdraws(CommonParameter parameter) {
        ListResultEx<UserAccountWithdrawView> resultEx = new ListResultEx<>();

        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        // 查询参数
        Example example = new Example(UserAccountWithdraw.class);
        example.setOrderByClause(" create_time desc ");
        if (StringUtils.isNotBlank(parameter.getSearchText())) {
            example.createCriteria().andLike("orderNo", "%" + parameter.getSearchText() + "%");
        }
        List<UserAccountWithdraw> withdraws = userAccountWithdrawMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = userAccountWithdrawMapper.selectCount(null);

        // 抽取用户userId
        List<Integer> userIds = Lists.transform(withdraws, new Function<UserAccountWithdraw, Integer>() {
            @Override
            public Integer apply(@NullableDecl UserAccountWithdraw userAccountWithdraw) {
                return Integer.valueOf(userAccountWithdraw.getUserId());
            }
        });
        Map<Integer, User> userMap = this.getUserMap(userIds);
        Map<Integer, UserAccount> userAccountMap = this.getUserAccountMap(userIds);

        List<UserAccountWithdrawView> withdrawViews = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(withdraws)) {
            UserAccountWithdrawView userAccountWithdrawView;
            for (UserAccountWithdraw withdraw : withdraws) {
                userAccountWithdrawView = new UserAccountWithdrawView();
                BeanUtils.copyProperties(withdraw, userAccountWithdrawView);

                // 查询用户信息
                if (userMap != null && userMap.get(withdraw.getUserId()) != null) {
                    User user = userMap.get(withdraw.getUserId());
                    userAccountWithdrawView.setName(user.getName());
                    userAccountWithdrawView.setPhone(user.getPhone());
                }

                // 查询用户账号
                if (userAccountMap != null && userAccountMap.get(withdraw.getUserId()) != null) {
                    UserAccount userAccount = userAccountMap.get(withdraw.getUserId());
                    userAccountWithdrawView.setBalance(userAccount.getBalance());
                    userAccountWithdrawView.setBalanceEn(userAccount.getBalanceEn());
                    userAccountWithdrawView.setDeposit(userAccount.getDeposit());
                    userAccountWithdrawView.setDepositEn(userAccount.getDepositEn());
                }

                // 查询订单
                PayOrder payOrder = payOrderMapper.selectByPrimaryKey(withdraw.getOrderNo());
                if (payOrder != null) {
                    userAccountWithdrawView.setOrderState(payOrder.getState());
                }
                withdrawViews.add(userAccountWithdrawView);
            }
        }

        resultEx.setDataList(withdrawViews);
        resultEx.setTotalCount(total);
        return resultEx.makeSuccessResult();
    }

    /**
     * 查询用户账号信息
     * @param userIds
     * @return
     */
    private Map<Integer, UserAccount> getUserAccountMap(List<Integer> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return null;
        }
        Example example = new Example(UserAccount.class);
        example.createCriteria().andIn("userId", userIds);
        List<UserAccount> userAccounts = userAccountMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(userAccounts)) {
            return null;
        }
        // 将集合转换为Map
        Map<Integer, UserAccount> userAccountMap = Maps.uniqueIndex(userAccounts.iterator(), new Function<UserAccount, Integer>() {
            @Override
            public Integer apply(@NullableDecl UserAccount user) {
                return user.getUserId();
            }
        });
        return userAccountMap;
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

    @Override
    public RestResult updateStatusById(WithdrawParameter parameter) {
        if (null == parameter.getId()) {
            return RestResult.error("参数不能为空");
        }
        // 查询提现记录
        UserAccountWithdraw withdraw = userAccountWithdrawMapper.selectByPrimaryKey(parameter.getId());
        if (null == withdraw) {
            return RestResult.error("当前记录不存在");
        }
        withdraw.setStatus(parameter.getStatus());

        userAccountWithdrawMapper.updateByPrimaryKeySelective(withdraw);

        return RestResult.success("设置成功");
    }
}

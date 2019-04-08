package com.mars.yoyo.hotspot.admin.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mars.yoyo.hotspot.admin.dao.LeaseMapper;
import com.mars.yoyo.hotspot.admin.dao.UserMapper;
import com.mars.yoyo.hotspot.admin.entity.Lease;
import com.mars.yoyo.hotspot.admin.entity.User;
import com.mars.yoyo.hotspot.admin.params.LeaseParameter;
import com.mars.yoyo.hotspot.admin.resutls.LeaseView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.LeaseService;
import lombok.extern.slf4j.Slf4j;
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
 * LeaseService
 *
 * @author admin
 * @Date 2018/9/5 17:07
 */
@Slf4j
@Service
public class LeaseServiceImpl implements LeaseService {

    @Autowired
    private LeaseMapper leaseMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ListResultEx<LeaseView> listLeaseView(LeaseParameter parameter) {
        ListResultEx<LeaseView> resultEx = new ListResultEx<>();

        // 设置查询条件
        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        Example example = new Example(Lease.class);
        example.setOrderByClause("create_time desc");
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(parameter.getPowerBankId())) {
            criteria.andLike("powerBankId", "%" + parameter.getPowerBankId() + "%");
        }
        if (StringUtils.isNotBlank(parameter.getLeaseNo())) {
            criteria.andLike("leaseNo", "%" + parameter.getLeaseNo() + "%");
        }
        if (StringUtils.isNotBlank(parameter.getStartTime())) {
            criteria.andGreaterThanOrEqualTo("rentTime", parameter.getStartTime());
        }
        if (StringUtils.isNotBlank(parameter.getEndTime())) {
            criteria.andLessThan("rentTime", parameter.getEndTime());
        }

        // 查询记录
        List<Lease> leaseList = leaseMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = leaseMapper.selectCountByExample(example);

        // 设置返回参数
        List<LeaseView> leaseViewList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(leaseList)) {
            // 获取用户信息
            Map<Integer, User> userMap = this.getUserMpas(leaseList);

            LeaseView leaseView;
            for (Lease lease : leaseList) {
                leaseView = new LeaseView();
                BeanUtils.copyProperties(lease, leaseView);

                // 设置用户信息
                if (userMap != null && userMap.get(lease.getUserId()) != null) {
                    User user = userMap.get(lease.getUserId());
                    leaseView.setName(user.getName());
                    leaseView.setPhone(user.getPhone());
                }
                leaseViewList.add(leaseView);
            }
        }

        resultEx.setDataList(leaseViewList);
        resultEx.setTotalCount(total);
        return resultEx.makeSuccessResult();
    }

    /**
     * 查询订单中的用户信息
     * @param leaseList
     * @return
     */
    private Map<Integer, User> getUserMpas(List<Lease> leaseList) {
        List<Integer> userIds = Lists.transform(leaseList, new Function<Lease, Integer>() {
            @Override
            public Integer apply(@NullableDecl Lease order) {
                return Integer.valueOf(order.getUserId());
            }
        });

        Example example = new Example(User.class);
        example.createCriteria().andIn("id", userIds);
        List<User> userList = userMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        }
        // 将用户集合转换为map
        Map<Integer, User> userMap = Maps.uniqueIndex(userList.iterator(), new Function<User, Integer>() {
            @Override
            public Integer apply(@NullableDecl User user) {
                return user.getId();
            }
        });
        return userMap;
    }

}

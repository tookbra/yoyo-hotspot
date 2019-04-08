package com.mars.yoyo.hotspot.admin.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mars.yoyo.hotspot.admin.dao.LeaseMapper;
import com.mars.yoyo.hotspot.admin.dao.PayOrderMapper;
import com.mars.yoyo.hotspot.admin.dao.UserMapper;
import com.mars.yoyo.hotspot.admin.entity.Lease;
import com.mars.yoyo.hotspot.admin.entity.PayOrder;
import com.mars.yoyo.hotspot.admin.entity.User;
import com.mars.yoyo.hotspot.admin.params.OrderParameter;
import com.mars.yoyo.hotspot.admin.resutls.PayOrderView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.OrderService;
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
 * 订单业务实现
 *
 * @author admin
 * @create 2018/5/23
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Autowired
    private LeaseMapper leaseMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ListResultEx<PayOrderView> getOrderList(OrderParameter parameter) {
        ListResultEx<PayOrderView> resultEx = new ListResultEx<>();

        // 设置查询参数
        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        Example example = new Example(PayOrder.class);
        example.setOrderByClause("create_time desc");
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(parameter.getSearchText())) {
            criteria.andLike("orderId", "%" + parameter.getSearchText() + "%");
        }
        if (parameter.getPayChannel() != null) {
            criteria.andEqualTo("payChannel", parameter.getPayChannel());
        }
        if (parameter.getOrderType() != null) {
            criteria.andEqualTo("orderType", parameter.getOrderType());
        }
        if (parameter.getState() != null) {
            criteria.andEqualTo("state", parameter.getState());
        }
        if (StringUtils.isNotBlank(parameter.getStartTime())) {
            criteria.andGreaterThanOrEqualTo("createTime", parameter.getStartTime());
        }
        if (StringUtils.isNotBlank(parameter.getEndTime())) {
            criteria.andLessThan("createTime", parameter.getEndTime());
        }

        List<PayOrder> orderList = payOrderMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = payOrderMapper.selectCountByExample(example);

        // 设置订单信息
        List<PayOrderView> orderViewList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orderList)) {
            // 获取用户信息
            Map<Integer, User> userMap = this.getUserMpas(orderList);
            Map<String, Lease> leaseMap = this.getLeaseMaps(orderList);

            // 构建订单信息
            PayOrderView orderView;
            for (PayOrder order : orderList) {
                orderView = new PayOrderView();
                BeanUtils.copyProperties(order, orderView);

                // 设置用户信息
                if (userMap != null && userMap.get(order.getUserId()) != null) {
                    User user = userMap.get(order.getUserId());
                    orderView.setPhone(user.getPhone());
                    orderView.setEn(user.getEn());
                }

                // 设置租借信息
                if (leaseMap != null && leaseMap.get(order.getOrderId()) != null) {
                    Lease lease = leaseMap.get(order.getOrderId());
                    orderView.setPowerBankId(lease.getPowerBankId());
                    orderView.setPassowrd(lease.getPassowrd());
                    orderView.setReturned(lease.getReturned());
                    orderView.setProductName(lease.getProductName());
                }
                orderViewList.add(orderView);
            }
        }
        resultEx.setDataList(orderViewList);
        resultEx.setTotalCount(total);

        return resultEx.makeSuccessResult();
    }

    /**
     * 查询订单中的用户信息
     * @param orderList
     * @return
     */
    private Map<Integer, User> getUserMpas(List<PayOrder> orderList) {
        List<Integer> userIds = Lists.transform(orderList, new Function<PayOrder, Integer>() {
            @Override
            public Integer apply(@NullableDecl PayOrder order) {
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

    /**
     * 获取租借信息
     * @param orderList
     * @return
     */
    private Map<String, Lease> getLeaseMaps(List<PayOrder> orderList) {
        List<String> orderIds = Lists.transform(orderList, new Function<PayOrder, String>() {
            @Override
            public String apply(@NullableDecl PayOrder order) {
                return String.valueOf(order.getOrderId());
            }
        });

        Example example = new Example(Lease.class);
        example.createCriteria().andIn("orderNo", orderIds);
        List<Lease> leaseList = leaseMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(leaseList)) {
            return null;
        }
        // 将用户集合转换为map
        Map<String, Lease> leaseMap = Maps.uniqueIndex(leaseList.iterator(), new Function<Lease, String>() {
            @Override
            public String apply(@NullableDecl Lease lease) {
                return lease.getOrderNo();
            }
        });
        return leaseMap;
    }

}

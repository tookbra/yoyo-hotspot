package com.mars.yoyo.hotspot.admin.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mars.yoyo.hotspot.admin.dao.CouponMapper;
import com.mars.yoyo.hotspot.admin.dao.UserCouponMapper;
import com.mars.yoyo.hotspot.admin.dao.UserMapper;
import com.mars.yoyo.hotspot.admin.entity.Coupon;
import com.mars.yoyo.hotspot.admin.entity.User;
import com.mars.yoyo.hotspot.admin.entity.UserCoupon;
import com.mars.yoyo.hotspot.admin.enums.DeleteStatusEnum;
import com.mars.yoyo.hotspot.admin.params.PageParameter;
import com.mars.yoyo.hotspot.admin.resutls.UserCouponView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.UserCouponService;
import lombok.extern.slf4j.Slf4j;
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
 * 用户优惠券接口实现
 *
 * @author admin
 * @create 2018/5/16
 */
@Slf4j
@Service("userCouponService")
public class UserCouponServiceImpl implements UserCouponService {

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ListResultEx<UserCouponView> getUserCouponList(Integer userId, PageParameter parameter) {
        ListResultEx<UserCouponView> resultEx = new ListResultEx<>();

        // 参数判定
        if (null == userId) {
            return resultEx.makeFailedResult("参数不能为空");
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (null == user) {
            return resultEx.makeFailedResult("会员不存在");
        }
        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        // 查询用户的有效的优惠券
        Example example = new Example(UserCoupon.class);
        example.setOrderByClause("create_time desc");
        Example.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andEqualTo("userId", userId);
        }
        List<UserCoupon> userCouponList = userCouponMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer totalCount = userCouponMapper.selectCountByExample(example);

        Map<Integer, Coupon> couponMap = this.getCouponMap(userCouponList);

        // 设置用户优惠券的结果集
        List<UserCouponView> userCouponViewList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userCouponList)) {
            UserCouponView userCouponView;
            for (UserCoupon userCoupon : userCouponList) {
                userCouponView = new UserCouponView();
                // 设置用户优惠券信息
                BeanUtils.copyProperties(userCoupon, userCouponView);
                userCouponView.setUserName(user.getName());
                // 获取优惠券信息
                if (couponMap != null && couponMap.get(userCoupon.getCouponId()) != null) {
                    Coupon coupon = couponMap.get(userCoupon.getCouponId());
                    userCouponView.setName(coupon.getName());
                    userCouponView.setUseType(coupon.getUseType());
                    userCouponView.setChannel(coupon.getChannel());
                    userCouponView.setConditions(coupon.getConditions());
                    userCouponView.setMoney(coupon.getMoney());
                    userCouponView.setStatus(coupon.getStatus());
                    userCouponView.setBeginDate(coupon.getBeginDate());
                    userCouponView.setEndDate(coupon.getEndDate());
                } else {
                    continue;
                }
                userCouponViewList.add(userCouponView);
            }
        }
        resultEx.setDataList(userCouponViewList);
        resultEx.setTotalCount(totalCount);

        return resultEx.makeSuccessResult();
    }

    private Map<Integer, Coupon> getCouponMap(List<UserCoupon> userCouponList) {
        // 抽取优惠券couponId
        List<Integer> couponIds = Lists.transform(userCouponList, new Function<UserCoupon, Integer>() {
            @Override
            public Integer apply(@NullableDecl UserCoupon userCoupon) {
                return Integer.valueOf(userCoupon.getCouponId());
            }
        });

        // 查询优惠券的信息
        Example couponExample = new Example(Coupon.class);
        couponExample.createCriteria().andEqualTo("deleted", DeleteStatusEnum.UNDELETE.getCode()).andIn("id", couponIds);

        List<Coupon> couponList = couponMapper.selectByExample(couponExample);
        if (CollectionUtils.isEmpty(couponList)) {
            return null;
        }

        // 将优惠券集合转换为map
        Map<Integer, Coupon> couponMap = Maps.uniqueIndex(couponList.iterator(), new Function<Coupon, Integer>() {
            @Override
            public Integer apply(@NullableDecl Coupon coupon) {
                return coupon.getId();
            }
        });

        return couponMap;
    }

}

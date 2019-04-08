package com.mars.yoyo.hotspot.admin.service.impl;

import com.mars.yoyo.hotspot.admin.dao.CouponMapper;
import com.mars.yoyo.hotspot.admin.entity.Coupon;
import com.mars.yoyo.hotspot.admin.enums.DeleteStatusEnum;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.CouponParameter;
import com.mars.yoyo.hotspot.admin.resutls.CouponView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.admin.service.CouponService;
import com.mars.yoyo.hotspot.result.RestResult;
import com.mars.yoyo.hotspot.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 优惠券接口实现
 *
 * @author admin
 * @create 2018/5/17
 */
@Slf4j
@Service("couponService")
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Override
    public ListResultEx<CouponView> listCoupons(CommonParameter parameter) {
        ListResultEx<CouponView> resultEx = new ListResultEx<>();

        // 查询优惠券列表
        Example example = new Example(Coupon.class);
        example.setOrderByClause("create_time desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deleted", DeleteStatusEnum.UNDELETE.getCode());
        if (!StringUtils.isEmpty(parameter.getSearchText())) {
            criteria.andLike("name", "%" + parameter.getSearchText() + "%");
        }
        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        List<Coupon> couponList = couponMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = couponMapper.selectCountByExample(example);

        // 设置结果参数
        List<CouponView> couponViewList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(couponList)) {
            CouponView couponView;
            for (Coupon coupon : couponList) {
                couponView = new CouponView();
                BeanUtils.copyProperties(coupon, couponView);
                couponViewList.add(couponView);
            }
        }

        resultEx.setDataList(couponViewList);
        resultEx.setTotalCount(total);
        return resultEx.makeSuccessResult();
    }

    @Override
    public RestResult addCouponInfo(CouponParameter parameter) {
        // 参数判定
        if (StringUtils.isEmpty(parameter.getName())) {
            return RestResult.error("参数不能为空");
        }
        // 创建优惠券对象
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(parameter, coupon);
        coupon.setMoney(new BigDecimal(parameter.getMoney()));
        coupon.setChannel(parameter.getChannel());
        coupon.setBeginDate(DateUtil.parseDate(parameter.getBeginDate(), "yyyy-MM-dd"));
        coupon.setEndDate(DateUtil.parseDate(parameter.getEndDate(), "yyyy-MM-dd"));

        Date now = new Date();
        coupon.setCreateTime(now);
        coupon.setModifyTime(now);

        couponMapper.insertSelective(coupon);

        return RestResult.success("添加优惠券完成");
    }

    @Override
    public RestResult deleteCoupon(Integer couponId) {
        // 参数判定
        if (null == couponId) {
            return RestResult.error("参数不能为空");
        }

        // 查询优惠券
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        if (null == coupon) {
            return RestResult.error("优惠券不存在");
        }
        coupon.setModifyTime(new Date());
        // 设置优惠券为无效状态 - 0
        coupon.setStatus(0);
        // 设置优惠券删除状态，并更新
        coupon.setDeleted(DeleteStatusEnum.DELETED.getCode());

        couponMapper.updateByPrimaryKey(coupon);

        return RestResult.success("优惠券删除完成");
    }

    @Override
    public RestResult updateCoupon(CouponParameter parameter) {
        // 参数判定
        if (null == parameter.getId()) {
            return RestResult.error("参数不能为空");
        }

        // 查询优惠券
        Coupon coupon = couponMapper.selectByPrimaryKey(parameter.getId());
        if (null == coupon) {
            return RestResult.error("优惠券不存在");
        }

        // 设置需要更新的参数
        BeanUtils.copyProperties(parameter, coupon);
        String money = parameter.getMoney().replace(",", "");
        coupon.setMoney(new BigDecimal(money));
        coupon.setChannel(parameter.getChannel());
        coupon.setBeginDate(DateUtil.parseDate(parameter.getBeginDate(), "yyyy-MM-dd"));
        coupon.setEndDate(DateUtil.parseDate(parameter.getEndDate(), "yyyy-MM-dd"));
        coupon.setModifyTime(new Date());

        couponMapper.updateByPrimaryKey(coupon);

        return RestResult.success("优惠券更新完成");
    }

    @Override
    public RestResult getCouponById(Integer couponId) {
        // 参数判定
        if (null == couponId) {
            return RestResult.error("参数不能为空");
        }

        // 查询优惠券
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        if (null == coupon) {
            return RestResult.error("优惠券不存在");
        }

        // 设置返回结果参数
        CouponView couponView = new CouponView();
        BeanUtils.copyProperties(coupon, couponView);

        return RestResult.success(couponView);
    }
}

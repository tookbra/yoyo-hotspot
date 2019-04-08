package com.mars.yoyo.hotspot.admin.service.impl;

import com.mars.yoyo.hotspot.admin.dao.RedEnvelopeItemMapper;
import com.mars.yoyo.hotspot.admin.dao.RedEnvelopeMapper;
import com.mars.yoyo.hotspot.admin.entity.RedEnvelope;
import com.mars.yoyo.hotspot.admin.entity.RedEnvelopeItem;
import com.mars.yoyo.hotspot.admin.enums.DeleteStatusEnum;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.QueryItemRedParameter;
import com.mars.yoyo.hotspot.admin.params.RedEnvelopeParameter;
import com.mars.yoyo.hotspot.admin.resutls.RedEnvelopeItemView;
import com.mars.yoyo.hotspot.admin.resutls.RedEnvelopeView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.admin.service.RedEnvelopeService;
import com.mars.yoyo.hotspot.result.RestResult;
import com.mars.yoyo.hotspot.util.DateUtil;
import com.mars.yoyo.hotspot.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 红包接口实现
 *
 * @author admin
 * @create 2018/5/17
 */
@Slf4j
@Service("redEnvelopeService")
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class RedEnvelopeServiceImpl implements RedEnvelopeService {

    @Autowired
    private RedEnvelopeMapper redEnvelopeMapper;

    @Autowired
    RedEnvelopeItemMapper redEnvelopeItemMapper;

    @Override
    public ListResultEx<RedEnvelopeView> getRedEnvelopeList(CommonParameter parameter) {
        ListResultEx<RedEnvelopeView> resultEx = new ListResultEx<>();
        // 查询红包记录
        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        Example example = new Example(RedEnvelope.class);
        example.setOrderByClause(" create_time desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deleted", DeleteStatusEnum.UNDELETE.getCode());
        if (StringUtils.isNoneBlank(parameter.getSearchText())) {
            criteria.andEqualTo("money", parameter.getSearchText());
        }
        List<RedEnvelope> redEnvelopeList = redEnvelopeMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = redEnvelopeMapper.selectCountByExample(example);

        // 设置红包结果集
        List<RedEnvelopeView> redEnvelopeViewList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(redEnvelopeList)) {
            RedEnvelopeView redEnvelopeView;
            for (RedEnvelope redEnvelope : redEnvelopeList) {
                redEnvelopeView = new RedEnvelopeView();
                BeanUtils.copyProperties(redEnvelope, redEnvelopeView);
                redEnvelopeViewList.add(redEnvelopeView);
            }
        }
        resultEx.setDataList(redEnvelopeViewList);
        resultEx.setTotalCount(total);
        return resultEx.makeSuccessResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult addRedEnvelope(RedEnvelopeParameter parameter) {
        // 参数判断
        if (null == parameter.getMoney()) {
            return RestResult.error("红包金额不能为空");
        }

        if (null == parameter.getCount()) {
            return RestResult.error("红包数量不能为空");
        }

        BigDecimal money = new BigDecimal(parameter.getMoney());
        if (parameter.getCount() / 100 > money.intValue()) {
            return RestResult.error("红包金额太小, 不足每个人分到1分钱");
        }

        RedEnvelope redEnvelope = new RedEnvelope();
        BeanUtils.copyProperties(parameter, redEnvelope);
        redEnvelope.setMoney(money);
        redEnvelope.setBeginTime(DateUtil.parseDate(parameter.getBeginTime(), "yyyy-MM-dd"));
        redEnvelope.setEndTime(DateUtil.parseDate(parameter.getEndTime(), "yyyy-MM-dd"));

        Date now = new Date();
        redEnvelope.setCreateTime(now);
        redEnvelope.setModifyTime(now);
        redEnvelopeMapper.insertSelective(redEnvelope);

        // 判断是否生成小红包
        if (parameter.getIsGenerate() == 1) {
            List<RedEnvelopeItem> redEnvelopeItemList = this.generateItem(redEnvelope.getId(), parameter.getCount(), Double.valueOf(parameter.getMoney()) * 100);
            if (CollectionUtils.isNotEmpty(redEnvelopeItemList)) {
                redEnvelopeItemMapper.insertList(redEnvelopeItemList);
            } else {
                return RestResult.error("红包生成失败");
            }
        }

        return RestResult.success("添加红包完成");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult deleteRedEnvelope(Integer redEnvelopeId) {
        // 参数判断
        if (null == redEnvelopeId) {
            return RestResult.error("参数不能为空");
        }

        // 查询红包记录
        RedEnvelope redEnvelope = redEnvelopeMapper.selectByPrimaryKey(redEnvelopeId);
        if (null == redEnvelope) {
            return RestResult.error("红包记录不存在");
        }

        redEnvelope.setDeleted(DeleteStatusEnum.DELETED.getCode());
        // 设置红包为不可用
        redEnvelope.setState(0);
        redEnvelope.setModifyTime(new Date());

        redEnvelopeMapper.updateByPrimaryKeySelective(redEnvelope);

        return RestResult.success("删除红包完成");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResult updateRedEnvelope(RedEnvelopeParameter parameter) {
        // 参数判断
        if (null == parameter.getId()) {
            return RestResult.error("参数不能为空");
        }
        // 查询红包记录
        RedEnvelope redEnvelope = redEnvelopeMapper.selectByPrimaryKey(parameter.getId());
        if (null == redEnvelope) {
            return RestResult.error("红包记录不存在");
        }

        // 判断红包是否生成
        List<RedEnvelopeItem> redEnvelopeItemList = new ArrayList<>();
        if (redEnvelope.getIsGenerate() == 0 && parameter.getIsGenerate() == 1) {
            redEnvelopeItemList = this.generateItem(redEnvelope.getId(), parameter.getCount(), Double.valueOf(parameter.getMoney()) * 100);
        }

        // 设置更新的参数
        BeanUtils.copyProperties(parameter, redEnvelope);
        String money = parameter.getMoney().replace(",", "");
        redEnvelope.setMoney(new BigDecimal(money));
        redEnvelope.setBeginTime(DateUtil.parseDate(parameter.getBeginTime(), "yyyy-MM-dd"));
        redEnvelope.setEndTime(DateUtil.parseDate(parameter.getEndTime(), "yyyy-MM-dd"));
        redEnvelope.setModifyTime(new Date());
        redEnvelopeMapper.updateByPrimaryKeySelective(redEnvelope);

        // 设置小红包数据
        if (CollectionUtils.isNotEmpty(redEnvelopeItemList)) {
            redEnvelopeItemMapper.insertList(redEnvelopeItemList);
        } else {
            return RestResult.error("红包生成失败");
        }

        return RestResult.success("更新红包完成");
    }

    @Override
    public RestResult getRedEnvelopeById(Integer redEnvelopeId) {
        RedEnvelope redEnvelope = redEnvelopeMapper.selectByPrimaryKey(redEnvelopeId);
        if (null == redEnvelope) {
            return RestResult.error("红包记录不存在");
        }
        RedEnvelopeView redEnvelopeView = new RedEnvelopeView();
        BeanUtils.copyProperties(redEnvelope, redEnvelopeView);

        return RestResult.success(redEnvelopeView);
    }

    public List<RedEnvelopeItem> generateItem(int redId, int count, double amount) {
        List<RedEnvelopeItem> redEnvelopeItemList = new ArrayList<>();
        double[] result = new double[count];
        double[] randNumber = new double[count];
        //随机数和
        double randSum = 0;
        //按每人一分钱的计划扣出来，剩下的再分配
        double allocateSum = amount - count;
        for (int i = 0; i < randNumber.length; i++) {
            //生成随机数
            randNumber[i] = Math.random() * allocateSum;
            randSum +=randNumber[i];
        }
        double left = allocateSum;
        for (int i = 0; i < result.length-1; i++) {
            result[i] = Math.round(randNumber[i] / randSum * allocateSum);
            left -= result[i];
            //把一分钱加回去
            result[i]=(result[i] + 1) * 1.0 / 100;
        }
        result[count-1] = (left + 1) * 1.0 / 100;

        Arrays.stream(result).forEach(item -> {
            RedEnvelopeItem redEnvelopeItem = new RedEnvelopeItem();
            redEnvelopeItem.setRedEnvelopeId(redId);
            redEnvelopeItem.setAmount(new BigDecimal(item));
            redEnvelopeItem.setIsReceive(0);
            redEnvelopeItemList.add(redEnvelopeItem);
        });
        return redEnvelopeItemList;
    }

    @Override
    public ListResultEx<RedEnvelopeItemView> listItemRedEnvelopes(QueryItemRedParameter parameter) {
        ListResultEx<RedEnvelopeItemView> resultEx = new ListResultEx<>();

        // 设置查询参数
        Example example = new Example(RedEnvelopeItem.class);
        Example.Criteria criteria = example.createCriteria();
        if (parameter.getRedEnvelopeId() != null) {
            criteria.andEqualTo("redEnvelopeId", parameter.getRedEnvelopeId());
        }
        if (parameter.getIsReceive() != null) {
            criteria.andEqualTo("isReceive", parameter.getIsReceive());
        }
        if (StringUtil.isNoneBlank(parameter.getAmount())) {
            criteria.andEqualTo("amount", parameter.getAmount());
        }
        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());

        // 查询列表
        List<RedEnvelopeItem> redEnvelopeItemList = redEnvelopeItemMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = redEnvelopeItemMapper.selectCountByExample(example);

        // 设置返回参数
        List<RedEnvelopeItemView> redEnvelopeItemViewList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(redEnvelopeItemList)) {
            RedEnvelopeItemView redEnvelopeItemView;
            for (RedEnvelopeItem redEnvelopeItem : redEnvelopeItemList) {
                redEnvelopeItemView = new RedEnvelopeItemView();
                redEnvelopeItemView.setId(redEnvelopeItem.getId());
                redEnvelopeItemView.setReceived(redEnvelopeItem.getIsReceive());
                redEnvelopeItemView.setAmount(redEnvelopeItem.getAmount());

                // 查询大红包信息
                RedEnvelope redEnvelope = redEnvelopeMapper.selectByPrimaryKey(redEnvelopeItem.getRedEnvelopeId());
                if (redEnvelope != null) {
                    redEnvelopeItemView.setMoney(redEnvelope.getMoney());
                    redEnvelopeItemView.setState(redEnvelope.getState());
                    redEnvelopeItemView.setChannel(redEnvelope.getChannel());
                    redEnvelopeItemView.setBeginTime(redEnvelope.getBeginTime());
                    redEnvelopeItemView.setEndTime(redEnvelope.getEndTime());
                }
                redEnvelopeItemViewList.add(redEnvelopeItemView);
            }
        }
        resultEx.setDataList(redEnvelopeItemViewList);
        resultEx.setTotalCount(total);
        return resultEx.makeSuccessResult();
    }
}

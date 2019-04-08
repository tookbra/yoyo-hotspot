package com.mars.yoyo.hotspot.admin.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mars.yoyo.hotspot.admin.dao.RedEnvelopeItemMapper;
import com.mars.yoyo.hotspot.admin.dao.RedEnvelopeMapper;
import com.mars.yoyo.hotspot.admin.dao.UserMapper;
import com.mars.yoyo.hotspot.admin.dao.UserRedEnvelopeMapper;
import com.mars.yoyo.hotspot.admin.entity.RedEnvelope;
import com.mars.yoyo.hotspot.admin.entity.RedEnvelopeItem;
import com.mars.yoyo.hotspot.admin.entity.User;
import com.mars.yoyo.hotspot.admin.entity.UserRedEnvelope;
import com.mars.yoyo.hotspot.admin.enums.DeleteStatusEnum;
import com.mars.yoyo.hotspot.admin.params.PageParameter;
import com.mars.yoyo.hotspot.admin.resutls.UserRedEnvelopeView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.UserRedEnvelopeService;
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
 * 用户红包接口
 *
 * @author admin
 * @create 2018/5/16
 */
@Slf4j
@Service("userRedEnvelopeService")
public class UserRedEnvelopeServiceImpl implements UserRedEnvelopeService {

    @Autowired
    private UserRedEnvelopeMapper userRedEnvelopeMapper;

    @Autowired
    private RedEnvelopeMapper redEnvelopeMapper;

    @Autowired
    private RedEnvelopeItemMapper redEnvelopeItemMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ListResultEx<UserRedEnvelopeView> getUserRedEnvelopeList(Integer userId, PageParameter parameter) {
        ListResultEx<UserRedEnvelopeView> resultEx = new ListResultEx<>();

        // 参数判定
        if (null == userId) {
            return resultEx.makeFailedResult("参数不能为空");
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (null == user) {
            return resultEx.makeFailedResult("会员不存在");
        }

        // 查询用户的红包
        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        Example example = new Example(UserRedEnvelope.class);
        example.setOrderByClause("create_time desc");
        Example.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andEqualTo("userId", userId);
        }
        List<UserRedEnvelope> userRedEnvelopeList = userRedEnvelopeMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer totalCount = userRedEnvelopeMapper.selectCountByExample(example);

        // 获取到红包信息
        Map<Integer, RedEnvelope> redEnvelopeMap =  this.getRedEnvelopeMaps(userRedEnvelopeList);
        Map<Integer, RedEnvelopeItem> redEnvelopeItemMap = this.getRedEnvelopeItemMaps(userRedEnvelopeList);

        // 设置用户红包返回结果集
        List<UserRedEnvelopeView> userRedEnvelopeViewList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userRedEnvelopeList)) {
            UserRedEnvelopeView userRedEnvelopeView;
            for (UserRedEnvelope userRedEnvelope : userRedEnvelopeList) {
                userRedEnvelopeView = new UserRedEnvelopeView();
                // 设置用户红包
                BeanUtils.copyProperties(userRedEnvelope, userRedEnvelopeView);

                // 获取红包信息
                if (redEnvelopeMap != null && redEnvelopeMap.get(userRedEnvelope.getRedEnvelopeId()) != null) {
                    RedEnvelope redEnvelope = redEnvelopeMap.get(userRedEnvelope.getRedEnvelopeId());
                    userRedEnvelopeView.setState(redEnvelope.getState());
                    userRedEnvelopeView.setChannel(redEnvelope.getChannel());
                    userRedEnvelopeView.setBeginTime(redEnvelope.getBeginTime());
                    userRedEnvelopeView.setEndTime(redEnvelope.getEndTime());
                    // 设置子红包信息
                    if (redEnvelopeItemMap != null && redEnvelopeItemMap.get(userRedEnvelope.getRedItemId()) != null) {
                        RedEnvelopeItem redEnvelopeItem = redEnvelopeItemMap.get(userRedEnvelope.getRedItemId());
                        userRedEnvelopeView.setReceived(redEnvelopeItem.getIsReceive());
                    }
                } else {
                    continue;
                }
                userRedEnvelopeViewList.add(userRedEnvelopeView);
            }
        }
        resultEx.setDataList(userRedEnvelopeViewList);
        resultEx.setTotalCount(totalCount);

        return resultEx.makeSuccessResult();
    }

    /**
     * 查询总的红包信息
     * @param userRedEnvelopeList
     * @return
     */
    private Map<Integer, RedEnvelope> getRedEnvelopeMaps(List<UserRedEnvelope> userRedEnvelopeList) {
        // 抽取红包redEnvelopeId
        List<Integer> redEnvelopeIds = Lists.transform(userRedEnvelopeList, new Function<UserRedEnvelope, Integer>() {
            @Override
            public Integer apply(@NullableDecl UserRedEnvelope userRedEnvelope) {
                return Integer.valueOf(userRedEnvelope.getRedEnvelopeId());
            }
        });

        // 查询红包的信息
        Example example = new Example(RedEnvelope.class);
        example.createCriteria().andEqualTo("deleted", DeleteStatusEnum.UNDELETE.getCode()).andIn("id", redEnvelopeIds);
        List<RedEnvelope> redEnvelopeList = redEnvelopeMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(redEnvelopeList)) {
            return null;
        }

        // 将红包集合转换为map
        Map<Integer, RedEnvelope> redEnvelopeMap = Maps.uniqueIndex(redEnvelopeList.iterator(), new Function<RedEnvelope, Integer>() {
            @Override
            public Integer apply(@NullableDecl RedEnvelope redEnvelope) {
                return redEnvelope.getId();
            }
        });

        return redEnvelopeMap;
    }

    /**
     * 查询子红包信息
     * @param userRedEnvelopeList
     * @return
     */
    private Map<Integer, RedEnvelopeItem> getRedEnvelopeItemMaps(List<UserRedEnvelope> userRedEnvelopeList) {
        // 抽取红包redEnvelopeId
        List<Integer> redItemIds = Lists.transform(userRedEnvelopeList, new Function<UserRedEnvelope, Integer>() {
            @Override
            public Integer apply(@NullableDecl UserRedEnvelope userRedEnvelope) {
                return Integer.valueOf(userRedEnvelope.getRedItemId());
            }
        });

        // 查询红包的信息
        Example example = new Example(RedEnvelopeItem.class);
        example.createCriteria().andIn("id", redItemIds);
        List<RedEnvelopeItem> redEnvelopeItemList = redEnvelopeItemMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(redEnvelopeItemList)) {
            return null;
        }

        // 将红包集合转换为map
        Map<Integer, RedEnvelopeItem> redEnvelopeItemMap = Maps.uniqueIndex(redEnvelopeItemList.iterator(), new Function<RedEnvelopeItem, Integer>() {
            @Override
            public Integer apply(@NullableDecl RedEnvelopeItem redEnvelope) {
                return redEnvelope.getId();
            }
        });

        return redEnvelopeItemMap;
    }



}

package com.mars.yoyo.hotspot.admin.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mars.yoyo.hotspot.admin.dao.SmsLogMapper;
import com.mars.yoyo.hotspot.admin.dao.UserMapper;
import com.mars.yoyo.hotspot.admin.entity.SmsLog;
import com.mars.yoyo.hotspot.admin.entity.User;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.SmsLogView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.SmsLogService;
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
 * 短信
 *
 * @author admin
 * @create 2018/5/17
 */
@Slf4j
@Service
public class SmsLogServiceImpl implements SmsLogService {

    @Autowired
    private SmsLogMapper smsLogMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ListResultEx<SmsLogView> getSmsLogList(CommonParameter parameter) {
        ListResultEx<SmsLogView> resultEx = new ListResultEx<>();

        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        // 设置查询参数
        Example example = new Example(SmsLog.class);
        example.setOrderByClause("create_time desc");
        if (StringUtils.isNotBlank(parameter.getSearchText())) {
            example.createCriteria().andLike("phone", "%" + parameter.getSearchText() + "%");
        }
        // 查询短信记录
        List<SmsLog> smsLogList = smsLogMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = smsLogMapper.selectCountByExample(example);

        // 获取用户对应ID的Map
        // Map<Integer, User> userMap = this.getUserMap(smsLogList);

        // 设置结果集
        List<SmsLogView> smsLogViewList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(smsLogList)) {
            SmsLogView smsLogView;
            for (SmsLog smsLog : smsLogList) {
                smsLogView = new SmsLogView();
                BeanUtils.copyProperties(smsLog, smsLogView);
                smsLogViewList.add(smsLogView);
            }
        }

        resultEx.setDataList(smsLogViewList);
        resultEx.setTotalCount(total);
        return resultEx.makeSuccessResult();
    }

    /**
     * 获取用户对应ID的Map
     * @param smsLogList
     * @return
     */
    private Map<Integer, User> getUserMap(List<SmsLog> smsLogList) {
        // 抽取用户userId
        List<Integer> userIds = Lists.transform(smsLogList, new Function<SmsLog, Integer>() {
            @Override
            public Integer apply(@NullableDecl SmsLog smsLog) {
                return Integer.valueOf(smsLog.getUserId());
            }
        });

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

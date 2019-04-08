package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.PageParameter;
import com.mars.yoyo.hotspot.admin.resutls.UserRedEnvelopeView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;

/**
 * 用户红包
 *
 * @author admin
 * @create 2018/5/16
 */
public interface UserRedEnvelopeService {

    /**
     * 查询用户的红包列表
     * @param userId
     * @return
     */
    ListResultEx<UserRedEnvelopeView> getUserRedEnvelopeList(Integer userId, PageParameter parameter);

}

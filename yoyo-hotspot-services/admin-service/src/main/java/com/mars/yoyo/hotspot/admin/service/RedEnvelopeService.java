package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.QueryItemRedParameter;
import com.mars.yoyo.hotspot.admin.params.RedEnvelopeParameter;
import com.mars.yoyo.hotspot.admin.resutls.RedEnvelopeItemView;
import com.mars.yoyo.hotspot.admin.resutls.RedEnvelopeView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.resutls.base.ResultEx;
import com.mars.yoyo.hotspot.result.RestResult;

/**
 * 红包接口
 *
 * @author admin
 * @create 2018/5/14
 * @since 1.0.0
 */
public interface RedEnvelopeService {

    /**
     * 分页查询红包
     * @return
     */
    ListResultEx<RedEnvelopeView> getRedEnvelopeList(CommonParameter parameter);

    /**
     * 添加红包记录
     * @param parameter
     * @return
     */
    RestResult addRedEnvelope(RedEnvelopeParameter parameter);

    /**
     * 删除红包记录（逻辑删除）
     * @param redEnvelopeId
     * @return
     */
    RestResult deleteRedEnvelope(Integer redEnvelopeId);

    /**
     * 更新红包信息
     * @param parameter
     * @return
     */
    RestResult updateRedEnvelope(RedEnvelopeParameter parameter);

    /**
     * 查询红包记录
     * @param redEnvelopeId
     * @return
     */
    RestResult getRedEnvelopeById(Integer redEnvelopeId);

    /**
     * 查询小红包
     * @return
     */
    ListResultEx<RedEnvelopeItemView> listItemRedEnvelopes(QueryItemRedParameter parameter);

}

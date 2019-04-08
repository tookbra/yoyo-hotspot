package com.mars.yoyo.hotspot.mifi.dao;

import com.mars.yoyo.hotspot.mifi.domain.RedEnvelopeItem;
import com.mars.yoyo.hotspot.mybatis.MyMapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface RedEnvelopeItemMapper extends MyMapper<RedEnvelopeItem> {

    /**
     * 随机获取未被领取的红包
     * @return {@link RedEnvelopeItem}
     */
    @Select("select * from red_envelope_item where is_receive = 0 order by rand() limit 1;")
    @Results({ @Result(id = true, column = "id", property = "id"),
            @Result(column = "red_envelope_id", property = "redEnvelopeId"),
            @Result(column = "is_receive", property = "isReceive") })
    RedEnvelopeItem findByRandom();
}
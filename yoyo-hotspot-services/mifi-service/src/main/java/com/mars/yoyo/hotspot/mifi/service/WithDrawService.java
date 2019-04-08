package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.vo.WithDrawVo;

/**
 * @author tookbra
 * @date 2018/6/21
 * @description
 */
public interface WithDrawService {

    /**
     * 提现申请
     * @param userId
     * @return
     */
    WithDrawVo apply(int userId);
}

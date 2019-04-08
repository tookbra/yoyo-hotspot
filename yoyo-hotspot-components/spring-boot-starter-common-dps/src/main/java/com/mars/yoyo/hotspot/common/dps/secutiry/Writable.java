package com.mars.yoyo.hotspot.common.dps.secutiry;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tookbra
 * @date 2018/6/4
 * @description
 */
public interface Writable {

    void read(HttpServletRequest request);
}

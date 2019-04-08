package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

/**
 * 用户信息更新接口
 *
 * @author admin
 * @create 2018/5/15
 */
@Data
public class UserUpdateParameter extends SessionParameter {

    private Integer id;

    /**
     * 手机号
     */
    private String phone;

    /**
     *  国家
     */
    private String country;

    /**
     * 是否交纳押金 0 否 1 是
     */
    private Integer deposited;

    /**
     * 是否禁用 0 否 1 是
     */
    private Integer state;

}

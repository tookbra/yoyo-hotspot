package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户返回参数
 *
 * @author admin
 * @create 2018/5/14
 */
@Data
public class UserView implements Serializable {

    /**
     * 用户编号
     */
    private Integer id;

    /**
     * 会员名称
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 国家(通过区号进行判断)
     */
    private String country;

    /**
     * 是否国际版本 0否 1是
     */
    private Integer en;

    /**
     * 注册时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    /**
     * 是否交纳押金 0 否 1 是
     */
    private Integer deposited;

    /**
     * 是否禁用 0 否 1 是
     */
    private Integer state;

    /**
     * 最后登录ip
     */
    private String lastLoginIp;

}

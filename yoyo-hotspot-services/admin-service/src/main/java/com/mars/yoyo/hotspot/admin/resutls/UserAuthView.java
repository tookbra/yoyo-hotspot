package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员身份认证
 *
 * @author admin
 * @create 2018/6/1
 */
@Data
public class UserAuthView implements Serializable {

    private static final long serialVersionUID = 4384428081978980210L;

    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 会员名称
     */
    private String userName;

    /**
     * 身份类型1 phone 2 wechat 3 qq 4 weibo
     */
    private Integer authType;

    /**
     * 身份唯一标识
     */
    private String openId;

    /**
     * 密码或token
     */
    private String secret;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

}

package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 管理员结果参数
 *
 * @author admin
 * @create 2018/5/18
 */
@Data
public class AdminView implements Serializable {

    private static final long serialVersionUID = 28769420204910547L;

    /**
     * id
     */
    private Integer id;

    /**
     * 管理员名称
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}

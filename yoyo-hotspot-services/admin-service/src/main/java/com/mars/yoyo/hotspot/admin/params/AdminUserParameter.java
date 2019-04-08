package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

/**
 * 管理类参数
 *
 * @author admin
 * @create 2018/5/8
 */
@Data
public class AdminUserParameter {

    /**
     * id
     */
    private Integer id;

    /**
     * 管理员名称
     */
    private String name;

    /**
     * 管理员密码
     */
    private String password;

    /**
     * 角色
     */
    private Integer roleId;

}

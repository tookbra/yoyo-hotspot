package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

import java.io.Serializable;

/**
 * 登陆
 *
 * @author admin
 * @create 2018/5/9
 */
@Data
public class LoginParameter implements Serializable {

    /**
     * 管理员名称
     */
    private String name;

    /**
     * 密码
     */
    private String password;

}

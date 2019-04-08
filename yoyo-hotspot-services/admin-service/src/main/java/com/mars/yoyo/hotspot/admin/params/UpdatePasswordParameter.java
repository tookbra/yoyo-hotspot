package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

/**
 * 修改密码
 *
 * @author admin
 * @create 2018/5/22
 */
@Data
public class UpdatePasswordParameter extends SessionParameter {

    /**
     * 原密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 确认密码
     */
    private String confirmPassword;

}

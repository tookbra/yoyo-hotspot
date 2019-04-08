package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

import java.io.Serializable;

/**
 * session
 *
 * @author admin
 * @create 2018/9/2
 */
@Data
public class SessionParameter implements Serializable {

    /**
     * 缓存对象
     */
    private String sessionIdentity;

}

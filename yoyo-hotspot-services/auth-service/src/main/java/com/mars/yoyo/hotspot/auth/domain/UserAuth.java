package com.mars.yoyo.hotspot.auth.domain;

import lombok.Data;

/**
 * @author tookbra
 * @date 2018/6/2
 * @description
 */
@Data
public class UserAuth {

    private String userId;

    private String secret;

    private String openId;
}

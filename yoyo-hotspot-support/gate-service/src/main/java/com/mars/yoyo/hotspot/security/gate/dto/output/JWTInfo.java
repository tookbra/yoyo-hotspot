package com.mars.yoyo.hotspot.security.gate.dto.output;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/5/25
 * @description
 */
@Data
public class JWTInfo implements Serializable {
    private String username;
    private String userId;
    private String name;
}

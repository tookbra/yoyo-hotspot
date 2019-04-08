package com.mars.yoyo.hotspot.ms.dto.input;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
@Data
public class SmsDto implements Serializable {

    private String name;

    private String pwd;

    private String phone;

    private String content;

    private String mttime;

    private int rpttype = 1;

    private String phonePre;
}

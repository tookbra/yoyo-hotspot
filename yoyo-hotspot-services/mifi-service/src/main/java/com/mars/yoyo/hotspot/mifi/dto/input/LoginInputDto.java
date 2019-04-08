package com.mars.yoyo.hotspot.mifi.dto.input;

import com.mars.yoyo.hotspot.dto.input.RequestDto;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
@Data
@ToString
public class LoginInputDto extends RequestDto {

    private static final long serialVersionUID = 1629695643113316797L;


    @NotNull(message = "{user.phone.not.blank}")
    private String username;

    @NotNull(message = "{sms.captcha.not.blank}")
    private String captcha;

    private String requestIp;

//    @NotNull(message = "{illegal.param}")
    private String deliveryChannel;

    private String countryAreaCode;

    private String code;

//    @NotNull(message = "{illegal.param}")
    private String lang;
}

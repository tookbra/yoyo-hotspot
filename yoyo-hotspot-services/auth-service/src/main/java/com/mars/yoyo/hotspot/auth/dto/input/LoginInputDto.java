package com.mars.yoyo.hotspot.auth.dto.input;

import com.mars.yoyo.hotspot.annotation.hibernate.validate.PhoneNum;
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
//    @PhoneNum(message = "{phone.Illegal.error}")
    private String username;

    @NotNull(message = "{sms.captcha.not.blank}")
    private String captcha;

    private String requestIp;

    private String deliveryChannel;

    /**
     * 国际区号
     */
    private String countryAreaCode;

    private String code;
    /**
     * 用户语言
     */
    private String lang;

}

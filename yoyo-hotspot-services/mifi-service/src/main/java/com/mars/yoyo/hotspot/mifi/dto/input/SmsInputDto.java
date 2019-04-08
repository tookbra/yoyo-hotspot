package com.mars.yoyo.hotspot.mifi.dto.input;

import com.mars.yoyo.hotspot.annotation.hibernate.validate.PhoneNum;
import com.mars.yoyo.hotspot.dto.input.RequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
@Data
@ToString
public class SmsInputDto extends RequestDto {

    private static final long serialVersionUID = 1629695643113316797L;


    @NotNull
    @PhoneNum(message = "{phone.Illegal.error}")
    private String phone;

    @Max(1000)
    @ApiModelProperty(value = "短信内容", example = "test", required = true)
    private String content;

    /**
     * 短信类型 reg:注册；return：归还：maturity：到期;
     */
    @NotNull
    @ApiModelProperty(value = "短信类型", example = "reg", required = true)
    private String type;

    @ApiModelProperty(value = "替换参数")
    private Map<String, Object> paramMap;

}

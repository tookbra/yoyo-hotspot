package com.mars.yoyo.hotspot.annotation.hibernate.validate;


import com.mars.yoyo.hotspot.annotation.hibernate.validate.def.PhoneNumDef;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * 手机号码验证
 * @author tookbra
 * @date 2018/6/1
 * @description
 */
@Documented
@Constraint(validatedBy = { PhoneNumDef.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface PhoneNum {
	String message() default "参数非法";

	String label() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}

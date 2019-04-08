package com.mars.yoyo.hotspot.annotation.hibernate.validate.def;


import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.mars.yoyo.hotspot.annotation.hibernate.validate.PhoneNum;
import com.mars.yoyo.hotspot.util.PhoneUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 手机号码验证
 * @author tookbra
 * @date 2018/6/1
 * @description
 */
public class PhoneNumDef implements ConstraintValidator<PhoneNum, String> {

	@Override
	public void initialize(PhoneNum constraintAnnotation) {
	}

	@Override
	public boolean isValid(String num, ConstraintValidatorContext arg1) {
		if (StringUtils.isBlank(num)) {
			return false;
		}
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		try {
			Phonenumber.PhoneNumber number = phoneUtil.parseAndKeepRawInput(num, "");
			boolean isNumberValid = phoneUtil.isValidNumber(number);
			return isNumberValid;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}

package com.mars.yoyo.hotspot.common.dps.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author tookbra
 * @date 2018/6/15
 * @description
 */
public class MessageSourceHandler {

    @Autowired
    private MessageSource messageSource;

    /**
     * @param messageKey
     * @return
     */
    public String getMessage(String messageKey) {
        String message = messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
        return message;
    }
}

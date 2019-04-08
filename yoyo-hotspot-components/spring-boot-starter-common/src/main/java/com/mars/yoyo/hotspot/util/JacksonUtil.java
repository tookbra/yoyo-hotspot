package com.mars.yoyo.hotspot.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author tookbra
 * @date 2018/5/24
 * @description
 */
public class JacksonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> String stringify(T bean) {
        StringWriter sw = new StringWriter();
        try {
            objectMapper.writeValue(sw, bean);
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <T> String parse(T obj) {
        try {
            if (obj == null) {
                return null;
            }
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            Throwables.throwIfUnchecked(e);
            throw new IllegalArgumentException(e);
        }
    }
}

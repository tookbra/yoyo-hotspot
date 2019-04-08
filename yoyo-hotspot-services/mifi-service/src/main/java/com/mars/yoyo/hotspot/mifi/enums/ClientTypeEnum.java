package com.mars.yoyo.hotspot.mifi.enums;

import org.bouncycastle.util.Strings;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author tookbra
 * @date 2018/6/4
 * @description
 */
public enum ClientTypeEnum {

    H5,NATIVE,MP;

    public static ClientTypeEnum of(String value) {
        Optional<ClientTypeEnum> clientTypeEnum = Arrays.stream(ClientTypeEnum.values())
                .filter(v -> Objects.equals(v.name(), Strings.toUpperCase(value)))
                .findFirst();
        return clientTypeEnum.orElseThrow(() -> new EntityNotFoundException(""));
    }
}

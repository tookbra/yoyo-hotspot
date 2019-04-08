package com.mars.yoyo.hotspot.mifi.enums;

/**
 * @author tookbra
 * @date 2018/10/29
 * @description
 */
public enum FlowPackageEnum {
    HOUR(528),
    DAY(529),
    MONTH(530),
    HOUR_EN(525),
    DAY_EN(526),
    MONTH_EN(527);

    private Integer pkgId;

    FlowPackageEnum(Integer pkgId) {
        this.pkgId = pkgId;
    }

    public Integer getPkgId() {
        return pkgId;
    }
}

package com.mars.yoyo.hotspot.mifi.dto;

import com.mars.yoyo.hotspot.mifi.domain.Lease;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author tookbra
 * @date 2018/8/29
 * @description
 */
@Data
@ToString
public class LeaseDto extends Lease {

    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

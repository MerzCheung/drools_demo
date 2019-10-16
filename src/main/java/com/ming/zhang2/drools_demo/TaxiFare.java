package com.ming.zhang2.drools_demo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxiFare {
    private BigDecimal  nightSurcharge;
    private BigDecimal  rideFare;
    public BigDecimal  total() {
        return this.nightSurcharge.add(this.rideFare);
    }
}


package com.ming.zhang2.drools_demo;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxiRide {
    private Boolean isNightSurcharge;
    private BigDecimal distanceInMile;
}

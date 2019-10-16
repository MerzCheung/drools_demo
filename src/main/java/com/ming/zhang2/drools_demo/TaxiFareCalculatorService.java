package com.ming.zhang2.drools_demo;

import java.math.BigDecimal;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxiFareCalculatorService {
    @Autowired
    private KieContainer kieContainer;

    public BigDecimal calculateFare(TaxiRide taxiRide, TaxiFare rideFare) {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("rideFare", rideFare);
        kieSession.insert(taxiRide);
        kieSession.fireAllRules();
        kieSession.dispose();
        return rideFare.total();
    }
}

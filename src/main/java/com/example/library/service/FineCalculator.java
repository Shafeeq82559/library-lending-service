package com.example.library.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class FineCalculator {

    static final BigDecimal DAILY_RATE = new BigDecimal("0.25");

    public BigDecimal calculate(LocalDate dueOn, LocalDate returnedOn) {
        long daysLate = ChronoUnit.DAYS.between(dueOn, returnedOn);
        if (daysLate <= 0) {
            return BigDecimal.ZERO;
        }
        return DAILY_RATE.multiply(BigDecimal.valueOf(daysLate));
    }
}

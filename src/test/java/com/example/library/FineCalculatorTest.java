package com.example.library;

import com.example.library.service.FineCalculator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class FineCalculatorTest {

    private final FineCalculator calculator = new FineCalculator();
    private final LocalDate due = LocalDate.of(2026, 3, 1);

    @Test
    void onTimeReturnHasNoFine() {
        assertThat(calculator.calculate(due, due)).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void earlyReturnHasNoFine() {
        assertThat(calculator.calculate(due, due.minusDays(3))).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void lateReturnChargesPerDay() {
        assertThat(calculator.calculate(due, due.plusDays(4))).isEqualByComparingTo("1.00");
    }
}

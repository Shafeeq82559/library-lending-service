package com.example.library.dto;

import com.example.library.model.PatronStanding;
import jakarta.validation.constraints.NotNull;

public record StandingChangeRequest(
        @NotNull PatronStanding standing,
        String note
) {
}

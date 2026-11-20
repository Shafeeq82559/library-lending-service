package com.example.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateBookRequest(
        @NotBlank @Pattern(regexp = "^(97[89])?\\d{9}[\\dX]$", message = "must be a 10 or 13 digit ISBN") String isbn,
        @NotBlank String title,
        @NotBlank String author,
        String genre,
        Integer publishedYear,
        @Min(1) int copies
) {
}

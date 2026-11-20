package com.example.library.dto;

import java.time.LocalDate;
import java.util.Map;

public record CatalogReport(
        LocalDate generatedOn,
        int totalTitles,
        int totalCopies,
        int copiesOnShelf,
        Map<String, Integer> titlesByGenre
) {
}

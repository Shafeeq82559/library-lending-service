package com.example.library.service;

import com.example.library.dto.CatalogReport;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class CatalogReportService {

    private final BookRepository bookRepository;
    private final Clock clock;

    public CatalogReportService(BookRepository bookRepository, Clock clock) {
        this.bookRepository = bookRepository;
        this.clock = clock;
    }

    @Transactional(readOnly = true)
    public CatalogReport generateReport() {
        List<Book> books = bookRepository.findAll();
        int totalCopies = 0;
        int onShelf = 0;
        Map<String, Integer> byGenre = new TreeMap<>();
        for (Book book : books) {
            totalCopies += book.getTotalCopies();
            onShelf += book.getAvailableCopies();
            String genre = book.getGenre() == null ? "Uncategorized" : book.getGenre();
            byGenre.merge(genre, 1, Integer::sum);
        }
        return new CatalogReport(LocalDate.now(clock), books.size(), totalCopies, onShelf, byGenre);
    }
}

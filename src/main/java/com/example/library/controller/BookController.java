package com.example.library.controller;

import com.example.library.dto.CatalogReport;
import com.example.library.dto.CreateBookRequest;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import com.example.library.service.CatalogReportService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final CatalogReportService reportService;

    public BookController(BookService bookService, CatalogReportService reportService) {
        this.bookService = bookService;
        this.reportService = reportService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@Valid @RequestBody CreateBookRequest request) {
        return bookService.addBook(request);
    }

    @GetMapping
    public List<Book> listBooks() {
        return bookService.listBooks();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @GetMapping("/search")
    public List<Book> search(@RequestParam("q") String query) {
        return bookService.search(query);
    }

    @GetMapping("/report")
    public CatalogReport report() {
        return reportService.generateReport();
    }
}

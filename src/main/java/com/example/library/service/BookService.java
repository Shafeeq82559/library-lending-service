package com.example.library.service;

import com.example.library.dto.CreateBookRequest;
import com.example.library.exception.BusinessRuleException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Book addBook(CreateBookRequest request) {
        if (bookRepository.existsByIsbn(request.isbn())) {
            throw BusinessRuleException.conflict("A book with ISBN " + request.isbn() + " is already in the catalog");
        }
        Book book = new Book(request.isbn(), request.title(), request.author(),
                request.genre(), request.publishedYear(), request.copies());
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> BusinessRuleException.notFound("Book", id));
    }

    @Transactional(readOnly = true)
    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Book> search(String query) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query);
    }
}

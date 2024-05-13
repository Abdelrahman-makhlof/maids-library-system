package com.maids.library.management.system.service;

import com.maids.library.management.system.exceptionhandler.dto.BookNotFoundException;
import com.maids.library.management.system.model.Book;
import com.maids.library.management.system.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Cacheable("books")
    public List<Book> getAllBooks() {
        log.info("Get from Database");
        return bookRepository.findAll();
    }

    @Cacheable(value = "books", key = "#id")
    public Book getBookById(Long id) {
        log.info("Get from Database");
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElse(null);
    }

    @CacheEvict(value = "books", allEntries = true)
    public Long addBook(Book book) {
        return bookRepository.save(book).getId();

    }

    @CacheEvict(value = "books", key = "#id")
    public void updateBook(Long id, Book book) throws BookNotFoundException {
        var existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            book.setId(id);
            book.setAvailableForBorrowing(existingBook.get().isAvailableForBorrowing());
            bookRepository.save(book);
        } else {
            throw new BookNotFoundException("Book " + id + " is not found");
        }
    }

    @CacheEvict(value = "books", key = "#id")
    public void updateBorrowedBook(Long id, Book book) throws BookNotFoundException {
        var existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            book.setId(id);
            bookRepository.save(book);
        }
    }

    @CacheEvict(value = "books", key = "#id")
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

}

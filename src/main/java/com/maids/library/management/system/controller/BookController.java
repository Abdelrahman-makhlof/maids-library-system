package com.maids.library.management.system.controller;

import com.maids.library.management.system.dto.BookDTO;
import com.maids.library.management.system.exceptionhandler.dto.ApplicationException;
import com.maids.library.management.system.exceptionhandler.dto.BookNotFoundException;
import com.maids.library.management.system.exceptionhandler.dto.ValidationException;
import com.maids.library.management.system.model.Book;
import com.maids.library.management.system.service.BookService;
import com.maids.library.management.system.service.ValidatorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final ValidatorService validatorService;

    /**
     * Retrieves a list of all books in the library.
     *
     * @return A list of BookDTO objects representing the books.
     */
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks().
                stream().map(book -> modelMapper.map(book, BookDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id The ID of the book to retrieve.
     * @return The BookDTO object representing the book.
     * @throws BookNotFoundException If the book with the given ID is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) throws ApplicationException {
        var book = bookService.getBookById(id);
        if (book == null) {
            throw new BookNotFoundException("Book " + id + " is not found");
        }
        return new ResponseEntity<>(modelMapper.map(book, BookDTO.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) throws ValidationException {
        validatorService.validateBookDto(bookDTO);
        bookDTO.setAvailableForBorrowing(Boolean.TRUE);
        var book = modelMapper.map(bookDTO, Book.class);
        var id = bookService.addBook(book);
        bookDTO.setId(id);
        return new ResponseEntity<>(bookDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO)
            throws BookNotFoundException {
        var book = modelMapper.map(bookDTO, Book.class);
        bookService.updateBook(id, book);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

package com.maids.library.management.system.controller;

import com.maids.library.management.system.dto.BookDTO;
import com.maids.library.management.system.exceptionhandler.dto.ApplicationException;
import com.maids.library.management.system.model.Book;
import com.maids.library.management.system.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book Test 1", "Author1", 2021));
        books.add(new Book(2L, "Book Test 2", "Author2", 2022));

        when(bookService.getAllBooks()).thenReturn(books);

        List<BookDTO> bookDTOs = new ArrayList<>();
        bookDTOs.add(new BookDTO(1L, "Book Test 1", "Author1", 2021));
        bookDTOs.add(new BookDTO(2L, "Book Test 2", "Author2", 2022));
        when(modelMapper.map(any(Book.class), eq(BookDTO.class))).thenReturn(bookDTOs.get(0), bookDTOs.get(1));

        ResponseEntity<List<BookDTO>> responseEntity = bookController.getAllBooks();

        verify(bookService, times(1)).getAllBooks();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
        assertEquals("Book Test 1", responseEntity.getBody().get(0).getTitle());
        assertEquals("Author1", responseEntity.getBody().get(0).getAuthor());
        assertEquals(2021, responseEntity.getBody().get(0).getPublicationYear());
        assertEquals("Book Test 2", responseEntity.getBody().get(1).getTitle());
        assertEquals("Author2", responseEntity.getBody().get(1).getAuthor());
        assertEquals(2022, responseEntity.getBody().get(1).getPublicationYear());
    }

    @Test
    void testGetBookById() throws ApplicationException {
        Book book = new Book(1L, "Book Test 1", "Author1", 2021);
        when(bookService.getBookById(1L)).thenReturn(book);


        BookDTO bookDTO = new BookDTO(1L, "Book Test 1", "Author1", 2021);
        when(modelMapper.map(book, BookDTO.class)).thenReturn(bookDTO);

        ResponseEntity<BookDTO> responseEntity = bookController.getBookById(1L);

        verify(bookService, times(1)).getBookById(1L);

        // Verify response entity and data
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Book Test 1", responseEntity.getBody().getTitle());
        assertEquals("Author1", responseEntity.getBody().getAuthor());
        assertEquals(2021, responseEntity.getBody().getPublicationYear());
    }

}

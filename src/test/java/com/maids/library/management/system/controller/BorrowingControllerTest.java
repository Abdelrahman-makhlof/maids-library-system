package com.maids.library.management.system.controller;

import com.maids.library.management.system.dto.BorrowingRecordDTO;
import com.maids.library.management.system.exceptionhandler.dto.ApplicationException;
import com.maids.library.management.system.model.Book;
import com.maids.library.management.system.model.BorrowingRecord;
import com.maids.library.management.system.model.Patron;
import com.maids.library.management.system.service.BorrowingRecordService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BorrowingControllerTest {

    @Mock
    private BorrowingRecordService borrowingRecordService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BorrowingController borrowingController;

    @Test
    void testBorrowBook() throws ApplicationException {
        Book book =  new Book(1L, "Book Test 1", "Author1", 2021);
        Patron patron = new Patron(1L,"Patron 1","Patron 1222");
        BorrowingRecord borrowedBook = new BorrowingRecord(1L, book, patron);
        when(borrowingRecordService.borrowBook(1L, 1L)).thenReturn(borrowedBook);

        when(modelMapper.map(borrowedBook, BorrowingRecord.class)).thenReturn(borrowedBook);
        ResponseEntity<BorrowingRecordDTO> responseEntity = borrowingController.borrowBook(1L, 1L);

        verify(borrowingRecordService, times(1)).borrowBook(1L, 1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getBook().getId());
        assertEquals(1L, responseEntity.getBody().getPatron().getId());
        assertEquals(1L, responseEntity.getBody().getId());
    }

    @Test
    void testReturnBook() throws ApplicationException {
        Book book =  new Book(1L, "Book Test 1", "Author1", 2021);
        Patron patron = new Patron(1L,"Patron 1","Patron 1222");
        var returnedRecord = new BorrowingRecord(1L, book, patron);

        when(borrowingRecordService.returnBook(1L, 1L)).thenReturn(returnedRecord);

        when(modelMapper.map(returnedRecord, BorrowingRecord.class)).thenReturn(returnedRecord);

        ResponseEntity<BorrowingRecordDTO> responseEntity = borrowingController.returnBook(1L, 1L);

        verify(borrowingRecordService, times(1)).returnBook(1L, 1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getBook().getId());
        assertEquals(1L, responseEntity.getBody().getPatron().getId());
        assertEquals(1L, responseEntity.getBody().getId());
    }

}


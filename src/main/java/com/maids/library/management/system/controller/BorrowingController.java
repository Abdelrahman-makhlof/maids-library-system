package com.maids.library.management.system.controller;

import com.maids.library.management.system.dto.BorrowingRecordDTO;
import com.maids.library.management.system.exceptionhandler.dto.ApplicationException;
import com.maids.library.management.system.service.BorrowingRecordService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BorrowingController {

    private final BorrowingRecordService borrowingRecordService;

    private final ModelMapper modelMapper;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDTO> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) throws ApplicationException {

        var borrowedBook = borrowingRecordService.borrowBook(bookId, patronId);
        return ResponseEntity.ok().body(modelMapper.map(borrowedBook, BorrowingRecordDTO.class));
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDTO> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) throws ApplicationException {

        var returnedRecord = borrowingRecordService.returnBook(bookId, patronId);
        return ResponseEntity.ok().body(modelMapper.map(returnedRecord, BorrowingRecordDTO.class));

    }

}
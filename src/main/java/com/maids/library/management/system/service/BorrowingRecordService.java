package com.maids.library.management.system.service;

import com.maids.library.management.system.exceptionhandler.dto.ApplicationException;
import com.maids.library.management.system.exceptionhandler.dto.BookNotFoundException;
import com.maids.library.management.system.exceptionhandler.dto.NotAvailableException;
import com.maids.library.management.system.model.Book;
import com.maids.library.management.system.model.BorrowingRecord;
import com.maids.library.management.system.model.Patron;
import com.maids.library.management.system.repository.BorrowingRecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BorrowingRecordService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookService bookService;
    private final PatronService patronService;
    private final ModelMapper modelMapper;

    public List<BorrowingRecord> getAllBorrowingRecords() {
        return borrowingRecordRepository.findAll();
    }

    public BorrowingRecord getBorrowingRecordById(Long id) {
        Optional<BorrowingRecord> optionalRecord = borrowingRecordRepository.findById(id);
        return optionalRecord.orElse(null);
    }

    public BorrowingRecord addBorrowingRecord(BorrowingRecord record) {
        return borrowingRecordRepository.save(record);
    }

    public BorrowingRecord updateBorrowingRecord(Long id, BorrowingRecord record) {
        if (borrowingRecordRepository.existsById(id)) {
            record.setId(id);
            return borrowingRecordRepository.save(record);
        }
        return null;
    }

    public BorrowingRecord getBorrowingRecordByBookAndPatron(Book book, Patron patron) {
        Optional<BorrowingRecord> optionalRecord = borrowingRecordRepository
                .getBorrowingRecordByBookAndPatron(book.getId(), patron.getId());
        return optionalRecord.orElse(null);
    }

    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) throws ApplicationException {
        var book = bookService.getBookById(bookId);
        var patron = patronService.getPatronById(patronId);

        if (Objects.nonNull(book) && Objects.nonNull(patron)) {
            if (book.isAvailableForBorrowing()) {
                var borrowingRecord = new BorrowingRecord();
                borrowingRecord.setBook(book);
                borrowingRecord.setPatron(patron);
                borrowingRecord.setBorrowingDate(LocalDateTime.now());

                book.setAvailableForBorrowing(Boolean.FALSE);
                bookService.updateBorrowedBook(bookId, book);
                return addBorrowingRecord(borrowingRecord);

            } else {
                throw new NotAvailableException("Book " + bookId + " is not available for borrowing");
            }
        } else if (Objects.nonNull(book)) {
            throw new BookNotFoundException("Book " + bookId + " is not found");
        } else if (Objects.nonNull(patron)) {
            throw new BookNotFoundException("Patron " + patronId + " is not found");
        }
        return null;
    }

    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) throws ApplicationException {

        var book = bookService.getBookById(bookId);
        var patron = patronService.getPatronById(patronId);

        if (Objects.nonNull(book) && Objects.nonNull(patron)) {
            BorrowingRecord borrowingRecord = getBorrowingRecordByBookAndPatron(book, patron);

            if (borrowingRecord != null && borrowingRecord.getReturnDate() == null) {
                borrowingRecord.setReturnDate(LocalDateTime.now());
                book.setAvailableForBorrowing(true);
                bookService.updateBorrowedBook(bookId, book);
                return updateBorrowingRecord(borrowingRecord.getId(), borrowingRecord);

            } else {
                throw new NotAvailableException("Book " + bookId + " is not borrowed by the patron or already returned");
            }
        }  else if (Objects.nonNull(book)) {
            throw new BookNotFoundException("Book " + bookId + " is not found");
        } else if (Objects.nonNull(patron)) {
            throw new BookNotFoundException("Patron " + patronId + " is not found");
        }
        return null;
    }

}

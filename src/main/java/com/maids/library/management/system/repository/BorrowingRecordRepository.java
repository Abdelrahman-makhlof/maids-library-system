package com.maids.library.management.system.repository;

import com.maids.library.management.system.model.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    @Query(value = "SELECT * FROM BORROWING_RECORD WHERE BOOK_ID = :bookId and PATRON_ID= :patronId", nativeQuery = true)
    Optional<BorrowingRecord> getBorrowingRecordByBookAndPatron(long bookId, long patronId);

}

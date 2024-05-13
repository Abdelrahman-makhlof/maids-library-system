package com.maids.library.management.system.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BorrowingRecordDTO {

    private Long id;
    private BookDTO book;
    private PatronDTO patron;
    private LocalDateTime borrowingDate;
    private LocalDateTime returnDate;

    public BorrowingRecordDTO(long id, long bookId, long patronId) {
    }

}

package com.maids.library.management.system.exceptionhandler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiError {

    private String message;
    private LocalDateTime time;
}

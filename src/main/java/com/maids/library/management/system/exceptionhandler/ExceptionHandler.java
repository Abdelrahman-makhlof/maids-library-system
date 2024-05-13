package com.maids.library.management.system.exceptionhandler;

import com.maids.library.management.system.exceptionhandler.dto.ApiError;
import com.maids.library.management.system.exceptionhandler.dto.ApplicationException;
import com.maids.library.management.system.exceptionhandler.dto.BookNotFoundException;
import com.maids.library.management.system.exceptionhandler.dto.NotAvailableException;
import com.maids.library.management.system.exceptionhandler.dto.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = NotAvailableException.class)
    public ResponseEntity<ApiError> handleGeneralException(NotAvailableException ex) {
        var apiError = new ApiError(ex.getMessage(), LocalDateTime.now());
        log.error("Exception {}", ex);
        log.info("Response: {}", apiError);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<ApiError> handleGeneralException(ValidationException ex) {
        var apiError = new ApiError(ex.getMessage(), LocalDateTime.now());
        log.error("Exception {}", ex);
        log.info("Response: {}", apiError);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<ApiError> handleGeneralException(ApplicationException ex) {
        var apiError = new ApiError(ex.getMessage(), LocalDateTime.now());
        log.error("Exception {}", ex);
        log.info("Response: {}", apiError);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = BookNotFoundException.class)
    public ResponseEntity<ApiError> handleGeneralException(BookNotFoundException ex) {
        var apiError = new ApiError(ex.getMessage(), LocalDateTime.now());
        log.error("Exception {}", ex);
        log.info("Response: {}", apiError);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {
        var apiError = new ApiError("Internal error", LocalDateTime.now());
        log.error("Exception {}", ex);
        log.info("Response: {}", apiError);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

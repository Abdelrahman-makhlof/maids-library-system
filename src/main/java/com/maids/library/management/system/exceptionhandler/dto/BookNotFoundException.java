package com.maids.library.management.system.exceptionhandler.dto;

import lombok.Getter;

@Getter
public class BookNotFoundException extends ApplicationException {

    public BookNotFoundException(String message) {
        super(message);
    }

}

package com.maids.library.management.system.exceptionhandler.dto;

import lombok.Getter;

@Getter
public class ApplicationException extends Exception {

    public ApplicationException(String message) {
        super(message);
    }

}

package com.maids.library.management.system.exceptionhandler.dto;

import lombok.Getter;

@Getter
public class NotAvailableException extends ApplicationException {

    public NotAvailableException(String message) {
        super(message);
    }

}

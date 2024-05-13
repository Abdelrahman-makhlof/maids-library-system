package com.maids.library.management.system.service;

import com.maids.library.management.system.dto.BookDTO;
import com.maids.library.management.system.exceptionhandler.dto.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {

    public void validateBookDto(BookDTO bookDTO) throws ValidationException {

        if (bookDTO.getTitle()==null)
            throw new ValidationException("Title is required");
        if (bookDTO.getAuthor()==null)
            throw new ValidationException("Author is required");


    }

}

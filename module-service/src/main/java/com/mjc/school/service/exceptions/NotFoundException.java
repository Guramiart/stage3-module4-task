package com.mjc.school.service.exceptions;

import java.io.Serial;

public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2L;

    public NotFoundException(String message) {
        super(message);
    }

}

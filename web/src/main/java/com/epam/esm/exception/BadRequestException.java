package com.epam.esm.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String name) {
        super(String.format("%s not valid value",name));
    }
}

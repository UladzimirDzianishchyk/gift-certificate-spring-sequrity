package com.epam.esm.exception;

public class EntityIsExistException extends RuntimeException{
    public EntityIsExistException(String name) {
        super(String.format("%s is exist",name));
    }
}

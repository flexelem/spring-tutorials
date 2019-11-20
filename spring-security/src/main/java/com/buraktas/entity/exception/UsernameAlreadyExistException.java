package com.buraktas.entity.exception;

public class UsernameAlreadyExistException extends RuntimeException {

    public UsernameAlreadyExistException() {
        super("Username already exists.");
    }
}

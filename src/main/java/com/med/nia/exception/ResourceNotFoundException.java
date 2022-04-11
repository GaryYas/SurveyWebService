package com.med.nia.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3860344765771841851L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

package com.med.nia.exception;

public class BadSurveyParametersException extends RuntimeException {

    private static final long serialVersionUID = -6533058551430906774L;

    public BadSurveyParametersException(String message) {
        super(message);
    }
}

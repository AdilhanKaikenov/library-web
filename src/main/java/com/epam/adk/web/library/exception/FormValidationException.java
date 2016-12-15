package com.epam.adk.web.library.exception;

/**
 * FormValidationException class created on 15.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class FormValidationException extends Exception {

    public FormValidationException(String message) {
        super(message);
    }

    public FormValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormValidationException(Throwable cause) {
        super(cause);
    }
}

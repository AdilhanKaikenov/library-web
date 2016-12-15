package com.epam.adk.web.library.validator;


/**
 * Abstract class AbstractValidator created on 15.12.2016
 *
 * @author Kaikenov Adilhan
 */
public abstract class AbstractValidator {

    private String message;

    public AbstractValidator() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

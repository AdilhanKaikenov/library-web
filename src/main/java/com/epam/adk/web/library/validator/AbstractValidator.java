package com.epam.adk.web.library.validator;


/**
 * Abstract class AbstractValidator created on 15.12.2016
 *
 * @author Kaikenov Adilhan
 */
public abstract class AbstractValidator implements Validator {

    private String message;

    public AbstractValidator() {
    }

    @Override
    public boolean isValid(String field) {
        throw new UnsupportedOperationException("Do not support.");
    }

    @Override
    public boolean isValid(Long value) {
        throw new UnsupportedOperationException("Do not support.");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

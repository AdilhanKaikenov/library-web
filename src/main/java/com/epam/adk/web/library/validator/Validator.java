package com.epam.adk.web.library.validator;

/**
 * Interface Validator created on 15.12.2016
 *
 * @author Kaikenov Adilhan
 */
public interface Validator {

    boolean isValid(String field);

    boolean isValid(Long value);

    String getMessage();
}

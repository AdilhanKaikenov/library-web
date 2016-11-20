package com.epam.adk.web.library.exception;

/**
 * PropertyManagerException class created on 18.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class PropertyManagerException extends RuntimeException {

    public PropertyManagerException(String message) {
        super(message);
    }

    public PropertyManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertyManagerException(Throwable cause) {
        super(cause);
    }
}

package com.epam.adk.web.library.exception;

/**
 * DaoException class created on 18.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class DaoException extends Exception {

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}

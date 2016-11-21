package com.epam.adk.web.library.exception;

/**
 * ConnectionPoolException class created on 18.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class ConnectionPoolException extends Exception {

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}

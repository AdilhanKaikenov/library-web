package com.epam.adk.web.library.exception;

/**
 * DaoException class created on 09.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class MessageDigestAlgorithmException extends RuntimeException {

    public MessageDigestAlgorithmException(String msg) {
        super(msg);
    }

    public MessageDigestAlgorithmException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageDigestAlgorithmException(Throwable cause) {
        super(cause);
    }
}

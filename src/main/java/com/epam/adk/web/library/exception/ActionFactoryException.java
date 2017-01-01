package com.epam.adk.web.library.exception;

/**
 * ActionFactoryException class created on 01.01.2017
 *
 * @author Kaikenov Adilhan
 **/
public class ActionFactoryException extends Exception {

    public ActionFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionFactoryException(Throwable cause) {
        super(cause);
    }
}

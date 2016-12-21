package com.epam.adk.web.library.exception;

/**
 * JdbcDaoConfigurationException class created on 16.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class JdbcDaoConfigurationException extends DaoException {

    public JdbcDaoConfigurationException(String message) {
        super(message);
    }

    public JdbcDaoConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}

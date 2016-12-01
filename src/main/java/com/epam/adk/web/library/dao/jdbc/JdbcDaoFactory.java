package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.BookDao;
import com.epam.adk.web.library.dao.DaoFactory;
import com.epam.adk.web.library.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * JdbcDaoFactory class created on 23.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class JdbcDaoFactory extends DaoFactory {

    private static final Logger log = LoggerFactory.getLogger(JdbcDaoFactory.class);
    private Connection connection;

    public JdbcDaoFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UserDao userDao() {
        return new JdbcUserDao(connection);
    }

    @Override
    public BookDao bookDao() {
        return new JdbcBookDao(connection);
    }
}
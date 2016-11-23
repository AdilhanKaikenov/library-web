package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.UserDao;
import com.epam.adk.web.library.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * JdbcUserDao class created on 23.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class JdbcUserDao extends JdbcDao<User> implements UserDao {

    public JdbcUserDao(Connection connection) {
        super(connection);
    }
}

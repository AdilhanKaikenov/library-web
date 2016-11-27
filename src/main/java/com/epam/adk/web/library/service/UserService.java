package com.epam.adk.web.library.service;

import com.epam.adk.web.library.dao.DaoFactory;
import com.epam.adk.web.library.dao.UserDao;
import com.epam.adk.web.library.dao.jdbc.JdbcDaoFactory;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * UserService class created on 27.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public User registerUser(User user) {
        User registeredUser = null;
        JdbcDaoFactory jdbcDaoFactory = null;
        try {
            jdbcDaoFactory = (JdbcDaoFactory) DaoFactory.newInstance(DaoFactory.JDBC);
            UserDao userDao = jdbcDaoFactory.userDao();
            jdbcDaoFactory.beginTransaction();
            registeredUser = userDao.create(user);
            jdbcDaoFactory.endTransaction();
        } catch (DaoException | SQLException e) {
            log.error("Error: UserService class registerUser() method: {}", e);
            try {
                jdbcDaoFactory.rollbackTransaction();
            } catch (SQLException e1) {
                log.error("Error: UserService class registerUser() method, called rollbackTransaction() method failed: {}", e1);
            }
        } finally {
            jdbcDaoFactory.closeTransaction();
        }
        return registeredUser;
    }

    public boolean isExist(String columnName, String parameter) {
        log.debug("Entering isExist() method, arguments: columnName = {}, parameter = {}", columnName, parameter);
        JdbcDaoFactory jdbcDaoFactory = null;
        try {
            jdbcDaoFactory = (JdbcDaoFactory) DaoFactory.newInstance(DaoFactory.JDBC);
            UserDao userDao = jdbcDaoFactory.userDao();

            jdbcDaoFactory.beginTransaction();
            int existNumber = userDao.countParameter(columnName, parameter);
            log.debug("Parameter '{}' in column '{}' exist = {} times", parameter, columnName, existNumber);
            if (existNumber == 0){
                return false;
            }
            jdbcDaoFactory.endTransaction();
        } catch (DaoException | SQLException e) {
            log.error("Error: UserService class isExist() method: {}", e);
            try {
                jdbcDaoFactory.rollbackTransaction();
            } catch (SQLException e1) {
                log.error("Error: UserService class isExist() method, called rollbackTransaction() method failed: {}", e1);
            }
        } finally {
            jdbcDaoFactory.closeTransaction();
        }
        return true;
    }
}

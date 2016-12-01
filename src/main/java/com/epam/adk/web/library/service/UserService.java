package com.epam.adk.web.library.service;

import com.epam.adk.web.library.dao.DaoFactory;
import com.epam.adk.web.library.dao.UserDao;
import com.epam.adk.web.library.dao.jdbc.JdbcDaoFactory;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.exception.ServiceException;
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

    public User registerUser(User user) throws ServiceException {
        log.debug("Entering UserService class registerUser() method, user login = {}", user.getLogin());
        User registeredUser;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                UserDao userDao = jdbcDaoFactory.userDao();
                registeredUser = userDao.create(user);
                log.debug("Registered user login: {}", registeredUser.getLogin());
                jdbcDaoFactory.endTransaction();
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: UserService class, registerUser() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: UserService class, registerUser() method.", e);
        }
        log.debug("Leaving UserService class registerUser() method.");
        return registeredUser;
    }

    public User authorizeUser(User user) throws ServiceException {
        log.debug("Entering UserService class authorizeUser() method, user login = {}", user.getLogin());
        User authorizedUser;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                UserDao userDao = jdbcDaoFactory.userDao();
                authorizedUser = userDao.read(user);
                log.debug("Authorized user login: {}", authorizedUser.getLogin());
                jdbcDaoFactory.endTransaction();
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: UserService class, authorizeUser() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: UserService class, authorizeUser() method.", e);
        }
        log.debug("Leaving UserService class authorizeUser() method.");
        return authorizedUser;
    }
}

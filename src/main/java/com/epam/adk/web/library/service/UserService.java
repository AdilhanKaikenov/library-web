package com.epam.adk.web.library.service;

import com.epam.adk.web.library.dao.DaoFactory;
import com.epam.adk.web.library.dao.UserDao;
import com.epam.adk.web.library.dao.jdbc.JdbcDaoFactory;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.text.MessageFormat;

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
                jdbcDaoFactory.endTransaction();
            } catch (SQLException e) {
                try {
                    jdbcDaoFactory.rollbackTransaction();
                } catch (SQLException e1) {
                    throw new ServiceException(MessageFormat.format(
                            "Error: UserService class, registerUser() method. Can not rollback transaction: {0}", e1));
                }
                throw new ServiceException(MessageFormat.format(
                        "Error: UserService class, registerUser() method. Error transaction: {0}", e));
            }
        } catch (Exception e) {
            throw new ServiceException(MessageFormat.format("Error: UserService class, registerUser() method.", e));
        }
        log.debug("Exit UserService class registerUser() method.");
        return registeredUser;
    }
}

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
import java.util.List;

/**
 * UserService class created on 27.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public User register(User user) throws ServiceException {
        log.debug("Entering UserService class register() method, user login = {}", user.getLogin());
        User registeredUser;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            UserDao userDao = jdbcDaoFactory.getUserDao();
            registeredUser = userDao.create(user);
            log.debug("Leaving UserService class register() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: UserService class, register() method.", e);
        }
        return registeredUser;
    }

    public User authorize(User user) throws ServiceException {
        log.debug("Entering UserService class authorize() method, user login = {}", user.getLogin());
        User authorizedUser;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            UserDao userDao = jdbcDaoFactory.getUserDao();
            authorizedUser = userDao.read(user);
            if (authorizedUser != null) {
                log.debug("Authorized user login: {}", authorizedUser.getLogin());
            }
            log.debug("Leaving UserService class authorize() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: UserService class, authorize() method.", e);
        }
        return authorizedUser;
    }

    public User getUserById(int userID) throws ServiceException {
        log.debug("Entering UserService class getUserById() method, User id = {}", userID);
        User user;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            UserDao userDao = jdbcDaoFactory.getUserDao();
            user = userDao.read(userID);
            log.debug("Leaving UserService class getUserById() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: UserService class, getUserById() method.", e);
        }
        return user;
    }

    /**
     * This method returns all the users in parts according to page number.
     *
     * @param pageNumber number of pages
     * @param pageSize the number of users per page
     * @return List of users
     * @throws ServiceException
     */
    public List<User> getPaginated(int pageNumber, int pageSize) throws ServiceException {
        log.debug("Entering UserService class getPaginated() method.");
        List<User> result;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            UserDao userDao = jdbcDaoFactory.getUserDao();
            int offset = pageSize * pageNumber - pageSize;
            result = userDao.readRange(offset, pageSize);
            log.debug("UserService class, getPaginated() method: result size = {}", result.size());
            log.debug("Leaving UserService class getPaginated() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: UserService class, getPaginated() method.", e);
        }
        return result;
    }

    public void updateUserData(User user) throws ServiceException {
        log.debug("Entering UserService class updateUserData() method. User Id = {}", user.getId());
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            UserDao userDao = jdbcDaoFactory.getUserDao();
            userDao.update(user);
            log.debug("Leaving UserService class updateUserData() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: UserService class, updateUserData() method.", e);
        }
    }

    public int getUsersNumber() throws ServiceException {
        log.debug("Entering UserService class getUsersNumber() method.");
        int usersNumber;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            UserDao userDao = jdbcDaoFactory.getUserDao();
            usersNumber = userDao.getNumberRows();
            log.debug("Leaving UserService class getUsersNumber() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: UserService class, getUsersNumber() method.", e);
        }
        return usersNumber;
    }
}

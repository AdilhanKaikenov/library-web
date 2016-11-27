package com.epam.adk.web.library.dao;

import com.epam.adk.web.library.dao.jdbc.JdbcDaoFactory;
import com.epam.adk.web.library.dbcp.ConnectionPool;
import com.epam.adk.web.library.exception.ConnectionPoolException;
import com.epam.adk.web.library.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;

/**
 * Abstract class DaoFactory created on 23.11.2016
 *
 * @author Kaikenov Adilhan
 */
public abstract class DaoFactory {

    private static final Logger log = LoggerFactory.getLogger(DaoFactory.class);

    public static final Class JDBC = JdbcDaoFactory.class;

    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static Connection connection;

    public static <T extends DaoFactory> T newInstance(Class<T> clazz) throws DaoException {
        log.debug("Entering newInstance() method. DaoFactory class = {}", clazz.getSimpleName());
        try {
            connection = connectionPool.getConnection();
            log.debug("DaoFactory class, get Connection: {}", connection);
            Constructor<T> constructor = clazz.getConstructor(Connection.class);
            T instance = constructor.newInstance(connection);
            log.debug("DaoFactory class, newInstance() method successfully completed. Factory: {}", instance.getClass().getSimpleName());
            return instance;
        } catch (NoSuchMethodException e) {
            throw new DaoException(MessageFormat.format("No such method. Called getConstructor() method failed: {0}", e));
        } catch (InstantiationException e) {
            throw new DaoException(MessageFormat.format("Instantiation Exception. Called newInstance() method failed: {0}", e));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DaoException(MessageFormat.format("Error in DaoFactory class, newInstance() method failed: {0}", e));
        } catch (ConnectionPoolException e) {
            throw new DaoException(MessageFormat.format("Error in DaoFactory class, getConnection() method failed: {0}", e));
        }
    }

    public abstract UserDao userDao();

    /**
     * Start transaction.
     *
     * @throws SQLException
     */
    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    /**
     * RollBack transaction.
     *
     * @throws SQLException
     */
    public void rollbackTransaction() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }

    /**
     * Commit transaction to the DataBase.
     *
     * @throws SQLException
     */
    public void endTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    /**
     * Return connection back to pool of connections.
     */
    public void closeTransaction() {
        if (connection != null) {
            connectionPool.returnConnection(connection);
        }
    }
}

package com.epam.adk.web.library.dao;

import com.epam.adk.web.library.dbcp.ConnectionPool;
import com.epam.adk.web.library.exception.ConnectionPoolException;
import com.epam.adk.web.library.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Abstract class DaoFactory created on 23.11.2016
 *
 * @author Kaikenov Adilhan
 * @see AutoCloseable
 */
public abstract class DaoFactory implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(DaoFactory.class);

    private static ConnectionPool connectionPool;
    private static Connection connection;

    public static <T extends DaoFactory> T newInstance(Class<T> clazz) throws DaoException {
        log.debug("Entering newInstance() method. DaoFactory class = {}", clazz.getSimpleName());
        try {
            connection = connectionPool.getConnection();
            log.debug("DaoFactory class, get Connection: {}", connection);
            Constructor<T> constructor = clazz.getConstructor(Connection.class);
            T instance = constructor.newInstance(connection);
            log.debug("Leaving DaoFactory class, newInstance() method successfully completed. Factory: {}", instance.getClass().getSimpleName());
            return instance;
        } catch (NoSuchMethodException e) {
            throw new DaoException("No such method. Called getConstructor() method failed:", e);
        } catch (InstantiationException e) {
            throw new DaoException("Instantiation Exception. Called newInstance() method failed:", e);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DaoException("Error in DaoFactory class, newInstance() method failed:", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error in DaoFactory class, getConnection() method failed:", e);
        }
    }

    public static void setConnectionPool(ConnectionPool connectionPool) {
        DaoFactory.connectionPool = connectionPool;
    }

    public abstract UserDao userDao();

    public abstract BookDao bookDao();

    public abstract CommentDao commentDao();

    public abstract OrderDao orderDao();

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
    public void closeTransaction() throws SQLException {
        if (connection != null) {
            connection.setAutoCommit(true);
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void close() throws SQLException {
        closeTransaction();
    }
}

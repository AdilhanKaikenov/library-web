package com.epam.adk.web.library.dbcp;

import com.epam.adk.web.library.exception.ConnectionPoolException;
import com.epam.adk.web.library.exception.PropertyManagerException;
import com.epam.adk.web.library.propmanager.DataBasePropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ConnectionPool class created on 18.11.2016
 *
 * @author Kaikenov Adilhan
 */
public final class ConnectionPool {

    private static final Logger log = LoggerFactory.getLogger(ConnectionPool.class);

    private static String JDBC_URL = DataBasePropertyManager.getProperty("jdbc.url");
    private static String H2_DRIVER = DataBasePropertyManager.getProperty("h2.driver");
    private static String DB_LOGIN = DataBasePropertyManager.getProperty("db.login");
    private static String DB_PASSWORD = DataBasePropertyManager.getProperty("db.password");
    private static int DEFAULT_POOL_SIZE = Integer.parseInt(DataBasePropertyManager.getProperty("default.pool.size"));
    private static int MAX_POOL_SIZE = Integer.parseInt(DataBasePropertyManager.getProperty("max.pool.size"));
    private static long TIMEOUT = Long.parseLong(DataBasePropertyManager.getProperty("timeout.milliseconds"));

    private BlockingQueue<Connection> freeConnections = null;
    private final Lock lock = new ReentrantLock();

    public ConnectionPool() {
        log.debug("In constructor ConnectionPool.");
        try {
            try {
                log.debug("Creating connection pool.");
                log.debug("Database driver: {}", H2_DRIVER);
                Class.forName(H2_DRIVER);
                freeConnections = new ArrayBlockingQueue<>(MAX_POOL_SIZE, true);
                for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                    Connection connection = newConnection();
                    freeConnections.add(connection);
                    log.debug("Connection #{} - {}", i, connection);
                }
            } catch (ClassNotFoundException e) {
                log.error("Error: Driver class not found error: {}", e);
            } catch (SQLException e) {
                log.error("Error: Get connection from database failed. Called newConnection() method failed: {}", e);
            }
        } catch (PropertyManagerException e) {
            log.error("Error: Loading properties from properties file failed. Called initPoolConfigurations() method failed: {}", e);
        }
    }

    private Connection newConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_LOGIN, DB_PASSWORD);
    }

    /**
     * The ConnectionPoolHolder is a wrapper class which hides the object of ConnectionPool.
     */
    private static class ConnectionPoolHolder {
        static final ConnectionPool instance = new ConnectionPool();
    }

    /**
     * The Method to get an object of ConnectionPool.
     *
     * @return instance of ConnectionPool
     */
    public static ConnectionPool getInstance() {
        return ConnectionPoolHolder.instance;
    }

    /**
     * The method returns the free connection from pool of freeConnections, or creates a new one if there is no free freeConnections.
     *
     * @return free connection.
     */
    public Connection getConnection() throws ConnectionPoolException {
        Connection connection;
        lock.lock();
        try {
            connection = freeConnections.poll(TIMEOUT, TimeUnit.MILLISECONDS);
            log.debug("Connection: {}", connection);

            if (connection == null && freeConnections.size() < MAX_POOL_SIZE) {
                log.debug("Creating new connection.");
                connection = newConnection();
                freeConnections.add(connection);
            }

        } catch (InterruptedException e) {
            log.error("Error: getConnection() method. Called poll() method failed: {}", e);
            throw new ConnectionPoolException(MessageFormat.format(
                    "Error: getConnection() method. Called poll() method failed: {}", e));
        } catch (SQLException e) {
            log.error("Error: getConnection() method, get connection from database failed. Called newConnection() method failed: {}", e);
            throw new ConnectionPoolException(MessageFormat.format(
                    "Error: getConnection() method, get connection from database failed. Called newConnection() method failed: {0}", e));
        } finally {
            lock.unlock();
        }
        return connection;
    }

    public void returnConnection(Connection connection) {
        if (connection != null && freeConnections.size() < MAX_POOL_SIZE) {
            log.debug("returnConnection() method, return '{}' back.", connection);
            freeConnections.add(connection);
        }
    }

    public int freeConnectionsNumber() {
        return freeConnections.size();
    }
}
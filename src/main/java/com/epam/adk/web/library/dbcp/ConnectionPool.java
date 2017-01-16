package com.epam.adk.web.library.dbcp;

import com.epam.adk.web.library.exception.ConnectionPoolConfigurationException;
import com.epam.adk.web.library.exception.ConnectionPoolException;
import com.epam.adk.web.library.exception.ConnectionPoolInitializationException;
import com.epam.adk.web.library.exception.PropertyManagerException;
import com.epam.adk.web.library.propmanager.PropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

    private static final int DEFAULT_MIN_POOL_SIZE = 10;
    private static final int DEFAULT_MAX_POOL_SIZE = 50;
    private static final String H2DB_PROPERTIES_FILE_NAME = "h2db.properties";
    private static final Lock lock = new ReentrantLock();

    private static String jdbcUrl;
    private static String h2Driver;
    private static String dbLogin;
    private static String dbPassword;
    private static int defaultPoolSize;
    private static int maxPoolSize;
    private static long timeout;

    private BlockingQueue<Connection> freeConnections = null;

    private ConnectionPool() {
    }

    /**
     * Method for initialization ConnectionPool.
     *
     * @throws ConnectionPoolInitializationException
     */
    public void init() throws ConnectionPoolInitializationException {
        try {
            log.debug("Creating connection pool.");
            log.debug("Database driver: {}", h2Driver);
            Class.forName(h2Driver);
            freeConnections = new ArrayBlockingQueue<>(maxPoolSize, true);
            for (int i = 0; i < defaultPoolSize; i++) {
                Connection connection = newConnection();
                freeConnections.add(connection);
                log.debug("Connection #{} - {}", i, connection);
            }
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolInitializationException("Error: Driver class not found error:", e);
        } catch (SQLException e) {
            throw new ConnectionPoolInitializationException("Error: Get connection getFromValue database failed. Called newConnection() method failed:", e);
        }
    }

    public static void configure() throws ConnectionPoolConfigurationException {
        PropertiesManager propertiesManager;
        try {
            propertiesManager = new PropertiesManager(H2DB_PROPERTIES_FILE_NAME);
        } catch (PropertyManagerException e) {
            throw new ConnectionPoolConfigurationException("Error: ConnectionPool class, configure() method.", e);
        }
        jdbcUrl = propertiesManager.get("jdbc.url");
        h2Driver = propertiesManager.get("h2.driver");
        dbLogin = propertiesManager.get("db.login");
        dbPassword = propertiesManager.get("db.password");
        defaultPoolSize = getInt(propertiesManager.get("default.pool.size"), DEFAULT_MIN_POOL_SIZE);
        maxPoolSize = getInt(propertiesManager.get("max.pool.size"), DEFAULT_MAX_POOL_SIZE);
        timeout = Long.parseLong(propertiesManager.get("timeout.milliseconds"));
    }

    /**
     * The method parses a string and returns a numeric representation, or in the event of an error by a default value.
     *
     * @param string
     * @param defaultValue
     * @return
     */
    private static int getInt(String string, int defaultValue) {
        int result = defaultValue;
        try {
            result = Integer.parseInt(string);
        } catch (NumberFormatException e){
            return result;
        }
        return result;
    }

    private Connection newConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, dbLogin, dbPassword);
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
     * The method returns the free connection getFromValue pool of freeConnections, or creates a new one if there is no free freeConnections.
     *
     * @return free connection.
     */
    public Connection getConnection() throws ConnectionPoolException {
        log.debug("Entering ConnectionPool class getConnection() method.");
        Connection connection;
        lock.lock();
        try {
            connection = freeConnections.poll(timeout, TimeUnit.MILLISECONDS);
            log.debug("Connection: {}", connection);

            if (connection == null && freeConnections.size() < maxPoolSize) {
                log.debug("Creating new connection.");
                connection = newConnection();
                freeConnections.add(connection);
            }
            log.debug("Leaving ConnectionPool class getConnection() method.");
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Error: getConnection() method. Called poll() method failed:", e);
        } catch (SQLException e) {
            throw new ConnectionPoolException("Error: getConnection() method, get connection getFromValue database failed. Called newConnection() method failed:", e);
        } finally {
            lock.unlock();
        }
        return connection;
    }

    /**
     * The method accepts the connection and returns it back to the pool.
     *
     * @param connection
     */
    public void returnConnection(Connection connection) {
        if (connection != null && freeConnections.size() < maxPoolSize) {
            log.debug("ConnectionPool class, returnConnection() method, return '{}' back.", connection);
            freeConnections.add(connection);
        }
    }

    public int freeConnectionsNumber() {
        return freeConnections.size();
    }

    /**
     * The method covers all the free compound and then clears the connections pool.
     *
     * @throws ConnectionPoolException
     */
    public void shutDown() throws ConnectionPoolException {

        for (Connection connection : freeConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new ConnectionPoolException("Error: ConnectionPool class, shutDown() method. Can not close connection", e);
            }
        }
        freeConnections.clear();
        log.debug("ConnectionPool class, shutDown() method, Pool successfully closed. Pool size = {}.", freeConnectionsNumber());
    }
}
package com.epam.adk.web.library.dbcp;

import com.epam.adk.web.library.exception.ConnectionPoolException;
import com.epam.adk.web.library.exception.ConnectionPoolInitializationException;
import com.epam.adk.web.library.exception.PropertyManagerException;
import com.epam.adk.web.library.propmanager.PropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
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

    private static String JDBC_URL;
    private static String H2_DRIVER;
    private static String DB_LOGIN;
    private static String DB_PASSWORD;
    private static int DEFAULT_POOL_SIZE;
    private static int MAX_POOL_SIZE;
    private static long TIMEOUT;

    private BlockingQueue<Connection> freeConnections = null;
    private final Lock lock = new ReentrantLock();

    private ConnectionPool() {
    }

    public void init() throws ConnectionPoolInitializationException {
        try {
            log.debug("Creating connection pool.");
            configure();
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
            throw new ConnectionPoolInitializationException("Error: Driver class not found error:", e);
        } catch (SQLException e) {
            log.error("Error: Get connection from database failed. Called newConnection() method failed: {}", e);
            throw new ConnectionPoolInitializationException("Error: Get connection from database failed. Called newConnection() method failed:", e);
        } catch (PropertyManagerException e) {
            log.error("Error: Called configure() method failed: {}", e);
            throw new ConnectionPoolInitializationException("Error: Called configure() method failed: {}", e);
        }
    }

    private void configure() throws PropertyManagerException {
        PropertiesManager propertiesManager = new PropertiesManager("h2db.properties");
        Map<String, String> dbProperties = propertiesManager.getPropertiesAsMap();
        JDBC_URL = dbProperties.get("jdbc.url");
        H2_DRIVER = dbProperties.get("h2.driver");
        DB_LOGIN = dbProperties.get("db.login");
        DB_PASSWORD = dbProperties.get("db.password");
        DEFAULT_POOL_SIZE = getInt(dbProperties.get("default.pool.size"), DEFAULT_MIN_POOL_SIZE);
        MAX_POOL_SIZE = getInt(dbProperties.get("max.pool.size"), DEFAULT_MAX_POOL_SIZE);
        TIMEOUT = Long.parseLong(dbProperties.get("timeout.milliseconds"));
    }

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
        log.debug("Entering ConnectionPool class getConnection() method.");
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
            log.debug("Leaving ConnectionPool class getConnection() method.");
        } catch (InterruptedException e) {
            log.error("Error: getConnection() method. Called poll() method failed: {}", e);
            throw new ConnectionPoolException("Error: getConnection() method. Called poll() method failed:", e);
        } catch (SQLException e) {
            log.error("Error: getConnection() method, get connection from database failed. Called newConnection() method failed: {}", e);
            throw new ConnectionPoolException("Error: getConnection() method, get connection from database failed. Called newConnection() method failed:", e);
        } finally {
            lock.unlock();
        }
        return connection;
    }

    public void returnConnection(Connection connection) {
        if (connection != null && freeConnections.size() < MAX_POOL_SIZE) {
            log.debug("ConnectionPool class, returnConnection() method, return '{}' back.", connection);
            freeConnections.add(connection);
        }
    }

    public int freeConnectionsNumber() {
        return freeConnections.size();
    }

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
package com.epam.adk.web.library.listener;

import com.epam.adk.web.library.dao.DaoFactory;
import com.epam.adk.web.library.dbcp.ConnectionPool;
import com.epam.adk.web.library.exception.ConnectionPoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ConnectionPoolListener class created on 30.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class ConnectionPoolListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(ConnectionPoolListener.class);
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            connectionPool.init();
        } catch (ConnectionPoolException e) {
            log.error("ConnectionPoolListener class, called init() method failed. {}", e);
        }
        DaoFactory.setConnectionPool(connectionPool);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            connectionPool.shutDown();
        } catch (ConnectionPoolException e) {
            log.error("ConnectionPoolListener class, called shutDown() method failed. {}", e);
        }
    }
}

package com.epam.adk.web.library.service;

import com.epam.adk.web.library.dao.DaoFactory;
import com.epam.adk.web.library.dao.OrderDao;
import com.epam.adk.web.library.dao.jdbc.JdbcDaoFactory;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * OrderBookService class created on 05.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class OrderBookService {

    private static final Logger log = LoggerFactory.getLogger(OrderBookService.class);

    public Order sendRequest(Order order) throws ServiceException {
        log.debug("Entering OrderBookService class sendRequest() method.");
        Order orderRequest;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                OrderDao orderDao = jdbcDaoFactory.orderDao();
                orderRequest = orderDao.create(order);
                jdbcDaoFactory.endTransaction();
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrderBookService class, sendRequest() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrderBookService class, sendRequest() method.", e);
        }
        log.debug("Leaving OrderBookService class sendRequest() method.");
        return orderRequest;
    }

    public int getOrderNumber(Order order) throws ServiceException {
        log.debug("Entering OrderBookService class getOrderNumber() method.");
        int orderNumber;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                OrderDao orderDao = jdbcDaoFactory.orderDao();
                orderNumber = orderDao.countOrder(order);
                jdbcDaoFactory.endTransaction();
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrderBookService class, getOrderNumber() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrderBookService class, getOrderNumber() method.", e);
        }
        log.debug("Leaving OrderBookService class getOrderNumber() method.");
        return orderNumber;
    }

}

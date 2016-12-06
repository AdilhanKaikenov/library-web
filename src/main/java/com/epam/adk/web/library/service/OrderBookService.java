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
import java.util.List;

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

    public int getOrdersNumberByUser(int id) throws ServiceException {
        log.debug("Entering OrderBookService class getOrdersNumberByUser() method.");
        int ordersNumber;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                OrderDao orderDao = jdbcDaoFactory.orderDao();
                ordersNumber = orderDao.getNumberRowsByIdParameter(id);
                jdbcDaoFactory.endTransaction();
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrderBookService class, getOrdersNumberByUser() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrderBookService class, getOrdersNumberByUser() method.", e);
        }
        log.debug("Leaving OrderBookService class getOrdersNumberByUser() method.");
        return ordersNumber;
    }

    public List<Order> getPaginatedOrders(int userID, int pageNumber, int pageSize) throws ServiceException {
        log.debug("Entering OrderBookService class getPaginatedOrders() method. User id = {}", userID);
        List<Order> result;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)){
            try {
                jdbcDaoFactory.beginTransaction();
                OrderDao orderDao = jdbcDaoFactory.orderDao();
                int offset = pageSize * pageNumber - pageSize;
                result = orderDao.readRangeByIdParameter(userID, offset, pageSize);
                jdbcDaoFactory.endTransaction();
            } catch (SQLException e){
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrderBookService class, getPaginatedOrders() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrderBookService class, getPaginatedOrders() method.", e);
        }
        log.debug("Leaving OrderBookService class getPaginatedOrders() method. Amount of orders comment = {}", result.size());
        return result;
    }

    public List<Order> getPaginated(int pageNumber, int pageSize) throws ServiceException {
        log.debug("Entering OrderBookService class getPaginated() method.");
        List<Order> result;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                OrderDao orderDao = jdbcDaoFactory.orderDao();
                int offset = pageSize * pageNumber - pageSize;
                result = orderDao.readRange(offset, pageSize);
                log.debug("OrderBookService class, getPaginated() method: result size = {}", result.size());
                jdbcDaoFactory.endTransaction();
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrderBookService class, getPaginated() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrderBookService class, getPaginated() method.", e);
        }
        log.debug("Leaving OrderBookService class getPaginated() method.");
        return result;
    }

    public int getOrdersNumber() throws ServiceException {
        log.debug("Entering OrderBookService class getOrdersNumber() method.");
        int ordersNumber;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                OrderDao orderDao = jdbcDaoFactory.orderDao();
                ordersNumber = orderDao.getNumberRows();
                jdbcDaoFactory.endTransaction();
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrderBookService class, getOrdersNumber() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrderBookService class, getOrdersNumber() method.", e);
        }
        log.debug("Leaving OrderBookService class getOrdersNumber() method.");
        return ordersNumber;
    }
}

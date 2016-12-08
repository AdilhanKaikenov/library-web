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
                log.debug("Leaving OrderBookService class sendRequest() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrderBookService class, sendRequest() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrderBookService class, sendRequest() method.", e);
        }
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
                log.debug("Leaving OrderBookService class getOrderNumber() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrderBookService class, getOrderNumber() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrderBookService class, getOrderNumber() method.", e);
        }
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
                log.debug("Leaving OrderBookService class getOrdersNumberByUser() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrderBookService class, getOrdersNumberByUser() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrderBookService class, getOrdersNumberByUser() method.", e);
        }
        return ordersNumber;
    }

    public List<Order> getPaginatedUserOrders(int userID, int pageNumber, int pageSize) throws ServiceException {
        log.debug("Entering OrderBookService class getPaginatedUserOrders() method. User id = {}", userID);
        List<Order> result;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                OrderDao orderDao = jdbcDaoFactory.orderDao();
                int offset = pageSize * pageNumber - pageSize;
                result = orderDao.readRangeByIdParameter(userID, offset, pageSize);
                jdbcDaoFactory.endTransaction();
                log.debug("Leaving OrderBookService class getPaginatedUserOrders() method. Amount of orders comment = {}", result.size());
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrderBookService class, getPaginatedUserOrders() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrderBookService class, getPaginatedUserOrders() method.", e);
        }
        return result;
    }

    public List<Order> getPaginatedByOrderStatus(int statusID, int pageNumber, int pageSize) throws ServiceException {
        log.debug("Entering OrderBookService class getPaginated() method.");
        List<Order> result;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                OrderDao orderDao = jdbcDaoFactory.orderDao();
                int offset = pageSize * pageNumber - pageSize;
                result = orderDao.readRangeByStatusId(statusID, offset, pageSize);
                log.debug("OrderBookService class, getPaginated() method: result size = {}", result.size());
                jdbcDaoFactory.endTransaction();
                log.debug("Leaving OrderBookService class getPaginated() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrderBookService class, getPaginated() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrderBookService class, getPaginated() method.", e);
        }
        return result;
    }

    public int getOrdersNumberByStatusID(int statusID) throws ServiceException {
        log.debug("Entering OrderBookService class getOrdersNumberByStatusID() method.");
        int ordersNumber;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                OrderDao orderDao = jdbcDaoFactory.orderDao();
                ordersNumber = orderDao.getNumberRowsByStatusId(statusID);
                jdbcDaoFactory.endTransaction();
                log.debug("Leaving OrderBookService class getOrdersNumberByStatusID() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrderBookService class, getOrdersNumberByStatusID() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrderBookService class, getOrdersNumberByStatusID() method.", e);
        }
        return ordersNumber;
    }

    public int getOrdersNumberByBookID(int bookID) throws ServiceException {
        log.debug("Entering OrderBookService class getOrdersNumberByBookID() method.");
        int ordersNumber;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                OrderDao orderDao = jdbcDaoFactory.orderDao();
                ordersNumber = orderDao.getNumberRowsByBookId(bookID);
                jdbcDaoFactory.endTransaction();
                log.debug("Leaving OrderBookService class getOrdersNumberByBookID() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrderBookService class, getOrdersNumberByBookID() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrderBookService class, getOrdersNumberByBookID() method.", e);
        }
        return ordersNumber;
    }

    public Order getOrderById(int id) throws ServiceException {
        log.debug("Entering OrderBookService class getOrderById() method. Id = {}", id);
        Order order;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                OrderDao orderDao = jdbcDaoFactory.orderDao();
                order = orderDao.read(id);
                jdbcDaoFactory.endTransaction();
                log.debug("Leaving OrderBookService class getOrderById() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrderBookService class, getOrderById() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrderBookService class, getOrderById() method.", e);
        }
        return order;
    }

    public void updateOrder(Order order) throws ServiceException {
        log.debug("Entering OrderBookService class updateOrder() method. Order Id = {}", order.getId());
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                OrderDao orderDao = jdbcDaoFactory.orderDao();
                orderDao.update(order);
                jdbcDaoFactory.endTransaction();
                log.debug("Leaving OrderBookService class updateOrder() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrderBookService class, updateOrder() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrderBookService class, updateOrder() method.", e);
        }
    }

    public void writeToHistory(Order order) throws ServiceException {
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                OrderDao orderDao = jdbcDaoFactory.orderDao();
                orderDao.insertIntoHistory(order);
                orderDao.delete(order);
                jdbcDaoFactory.endTransaction();
                log.debug("Leaving OrderBookService class writeToHistory() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrderBookService class, writeToHistory() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrderBookService class, writeToHistory() method.", e);
        }
    }

    public void deleteRejectedOrderRequests() throws ServiceException {
        log.debug("Entering OrderBookService class deleteRejectedOrderRequests() method. ");
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                OrderDao orderDao = jdbcDaoFactory.orderDao();
                orderDao.deleteAllOldRejectedOrderRequests();
                jdbcDaoFactory.endTransaction();
                log.debug("Leaving OrderBookService class deleteRejectedOrderRequests() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrderBookService class, deleteRejectedOrderRequests() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrderBookService class, deleteRejectedOrderRequests() method.", e);
        }
    }
}

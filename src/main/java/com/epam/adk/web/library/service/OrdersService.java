package com.epam.adk.web.library.service;

import com.epam.adk.web.library.dao.DaoFactory;
import com.epam.adk.web.library.dao.OrdersBooksDao;
import com.epam.adk.web.library.dao.OrdersDao;
import com.epam.adk.web.library.dao.jdbc.JdbcDaoFactory;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.model.OrderBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * OrdersService class created on 16.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class OrdersService {

    private static final Logger log = LoggerFactory.getLogger(OrdersService.class);

    public Order add(Order order) throws ServiceException {
        log.debug("Entering OrdersService class add() method");
        Order addedOrder;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersDao registerOrderDao = jdbcDaoFactory.getOrdersDao();
            addedOrder = registerOrderDao.create(order);
            log.debug("Leaving OrdersService class add() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersService class, add() method.", e);
        }
        return addedOrder;
    }

    public int getOrdersNumberByUserID(int id) throws ServiceException {
        log.debug("Entering OrdersService class getOrdersNumberByUserID() method.");
        int ordersNumber;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersDao ordersDao = jdbcDaoFactory.getOrdersDao();
            ordersNumber = ordersDao.getNumberRowsByIdParameter(id);
            log.debug("Leaving OrdersService class getOrdersNumberByUserID() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersService class, getOrdersNumberByUserID() method.", e);
        }
        return ordersNumber;
    }

    public List<Order> getPaginatedUserOrders(int userID, int pageNumber, int pageSize) throws ServiceException {
        log.debug("Entering OrdersService class getPaginatedUserOrders() method. User id = {}", userID);
        List<Order> result;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersDao ordersDao = jdbcDaoFactory.getOrdersDao();
            int offset = pageSize * pageNumber - pageSize;
            result = ordersDao.readRangeByIdParameter(userID, offset, pageSize);
            log.debug("Leaving OrdersService class getPaginatedUserOrders() method. Amount of orders comment = {}", result.size());
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersService class, getPaginatedUserOrders() method.", e);
        }
        return result;
    }

    public int getOrdersNumberByStatus(boolean status) throws ServiceException {
        log.debug("Entering OrdersService class getOrdersNumberByStatus() method.");
        int ordersNumber;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersDao ordersDao = jdbcDaoFactory.getOrdersDao();
            ordersNumber = ordersDao.getNumberRowsByStatus(status);
            log.debug("Leaving OrdersService class getOrdersNumberByStatus() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersService class, getOrdersNumberByStatus() method.", e);
        }
        return ordersNumber;
    }

    public List<Order> getPaginatedByOrderStatus(boolean status, int pageNumber, int pageSize) throws ServiceException {
        log.debug("Entering OrdersService class getPaginated() method.");
        List<Order> result;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersDao ordersDao = jdbcDaoFactory.getOrdersDao();
            int offset = pageSize * pageNumber - pageSize;
            result = ordersDao.readRangeByStatus(status, offset, pageSize);
            log.debug("OrdersService class, getPaginated() method: result size = {}", result.size());
            log.debug("Leaving OrdersService class getPaginated() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersService class, getPaginated() method.", e);
        }
        return result;
    }

    public Order getOrderById(int orderID) throws ServiceException {
        log.debug("Entering OrdersService class getOrderBookById() method. Order Id = {}", orderID);
        Order order;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersDao ordersDao = jdbcDaoFactory.getOrdersDao();
            order = ordersDao.read(orderID);
            log.debug("Leaving OrdersService class getOrderBookById() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersService class, getOrderBookById() method.", e);
        }
        return order;
    }

    public void update(Order order) throws ServiceException {
        log.debug("Entering OrdersService class update() method. Order Id = {}", order.getId());
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                OrdersDao ordersDao = jdbcDaoFactory.getOrdersDao();
                ordersDao.update(order);

                OrdersBooksDao ordersBooksDao = jdbcDaoFactory.getOrdersBooksDao();
                List<OrderBook> ordersBooks = ordersBooksDao.readAllByIdParameter(order.getId());

                for (OrderBook ordersBook : ordersBooks) {
                    ordersBook.setIssued(order.isStatus());
                    ordersBooksDao.update(ordersBook);
                }

            } catch (SQLException e){
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrdersService class, update() method. TRANSACTION failed.", e);
            }
            log.debug("Leaving OrdersService class update() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersService class, update() method.", e);
        }
    }

    public void delete(Order order) throws ServiceException {
        log.debug("Entering OrdersService class delete() method. Order Id = {}", order.getId());
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                OrdersBooksDao ordersBooksDao = jdbcDaoFactory.getOrdersBooksDao();
                ordersBooksDao.deleteByIdParameter(order.getId());

                OrdersDao ordersDao = jdbcDaoFactory.getOrdersDao();
                ordersDao.delete(order);
                jdbcDaoFactory.endTransaction();
            } catch (SQLException e){
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: OrdersService class, delete() method. TRANSACTION failed.", e);
            }
            log.debug("Leaving OrdersService class delete() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersService class, delete() method.", e);
        }
    }
}

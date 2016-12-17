package com.epam.adk.web.library.service;

import com.epam.adk.web.library.dao.DaoFactory;
import com.epam.adk.web.library.dao.OrdersBooksDao;
import com.epam.adk.web.library.dao.jdbc.JdbcDaoFactory;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.OrderBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * OrdersBooksService class created on 05.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class OrdersBooksService {

    private static final Logger log = LoggerFactory.getLogger(OrdersBooksService.class);

    public OrderBook submitOrderBook(OrderBook order) throws ServiceException {
        log.debug("Entering OrdersBooksService class submitOrderBook() method.");
        OrderBook orderRequest;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
            orderRequest = orderDao.create(order);
            log.debug("Leaving OrdersBooksService class submitOrderBook() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, submitOrderBook() method.", e);
        }
        return orderRequest;
    }

    public int getOrderedBooksNumber(OrderBook order) throws ServiceException {
        log.debug("Entering OrdersBooksService class getOrderNumber() method.");
        int orderNumber;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
            orderNumber = orderDao.countOrderedBooks(order);
            log.debug("Leaving OrdersBooksService class getOrderNumber() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, getOrderNumber() method.", e);
        }
        return orderNumber;
    }

    public int getOrdersNumberByUser(int id) throws ServiceException {
        log.debug("Entering OrdersBooksService class getOrdersNumberByUser() method.");
        int ordersNumber;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
            ordersNumber = orderDao.getNumberRowsByIdParameter(id);
            log.debug("Leaving OrdersBooksService class getOrdersNumberByUser() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, getOrdersNumberByUser() method.", e);
        }
        return ordersNumber;
    }

    public List<OrderBook> getPaginatedUserOrders(int userID, int pageNumber, int pageSize) throws ServiceException {
        log.debug("Entering OrdersBooksService class getPaginated() method. User id = {}", userID);
        List<OrderBook> result;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
            int offset = pageSize * pageNumber - pageSize;
            result = orderDao.readRangeByIdParameter(userID, offset, pageSize);
            log.debug("Leaving OrdersBooksService class getPaginated() method. Amount of orders comment = {}", result.size());
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, getPaginated() method.", e);
        }
        return result;
    }

    public List<OrderBook> getPaginatedByOrderStatus(int statusID, int pageNumber, int pageSize) throws ServiceException {
        log.debug("Entering OrdersBooksService class getPaginated() method.");
        List<OrderBook> result;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
            int offset = pageSize * pageNumber - pageSize;
            result = orderDao.readRangeByStatusId(statusID, offset, pageSize);
            log.debug("OrdersBooksService class, getPaginated() method: result size = {}", result.size());
            log.debug("Leaving OrdersBooksService class getPaginated() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, getPaginated() method.", e);
        }
        return result;
    }

//    public int getOrdersNumberByStatusID(int statusID) throws ServiceException {
//        log.debug("Entering OrdersBooksService class getOrdersNumberByStatusID() method.");
//        int ordersNumber;
//        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
//            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
//            ordersNumber = orderDao.getNumberRowsByStatusId(statusID);
//            log.debug("Leaving OrdersBooksService class getOrdersNumberByStatusID() method.");
//        } catch (SQLException | DaoException e) {
//            throw new ServiceException("Error: OrdersBooksService class, getOrdersNumberByStatusID() method.", e);
//        }
//        return ordersNumber;
//    }

    public int getOrdersNumberByBookID(int bookID) throws ServiceException {
        log.debug("Entering OrdersBooksService class getOrdersNumberByBookID() method.");
        int ordersNumber;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
            ordersNumber = orderDao.getNumberRowsByBookId(bookID);
            log.debug("Leaving OrdersBooksService class getOrdersNumberByBookID() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, getOrdersNumberByBookID() method.", e);
        }
        return ordersNumber;
    }

    public OrderBook getOrderById(int userID, int bookID) throws ServiceException {
        log.debug("Entering OrdersBooksService class getOrderById() method. user Id = {}, book Id = {}", userID, bookID);
        OrderBook order;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
            order = orderDao.read(userID, bookID);
            log.debug("Leaving OrdersBooksService class getOrderById() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, getOrderById() method.", e);
        }
        return order;
    }

    public void update(OrderBook order) throws ServiceException {
        log.debug("Entering OrdersBooksService class update() method. OrderBook Id = {}", order.getId());
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
            orderDao.update(order);
            log.debug("Leaving OrdersBooksService class update() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, update() method.", e);
        }
    }

    public void writeToHistory(OrderBook order) throws ServiceException {
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
            orderDao.insertIntoHistory(order);
            orderDao.delete(order);
            log.debug("Leaving OrdersBooksService class writeToHistory() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, writeToHistory() method.", e);
        }
    }

//    public void deleteRejectedOrderRequests() throws ServiceException {
//        log.debug("Entering OrdersBooksService class deleteRejectedOrderRequests() method. ");
//        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
//            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
//            orderDao.deleteAllOldRejectedOrderRequests();
//            log.debug("Leaving OrdersBooksService class deleteRejectedOrderRequests() method.");
//        } catch (SQLException | DaoException e) {
//            throw new ServiceException("Error: OrdersBooksService class, deleteRejectedOrderRequests() method.", e);
//        }
//    }

    public void delete(OrderBook order) throws ServiceException {
        log.debug("Entering OrdersBooksService class delete() method. ");
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersBooksDao orderDao = jdbcDaoFactory.getOrdersBooksDao();
            orderDao.delete(order);
            log.debug("Leaving OrdersBooksService class delete() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersBooksService class, delete() method.", e);
        }
    }
}

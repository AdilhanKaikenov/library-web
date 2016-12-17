package com.epam.adk.web.library.service;

import com.epam.adk.web.library.dao.DaoFactory;
import com.epam.adk.web.library.dao.OrdersDao;
import com.epam.adk.web.library.dao.jdbc.JdbcDaoFactory;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Order;
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

    public List<Order> getPaginated(int pageNumber, int pageSize) throws ServiceException {
        log.debug("Entering OrdersService class getPaginated() method. ");
        List<Order> result;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            OrdersDao ordersDao = jdbcDaoFactory.getOrdersDao();
            int offset = pageSize * pageNumber - pageSize;
            result = ordersDao.readRange(offset, pageSize);
            log.debug("Leaving OrdersService class getPaginated() method. Amount of orders comment = {}", result.size());
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: OrdersService class, getPaginated() method.", e);
        }
        return result;
    }

}

package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.OrdersDao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.model.enums.OrderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JdbcOrders class created on 05.12.2016
 *
 * @author Kaikenov Adilhan
 * @see OrdersDao
 **/
public class JdbcOrders extends JdbcDao<Order> implements OrdersDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcOrders.class);

    public JdbcOrders(Connection connection) {
        super(connection);
    }

    @Override
    protected String getUpdateByEntityQuery() {
        return null;
    }

    @Override
    protected List<Order> createListFrom(ResultSet resultSet) throws DaoException {
        log.debug("Entering JdbcOrders class, createListFrom() method");
        List<Order> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Order order = new Order();
                log.debug("Creating order from resultSet");
                order.setId(resultSet.getInt("ID"));
                User user = new User();
                user.setId(resultSet.getInt("USER_ID"));
                user.setLogin(resultSet.getString("LOGIN"));
                user.setFirstname(resultSet.getString("FIRSTNAME"));
                user.setSurname(resultSet.getString("SURNAME"));
                user.setPatronymic(resultSet.getString("PATRONYMIC"));
                user.setAddress(resultSet.getString("ADDRESS"));
                user.setMobilePhone(resultSet.getString("MOBILE_PHONE"));
                order.setUser(user);
                order.setOrderType(OrderType.from(resultSet.getString("ORDER_TYPE")));
                order.setOrderDate(resultSet.getDate("ORDER_DATE"));
                log.debug("Order successfully created in createFrom() method. Order id = {}", order.getId());
                result.add(order);
            }
            log.debug("Leaving JdbcOrders class, createListFrom() method. Amount of orders = {}", result.size());
        } catch (SQLException e) {
            log.error("Error: JdbcOrders class createListFrom() method. I can not create List of orders from resultSet. {}", e);
            throw new DaoException("Error: JdbcOrders class createListFrom() method. I can not create List of orders from resultSet.", e);
        }
        return result;
    }

    @Override
    protected String getReadByIdQuery() {
        return null;
    }

    @Override
    protected String getReadAllQuery() {
        return null;
    }

    @Override
    protected String getReadRangeQuery() {
        return "SELECT orders.id, user.id AS user_id, user.login, user.firstname, user.surname, user.patronymic, user.address, user.mobile_phone, order_type.type AS order_type, orders.order_date FROM orders INNER JOIN user ON orders.user_id = user.id INNER JOIN order_type ON orders.order_type = order_type.id ORDER BY orders.order_date LIMIT ? OFFSET ?";
    }

    @Override
    protected String getTableName() {
        return "orders";
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO orders(user_id, order_type, order_date, date_from, date_to) VALUES(?, ?, ?, ?, ?)";
    }

    @Override
    protected String getReadByEntityQuery() {
        return null;
    }

    @Override
    protected String getCountNumberRowsByIdParameterQuery() {
        return null;
    }

    @Override
    protected String getReadRangeByIdParameterQuery() {
        return null;
    }

    @Override
    protected String getReadAllByIdParameterQuery() {
        return null;
    }

    @Override
    protected PreparedStatement setFieldsInUpdateByEntityPreparedStatement(PreparedStatement preparedStatement, Order registerOrder) throws DaoException {
        return null;
    }

    @Override
    protected PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, Order order) throws DaoException {
        log.debug("Entering JdbcOrders class, setFieldsInCreatePreparedStatement() method.");
        try {
            log.debug("Set user ID: {}", order.getUser().getId());
            preparedStatement.setInt(1, order.getUser().getId());
            log.debug("Set order type: {}", order.getOrderType().getValue());
            preparedStatement.setInt(2, order.getOrderType().ordinal());
            log.debug("Set order date: {}", order.getOrderDate());
            preparedStatement.setDate(3, order.getOrderDate());
            log.debug("Set order date: {}", order.getFrom());
            preparedStatement.setDate(4, order.getFrom());
            log.debug("Set order date: {}", order.getTo());
            preparedStatement.setDate(5, order.getTo());
            log.debug("Leaving JdbcOrders class, setFieldsInCreatePreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcOrders class setFieldsInCreatePreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcOrderDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement setFieldsInReadByEntityPreparedStatement(PreparedStatement preparedStatement, Order registerOrder) throws DaoException {
        return null;
    }
}

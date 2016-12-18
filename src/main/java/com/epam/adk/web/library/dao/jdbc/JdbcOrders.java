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
    protected List<Order> createListFrom(ResultSet resultSet) throws DaoException {
        log.debug("Entering JdbcOrders class, createListFrom() method");
        List<Order> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Order order = new Order();
                log.debug("Creating order from resultSet");
                order.setId(resultSet.getInt("ORDER_ID"));
                User user = new User();
                user.setId(resultSet.getInt("USER_ID"));
                user.setLogin(resultSet.getString("LOGIN"));
                user.setFirstname(resultSet.getString("FIRSTNAME"));
                user.setSurname(resultSet.getString("SURNAME"));
                user.setPatronymic(resultSet.getString("PATRONYMIC"));
                user.setAddress(resultSet.getString("ADDRESS"));
                user.setEmail(resultSet.getString("EMAIL"));
                user.setMobilePhone(resultSet.getString("MOBILE_PHONE"));
                order.setUser(user);
                order.setOrderType(OrderType.from(resultSet.getString("ORDER_TYPE")));
                order.setOrderDate(resultSet.getDate("ORDER_DATE"));
                order.setFrom(resultSet.getDate("DATE_FROM"));
                order.setTo(resultSet.getDate("DATE_TO"));
                order.setStatus(resultSet.getBoolean("STATUS"));
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
    public int getNumberRowsByStatus(boolean status) throws DaoException {
        log.debug("Entering JdbcOrders class, getNumberRowsByStatus() method. Status = {}", status);
        int numberRows = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement("SELECT COUNT(*) FROM orders WHERE status = ?");
            preparedStatement.setBoolean(1, status);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numberRows = resultSet.getInt(1);
            }
            log.debug("Leaving JdbcOrders class getNumberRowsByStatus() method. Rows number = {}", numberRows);
        } catch (SQLException e) {
            log.error("Error: JdbcOrders class, getNumberRowsByStatus() method. {}", e);
            throw new DaoException("Error: JdbcOrders class, getNumberRowsByStatus() method.", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return numberRows;
    }

    @Override
    public List<Order> readRangeByStatus(boolean status, int offset, int limit) throws DaoException {
        log.debug("Entering JdbcOrders class, readRangeByStatus() method");
        List<Order> result;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement("SELECT orders.id AS order_id, user.id AS user_id, user.login, user.firstname, user.surname, user.patronymic, user.address, user.email, user.mobile_phone, order_type.type AS order_type, orders.order_date, orders.date_from, orders.date_to, orders.status FROM orders INNER JOIN user ON orders.user_id = user.id INNER JOIN order_type ON orders.order_type = order_type.id WHERE orders.status = ? ORDER BY orders.order_date LIMIT ? OFFSET ?");
            preparedStatement.setBoolean(1, status);
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(3, offset);
            resultSet = preparedStatement.executeQuery();
            result = createListFrom(resultSet);
            log.debug("Leaving JdbcOrders class, readRangeByStatus() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcOrders class readRangeByStatus() method. {}", e);
            throw new DaoException("Error: JdbcOrders class readRangeByStatus() method. ", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return result;
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM orders WHERE id LIKE ?";
    }


    @Override
    protected String getReadByIdQuery() {
        return "SELECT orders.id AS order_id, user.id AS user_id, user.login, user.firstname, user.surname, user.patronymic, user.address, user.email, user.mobile_phone, order_type.type AS order_type, orders.order_date, orders.date_from, orders.date_to, orders.status FROM orders INNER JOIN user ON orders.user_id = user.id INNER JOIN order_type ON orders.order_type = order_type.id WHERE orders.id = ?";
    }

    @Override
    protected String getReadAllQuery() {
        return null;
    }

    @Override
    protected String getReadRangeQuery() {
        return "SELECT orders.id AS order_id, user.id AS user_id, user.login, user.firstname, user.surname, user.patronymic, user.address, user.email, user.mobile_phone, order_type.type AS order_type, orders.order_date, orders.date_from, orders.date_to, orders.status FROM orders INNER JOIN user ON orders.user_id = user.id INNER JOIN order_type ON orders.order_type = order_type.id ORDER BY orders.order_date LIMIT ? OFFSET ?";
    }

    @Override
    protected String getUpdateByEntityQuery() {
        return "UPDATE orders SET date_from = ?, date_to = ?, status = ? WHERE id LIKE ?";
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
        return "SELECT COUNT(*) FROM orders WHERE user_id = ?";
    }

    @Override
    protected String getReadRangeByIdParameterQuery() {
        return "SELECT orders.id AS order_id, user.id AS user_id, user.login, user.firstname, user.surname, user.patronymic, user.address, user.email, user.mobile_phone, order_type.type AS order_type, orders.order_date, orders.date_from, orders.date_to, orders.status FROM orders INNER JOIN user ON orders.user_id = user.id INNER JOIN order_type ON orders.order_type = order_type.id WHERE user.id = ? ORDER BY orders.order_date LIMIT ? OFFSET ?";
    }

    @Override
    protected String getReadAllByIdParameterQuery() {
        return null;
    }

    @Override
    protected PreparedStatement setFieldsInUpdateByEntityPreparedStatement(PreparedStatement preparedStatement, Order order) throws DaoException {
        log.debug("Entering JdbcOrders class, setFieldsInUpdateByEntityPreparedStatement() method.");
        try {
            log.debug("Set order date from: {}", order.getFrom());
            preparedStatement.setDate(1, order.getFrom());
            log.debug("Set order date to: {}", order.getTo());
            preparedStatement.setDate(2, order.getTo());
            log.debug("Set order status: {}", order.isStatus());
            preparedStatement.setBoolean(3, order.isStatus());
            log.debug("Set order id: {}", order.getId());
            preparedStatement.setInt(4, order.getId());
            log.debug("Leaving JdbcOrders class, setFieldsInUpdateByEntityPreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcOrders class setFieldsInUpdateByEntityPreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcOrderDao class setFieldsInUpdateByEntityPreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
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
            log.debug("Set order date from: {}", order.getFrom());
            preparedStatement.setDate(4, order.getFrom());
            log.debug("Set order date to: {}", order.getTo());
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

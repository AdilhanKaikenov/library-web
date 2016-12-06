package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.OrderDao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.model.enums.OrderStatus;
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
 * JdbcOrderDao class created on 05.12.2016
 *
 * @author Kaikenov Adilhan
 * @see OrderDao
 **/
public class JdbcOrderDao extends JdbcDao<Order> implements OrderDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcOrderDao.class);
    private static final String TABLE_NAME = "orders";
    private static final String CREATE_QUERY = "INSERT INTO orders(user_id, book_id, order_date, order_type, date_from, " +
            "date_to) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String COUNT_ORDERS_QUERY = "SELECT COUNT(*) FROM orders WHERE book_id = ? AND user_id = ? AND order_type = ?";
    private static final String SELECT_COUNT_BY_USER_ID = "SELECT COUNT(*) FROM orders WHERE user_id = ?";
    private static final String SELECT_RANGE_BY_ID_QUERY = "SELECT orders.id, orders.user_id, orders.book_id, book.title AS " +
            "book_title, orders.order_date, order_type.type AS order_type, orders.date_from, orders.date_to, order_status.type AS order_status, " +
            "book.total_amount - (SELECT COUNT(*) FROM orders WHERE orders.book_id = book.id AND orders.status = 0) AS available_amount, " +
            "concat(user.firstname, ' ', user.surname, ' ', user.patronymic) AS client " +
            "FROM orders, order_type, book, order_status, user WHERE orders.order_type = order_type.id AND orders.book_id = book.id AND " +
            "orders.status = order_status.id AND user.id = ? ORDER BY order_date LIMIT ? OFFSET ?";
    private static final String SELECT_BY_RANGE_QUERY = "SELECT orders.id, orders.user_id, orders.book_id, book.title AS " +
            "book_title, orders.order_date, order_type.type AS order_type, orders.date_from, orders.date_to, order_status.type AS order_status, " +
            "book.total_amount - (SELECT COUNT(*) FROM orders WHERE orders.book_id = book.id AND orders.status = 0) AS available_amount, " +
            "concat(user.firstname, ' ', user.surname, ' ', user.patronymic) AS client " +
            "FROM orders, order_type, book, order_status, user WHERE orders.order_type = order_type.id AND orders.book_id = book.id AND " +
            "orders.status = order_status.id AND orders.user_id = user.id ORDER BY order_date LIMIT ? OFFSET ?";

    public JdbcOrderDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<Order> createListFrom(ResultSet resultSet) throws DaoException {
        log.debug("Entering JdbcOrderDao class, createListFrom() method");
        List<Order> result = new ArrayList<>();
        try {
            while (resultSet.next()){
                Order order = new Order();
                log.debug("Creating order from resultSet");
                order.setId(resultSet.getInt("ID"));
                order.setUserID(resultSet.getInt("USER_ID"));
                order.setBookID(resultSet.getInt("BOOK_ID"));
                order.setClient(resultSet.getString("CLIENT"));
                order.setBookTitle(resultSet.getString("BOOK_TITLE"));
                order.setOrderDate(resultSet.getDate("ORDER_DATE"));
                order.setType(OrderType.from(resultSet.getString("ORDER_TYPE")));
                order.setFrom(resultSet.getDate("DATE_FROM"));
                order.setTo(resultSet.getDate("DATE_TO"));
                order.setAvailableBookAmount(resultSet.getInt("AVAILABLE_AMOUNT"));
                order.setStatus(OrderStatus.valueOf(resultSet.getString("ORDER_STATUS")));
                log.debug("Order successfully created in createFrom() method. Order id = {}", order.getId());
                result.add(order);
            }
        } catch (SQLException e) {
            log.error("Error: JdbcOrderDao class createListFrom() method. I can not create List of orders from resultSet. {}", e);
            throw new DaoException("Error: JdbcOrderDao class createListFrom() method. I can not create List of orders from resultSet.", e);
        }
        log.debug("Leaving JdbcOrderDao class, createListFrom() method.");
        return result;
    }

    @Override
    public int countOrder(Order order) throws DaoException {
        log.debug("Entering JdbcOrderDao class, countOrder() method");
        int count = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement(COUNT_ORDERS_QUERY);
            preparedStatement.setInt(1, order.getBookID());
            preparedStatement.setInt(2, order.getUserID());
            preparedStatement.setInt(3, order.getType().ordinal());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            log.error("Error: JdbcOrderDao class countOrder() method.", e);
            throw new DaoException("Error: JdbcOrderDao class countOrder() method.", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        log.debug("Leaving JdbcOrderDao class, countOrder() method.");
        return count;
    }

    @Override
    protected PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, Order entity) throws DaoException {
        log.debug("Entering JdbcOrderDao class, setFieldsInCreatePreparedStatement() method.");
        try {
            preparedStatement.setInt(1, entity.getUserID());
            preparedStatement.setInt(2, entity.getBookID());
            preparedStatement.setDate(3, entity.getOrderDate());
            preparedStatement.setInt(4, entity.getType().ordinal());
            preparedStatement.setDate(5, entity.getFrom());
            preparedStatement.setDate(6, entity.getTo());
            log.debug("Leaving JdbcOrderDao class, setFieldsInCreatePreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcOrderDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcOrderDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
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
        return SELECT_BY_RANGE_QUERY;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getCreateQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String getReadByEntityQuery() {
        return null;
    }

    @Override
    protected String getCountNumberRowsByIdParameterQuery() {
        return SELECT_COUNT_BY_USER_ID;
    }

    @Override
    protected String getReadRangeByIdParameterQuery() {
        return SELECT_RANGE_BY_ID_QUERY;
    }

    @Override
    protected String getReadAllByIdParameterQuery() {
        return null;
    }

    @Override
    protected PreparedStatement setFieldsInReadByEntityPreparedStatement(PreparedStatement preparedStatement, Order entity) throws DaoException {
        return null;
    }
}

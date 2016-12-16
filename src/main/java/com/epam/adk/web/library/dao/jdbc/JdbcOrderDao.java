package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.OrderDao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.model.enums.OrderStatus;
import com.epam.adk.web.library.model.enums.OrderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
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
    private static final String CREATE_QUERY = queryProperties.get("insert.order");
    private static final String INSERT_INTO_HISTORY_QUERY = queryProperties.get("insert.into.orders.history");
    private static final String COUNT_ORDERS_QUERY = queryProperties.get("select.count.orders");
    private static final String SELECT_COUNT_BY_USER_ID_QUERY = queryProperties.get("select.count.by.user.id");
    private static final String SELECT_RANGE_BY_ID_QUERY = queryProperties.get("select.range.orders.by.user.id");
    private static final String SELECT_RANGE_QUERY = queryProperties.get("select.orders.range");
    private static final String SELECT_RANGE_BY_STATUS_ID_QUERY = queryProperties.get("select.range.orders.by.status.id");
    private static final String COUNT_ORDERS_BY_STATUS_ID_QUERY = queryProperties.get("select.count.orders.by.status.id");
    private static final String COUNT_ORDERS_BY_BOOK_ID_QUERY = queryProperties.get("select.count.orders.by.book.id");
    private static final String SELECT_BY_ID_QUERY = queryProperties.get("select.by.id");
    private static final String UPDATE_QUERY = queryProperties.get("update.order");
    private static final String DELETE_ALL_OLD_REJECTED_ORDERS_QUERY = queryProperties.get("delete.all.old.rejected.orders");

    public JdbcOrderDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<Order> createListFrom(ResultSet resultSet) throws DaoException {
        log.debug("Entering JdbcOrderDao class, createListFrom() method");
        List<Order> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Order order = new Order();
                log.debug("Creating order from resultSet");
                order.setId(resultSet.getInt("ID"));
                User user = new User();
                user.setId(resultSet.getInt("USER_ID"));
                order.setUser(user);
                Book book = new Book();
                book.setId(resultSet.getInt("BOOK_ID"));
                book.setTitle(resultSet.getString("BOOK_TITLE"));
                order.setBook(book);
                order.setClient(resultSet.getString("CLIENT"));
                order.setOrderDate(resultSet.getDate("ORDER_DATE"));
                order.setType(OrderType.from(resultSet.getString("ORDER_TYPE")));
                order.setFrom(resultSet.getDate("DATE_FROM"));
                order.setTo(resultSet.getDate("DATE_TO"));
                order.setAvailableBookAmount(resultSet.getInt("AVAILABLE_AMOUNT"));
                order.setStatus(OrderStatus.valueOf(resultSet.getString("ORDER_STATUS")));
                log.debug("Order successfully created in createFrom() method. Order id = {}", order.getId());
                result.add(order);
            }
            log.debug("Leaving JdbcOrderDao class, createListFrom() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcOrderDao class createListFrom() method. I can not create List of orders from resultSet. {}", e);
            throw new DaoException("Error: JdbcOrderDao class createListFrom() method. I can not create List of orders from resultSet.", e);
        }
        return result;
    }

    public void insertIntoHistory(Order order) throws DaoException {
        log.debug("Entering JdbcOrderDao class, insertIntoHistory() method.");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement(INSERT_INTO_HISTORY_QUERY);
            preparedStatement = setFieldsInCreatePreparedStatement(preparedStatement, order);
            preparedStatement.execute();
            log.debug("Order successfully inserted into history");
            log.debug("Leaving JdbcOrderDao class, insertIntoHistory() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcOrderDao class, insertIntoHistory() method. {}", e);
            throw new DaoException("Error: JdbcOrderDao class, insertIntoHistory() method.", e);
        } finally {
            closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public List<Order> readRangeByStatusId(int id, int offset, int limit) throws DaoException {
        log.debug("Entering JdbcOrderDao class, readRangeByStatusId() method");
        List<Order> result;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement(SELECT_RANGE_BY_STATUS_ID_QUERY);
            preparedStatement = setFieldsInReadRangeByIdParameterPreparedStatement(preparedStatement, id, offset, limit);
            resultSet = preparedStatement.executeQuery();
            result = createListFrom(resultSet);
            log.debug("Leaving JdbcOrderDao class, readRangeByStatusId() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcOrderDao class readRangeByStatusId() method. {}", e);
            throw new DaoException("Error: JdbcOrderDao class readRangeByStatusId() method. ", e);
        } finally {
            close(preparedStatement, resultSet);
        }
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
            log.debug("Set book ID: {}", order.getUser().getId());
            preparedStatement.setInt(1, order.getBook().getId());
            log.debug("Set user ID: {}", order.getUser().getId());
            preparedStatement.setInt(2, order.getUser().getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            log.debug("Leaving JdbcOrderDao class, countOrder() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcOrderDao class countOrder() method.", e);
            throw new DaoException("Error: JdbcOrderDao class countOrder() method.", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return count;
    }

    @Override
    public int getNumberRowsByStatusId(int statusID) throws DaoException {
        log.debug("Entering JdbcOrderDao class, getNumberRowsByStatusId() method. Status Id = {}", statusID);
        int numberRows = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement(COUNT_ORDERS_BY_STATUS_ID_QUERY);
            preparedStatement = setFieldInCountNumberRowsByIdPreparedStatement(preparedStatement, statusID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numberRows = resultSet.getInt(1);
            }
            log.debug("Leaving JdbcOrderDao class getNumberRowsByStatusId() method. Rows number = {}", numberRows);
        } catch (SQLException e) {
            log.error("Error: JdbcOrderDao class, getNumberRowsByStatusId() method. {}", e);
            throw new DaoException("Error: JdbcOrderDao class, getNumberRowsByStatusId() method.", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return numberRows;
    }

    @Override
    public int getNumberRowsByBookId(int bookID) throws DaoException {
        log.debug("Entering JdbcOrderDao class, getNumberRowsByBookId() method. Book Id = {}", bookID);
        int numberRows = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement(COUNT_ORDERS_BY_BOOK_ID_QUERY);
            preparedStatement = setFieldInCountNumberRowsByIdPreparedStatement(preparedStatement, bookID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numberRows = resultSet.getInt(1);
            }
            log.debug("Leaving JdbcOrderDao class getNumberRowsByBookId() method. Rows number = {}", numberRows);
        } catch (SQLException e) {
            log.error("Error: JdbcOrderDao class, getNumberRowsByBookId() method. {}", e);
            throw new DaoException("Error: JdbcOrderDao class, getNumberRowsByBookId() method.", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return numberRows;
    }

    @Override
    public void deleteAllOldRejectedOrderRequests() throws DaoException {
        log.debug("Entering JdbcOrderDao class, deleteAllOldRejectedOrderRequests() method.");
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(DELETE_ALL_OLD_REJECTED_ORDERS_QUERY);
            log.debug("Leaving JdbcOrderDao class deleteAllOldRejectedOrderRequests() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcOrderDao class, deleteAllOldRejectedOrderRequests() method. {}", e);
            throw new DaoException("Error: JdbcOrderDao class, deleteAllOldRejectedOrderRequests() method.", e);
        }
    }

    @Override
    protected PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, Order order) throws DaoException {
        log.debug("Entering JdbcOrderDao class, setFieldsInCreatePreparedStatement() method.");
        try {
            log.debug("Set user ID: {}", order.getUser().getId());
            preparedStatement.setInt(1, order.getUser().getId());
            log.debug("Set book ID: {}", order.getBook().getId());
            preparedStatement.setInt(2, order.getBook().getId());
            log.debug("Set order date: {}", order.getOrderDate());
            preparedStatement.setDate(3, order.getOrderDate());
            log.debug("Set order type: {}", order.getType());
            preparedStatement.setInt(4, order.getType().ordinal());
            log.debug("Set date from : {}", order.getFrom());
            preparedStatement.setDate(5, order.getFrom());
            log.debug("Set date to: {}", order.getTo());
            preparedStatement.setDate(6, order.getTo());
            log.debug("Leaving JdbcOrderDao class, setFieldsInCreatePreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcOrderDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcOrderDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement setFieldsInUpdateByEntityPreparedStatement(PreparedStatement preparedStatement, Order order) throws DaoException {
        log.debug("Entering JdbcOrderDao class, setFieldsInUpdateByEntityPreparedStatement() method.");
        try {
            log.debug("Set date from : {}", order.getFrom());
            preparedStatement.setDate(1, order.getFrom());
            log.debug("Set date to: {}", order.getTo());
            preparedStatement.setDate(2, order.getTo());
            log.debug("Set order status: {}", order.getStatus());
            preparedStatement.setInt(3, order.getStatus().ordinal());
            log.debug("Set order ID: {}", order.getId());
            preparedStatement.setInt(4, order.getId());
            log.debug("Leaving JdbcOrderDao class, setFieldsInUpdateByEntityPreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcOrderDao class setFieldsInUpdateByEntityPreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcOrderDao class setFieldsInUpdateByEntityPreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected String getUpdateByEntityQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getReadByIdQuery() {
        return SELECT_BY_ID_QUERY;
    }

    @Override
    protected String getReadAllQuery() {
        return null;
    }

    @Override
    protected String getReadRangeQuery() {
        return SELECT_RANGE_QUERY;
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
        return SELECT_COUNT_BY_USER_ID_QUERY;
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

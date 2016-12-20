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

    private static final String TABLE_NAME = "orders";
    private static final String INSERT_QUERY = queryProperties.get("insert.order");
    private static final String DELETE_QUERY = queryProperties.get("delete.orders");
    private static final String UPDATE_QUERY = queryProperties.get("update.order");
    private static final String SELECT_BY_ID_QUERY = queryProperties.get("select.order.by.id");
    private static final String SELECT_RANGE_QUERY = queryProperties.get("select.orders.range");
    private static final String SELECT_COLUMNS_PART = queryProperties.get("select.orders.columns");
    private static final String SELECT_COUNT_BY_STATUS_QUERY = queryProperties.get("select.count.orders.by.status");
    private static final String SELECT_RANGE_BY_STATUS_QUERY = queryProperties.get("select.range.orders.by.status");
    private static final String SELECT_COUNT_BY_USER_ID_QUERY = queryProperties.get("select.count.orders.by.user.id");
    private static final String SELECT_RANGE_BY_USER_ID_QUERY = queryProperties.get("select.range.by.user.id");

    private static final String ORDER_ID_COLUMN_NAME = "ORDER_ID";
    private static final String USER_ID_COLUMN_NAME = "USER_ID";
    private static final String LOGIN_COLUMN_NAME = "LOGIN";
    private static final String FIRSTNAME_COLUMN_NAME = "FIRSTNAME";
    private static final String SURNAME_COLUMN_NAME = "SURNAME";
    private static final String PATRONYMIC_COLUMN_NAME = "PATRONYMIC";
    private static final String ADDRESS_COLUMN_NAME = "ADDRESS";
    private static final String EMAIL_COLUMN_NAME = "EMAIL";
    private static final String MOBILE_PHONE_COLUMN_NAME = "MOBILE_PHONE";
    private static final String ORDER_TYPE_COLUMN_NAME = "ORDER_TYPE";
    private static final String ORDER_DATE_COLUMN_NAME = "ORDER_DATE";
    private static final String DATE_FROM_COLUMN_NAME = "DATE_FROM";
    private static final String DATE_TO_COLUMN_NAME = "DATE_TO";
    private static final String STATUS_COLUMN_NAME = "STATUS";

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
                order.setId(resultSet.getInt(ORDER_ID_COLUMN_NAME));
                User user = new User();
                user.setId(resultSet.getInt(USER_ID_COLUMN_NAME));
                user.setLogin(resultSet.getString(LOGIN_COLUMN_NAME));
                user.setFirstname(resultSet.getString(FIRSTNAME_COLUMN_NAME));
                user.setSurname(resultSet.getString(SURNAME_COLUMN_NAME));
                user.setPatronymic(resultSet.getString(PATRONYMIC_COLUMN_NAME));
                user.setAddress(resultSet.getString(ADDRESS_COLUMN_NAME));
                user.setEmail(resultSet.getString(EMAIL_COLUMN_NAME));
                user.setMobilePhone(resultSet.getString(MOBILE_PHONE_COLUMN_NAME));
                order.setUser(user);
                order.setOrderType(OrderType.from(resultSet.getString(ORDER_TYPE_COLUMN_NAME)));
                order.setOrderDate(resultSet.getDate(ORDER_DATE_COLUMN_NAME));
                order.setFrom(resultSet.getDate(DATE_FROM_COLUMN_NAME));
                order.setTo(resultSet.getDate(DATE_TO_COLUMN_NAME));
                order.setStatus(resultSet.getBoolean(STATUS_COLUMN_NAME));
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
            preparedStatement = getConnection().prepareStatement(SELECT_COUNT_BY_STATUS_QUERY);
            preparedStatement.setBoolean(FIRST_PARAMETER_INDEX, status);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numberRows = resultSet.getInt(FIRST_COLUMN_INDEX);
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
            preparedStatement = getConnection().prepareStatement(SELECT_COLUMNS_PART + SELECT_RANGE_BY_STATUS_QUERY);
            preparedStatement.setBoolean(FIRST_PARAMETER_INDEX, status);
            preparedStatement.setInt(SECOND_PARAMETER_INDEX, limit);
            preparedStatement.setInt(THIRD_PARAMETER_INDEX, offset);
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
    protected PreparedStatement setFieldsInUpdateByEntityPreparedStatement(PreparedStatement preparedStatement, Order order) throws DaoException {
        log.debug("Entering JdbcOrders class, setFieldsInUpdateByEntityPreparedStatement() method.");
        try {
            log.debug("Set order date from: {}", order.getFrom());
            preparedStatement.setDate(FIRST_PARAMETER_INDEX, order.getFrom());
            log.debug("Set order date to: {}", order.getTo());
            preparedStatement.setDate(SECOND_PARAMETER_INDEX, order.getTo());
            log.debug("Set order status: {}", order.isStatus());
            preparedStatement.setBoolean(THIRD_PARAMETER_INDEX, order.isStatus());
            log.debug("Set order id: {}", order.getId());
            preparedStatement.setInt(FOURTH_PARAMETER_INDEX, order.getId());
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
            preparedStatement.setInt(FIRST_PARAMETER_INDEX, order.getUser().getId());
            log.debug("Set order type: {}", order.getOrderType().getValue());
            preparedStatement.setInt(SECOND_PARAMETER_INDEX, order.getOrderType().ordinal());
            log.debug("Set order date: {}", order.getOrderDate());
            preparedStatement.setDate(THIRD_PARAMETER_INDEX, order.getOrderDate());
            log.debug("Set order date from: {}", order.getFrom());
            preparedStatement.setDate(FOURTH_PARAMETER_INDEX, order.getFrom());
            log.debug("Set order date to: {}", order.getTo());
            preparedStatement.setDate(FIFTH_PARAMETER_INDEX, order.getTo());
            log.debug("Leaving JdbcOrders class, setFieldsInCreatePreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcOrders class setFieldsInCreatePreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcOrderDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    protected String getReadByIdQuery() {
        return SELECT_COLUMNS_PART + SELECT_BY_ID_QUERY;
    }

    @Override
    protected String getReadRangeQuery() {
        return SELECT_COLUMNS_PART + SELECT_RANGE_QUERY;
    }

    @Override
    protected String getUpdateByEntityQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getCreateQuery() {
        return INSERT_QUERY;
    }

    @Override
    protected String getCountNumberRowsByIdParameterQuery() {
        return SELECT_COUNT_BY_USER_ID_QUERY;
    }

    @Override
    protected String getReadRangeByIdParameterQuery() {
        return SELECT_COLUMNS_PART + SELECT_RANGE_BY_USER_ID_QUERY;
    }

    @Override
    protected String getReadByEntityQuery() {
        return null;
    }

    @Override
    protected String getDeleteByIdQuery() {
        return null;
    }

    @Override
    protected String getReadAllByIdParameterQuery() {
        return null;
    }

    @Override
    protected PreparedStatement setFieldsInReadByEntityPreparedStatement(PreparedStatement preparedStatement, Order registerOrder) throws DaoException {
        return null;
    }
}

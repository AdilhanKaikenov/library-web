package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.OrdersBooksDao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.OrderBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * JdbcOrdersBooksDao class created on 05.12.2016
 *
 * @author Kaikenov Adilhan
 * @see OrdersBooksDao
 **/
public class JdbcOrdersBooksDao extends JdbcDao<OrderBook> implements OrdersBooksDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcOrdersBooksDao.class);
    private static final String TABLE_NAME = "orders";


    public JdbcOrdersBooksDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<OrderBook> createListFrom(ResultSet resultSet) throws DaoException {

        return null;
    }

    @Override
    public OrderBook read(int userID, int bookID) throws DaoException {

        return null;
    }

    public void insertIntoHistory(OrderBook order) throws DaoException {

    }

    @Override
    public List<OrderBook> readRangeByStatusId(int id, int offset, int limit) throws DaoException {

        return null;
    }

    @Override
    public int countOrderedBooks(OrderBook order) throws DaoException {
        log.debug("Entering JdbcOrdersBooksDao class, countOrderedBooks() method");
        int count = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement("SELECT COUNT(*) FROM orders_books WHERE book_id = ? AND user_id = ?");
            log.debug("Set book ID: {}", order.getUser().getId());
            preparedStatement.setInt(1, order.getBook().getId());
            log.debug("Set user ID: {}", order.getUser().getId());
            preparedStatement.setInt(2, order.getUser().getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            log.debug("Leaving JdbcOrdersBooksDao class, countOrderedBooks() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcOrdersBooksDao class countOrderedBooks() method.", e);
            throw new DaoException("Error: JdbcOrdersBooksDao class countOrderedBooks() method.", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return count;
    }

     @Override
    public int getNumberRowsByBookId(int bookID) throws DaoException {

        return 0;
    }

     @Override
    public void delete(OrderBook orderBook) throws DaoException {

    }

    @Override
    protected PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, OrderBook orderBook) throws DaoException {
        log.debug("Entering JdbcOrdersBooksDao class, setFieldsInCreatePreparedStatement() method.");
        try {
            log.debug("Set user ID: {}", orderBook.getUser().getId());
            preparedStatement.setInt(1, orderBook.getUser().getId());
            log.debug("Set book ID: {}", orderBook.getBook().getId());
            preparedStatement.setInt(2, orderBook.getBook().getId());
            log.debug("Set order ID: {}", orderBook.getOrder().getId());
            preparedStatement.setInt(3, orderBook.getOrder().getId());

            log.debug("Leaving JdbcOrdersBooksDao class, setFieldsInCreatePreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcOrdersBooksDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcOrdersBooksDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;

    }

    @Override
    protected PreparedStatement setFieldsInUpdateByEntityPreparedStatement(PreparedStatement preparedStatement, OrderBook orderBook) throws DaoException {

        return preparedStatement;
    }

    @Override
    protected String getUpdateByEntityQuery() {
        return null;
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
        return null;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO orders_books(user_id, book_id, register_order_id) VALUES(?, ?, ?)";
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
    protected PreparedStatement setFieldsInReadByEntityPreparedStatement(PreparedStatement preparedStatement, OrderBook orderBook) throws DaoException {
        return null;
    }
}

package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.OrdersBooksDao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.model.OrderBook;
import com.epam.adk.web.library.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
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
        log.debug("Entering JdbcOrdersBooksDao class, createListFrom() method");
        List<OrderBook> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                OrderBook orderBook = new OrderBook();
                log.debug("Creating book of order from resultSet");
                User user = new User();
                user.setId(resultSet.getInt("USER_ID"));
                user.setLogin(resultSet.getString("LOGIN"));
                user.setFirstname(resultSet.getString("FIRSTNAME"));
                user.setSurname(resultSet.getString("SURNAME"));
                user.setPatronymic(resultSet.getString("PATRONYMIC"));
                user.setAddress(resultSet.getString("ADDRESS"));
                user.setEmail(resultSet.getString("EMAIL"));
                user.setMobilePhone(resultSet.getString("MOBILE_PHONE"));
                orderBook.setUser(user);
                Book book = new Book();
                book.setId(resultSet.getInt("BOOK_ID"));
                book.setTitle(resultSet.getString("TITLE"));
                book.setAuthors(resultSet.getString("AUTHORS"));
                book.setPublishYear(Year.of(resultSet.getInt("PUBLISH_YEAR")));
                orderBook.setBook(book);
                Order order = new Order();
                order.setId(resultSet.getInt("ORDER_ID"));
                order.setOrderDate(resultSet.getDate("ORDER_DATE"));
                order.setFrom(resultSet.getDate("DATE_FROM"));
                order.setTo(resultSet.getDate("DATE_TO"));
                order.setStatus(resultSet.getBoolean("STATUS"));
                orderBook.setOrder(order);
                orderBook.setAvailableBookAmount(resultSet.getInt("AVAILABLE_AMOUNT"));
                log.debug("OrderBook successfully created in createFrom() method.");
                result.add(orderBook);
            }
            log.debug("Leaving JdbcOrdersBooksDao class, createListFrom() method. Amount of orders books = {}", result.size());
        } catch (SQLException e) {
            log.error("Error: JdbcOrdersBooksDao class createListFrom() method. I can not create List of orders books from resultSet. {}", e);
            throw new DaoException("Error: JdbcOrdersBooksDao class createListFrom() method. I can not create List of orders books from resultSet.", e);
        }
        return result;
    }

    @Override
    public OrderBook read(int userID, int bookID) throws DaoException {
        log.debug("Entering JdbcOrdersBooksDao class, read(userID, bookID) method, User ID = {}, book ID = {}", userID, bookID);
        OrderBook result;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement("SELECT user.id AS user_id,  user.login, user.firstname,  user.surname, user.patronymic,  user.address, user.email,  user.mobile_phone, book.id AS book_id,  book.title, book.authors,  book.publish_year, book.total_amount - ordered_books.CNT AS available_amount,  orders.id AS order_id, orders.order_date,  orders.date_from,  orders.date_to, orders.status FROM orders_books, (SELECT orders_books.book_id, COUNT(*) AS CNT FROM orders_books GROUP BY orders_books.book_id) AS ordered_books INNER JOIN user ON orders_books.user_id = user.id INNER JOIN book ON orders_books.book_id = book.id INNER JOIN orders ON orders_books.order_id = orders.id WHERE book.id = ordered_books.book_id  AND orders_books.user_id = ? AND orders_books.book_id = ?");
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, bookID);
            resultSet = preparedStatement.executeQuery();
            result = createFrom(resultSet);
            log.debug("Leaving JdbcOrdersBooksDao class, read(userID, bookID) method.");
        } catch (SQLException e) {
            log.error("Error: JdbcOrdersBooksDao class read(userID, bookID) method. I can not read book of order by id from resultSet. {}", e);
            throw new DaoException("Error: JdbcOrdersBooksDao class read(userID, bookID) method. I can not read book of order by id from resultSet.", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return result;
    }

    @Override
    public void delete(int userID, int bookID) throws DaoException {
        log.debug("Entering JdbcOrdersBooksDao class, delete(userID, bookID) method, User ID = {}, book ID = {}", userID, bookID);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement("DELETE FROM orders_books WHERE orders_books.user_id  LIKE ? AND orders_books.book_id LIKE ?");
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, bookID);
            preparedStatement.execute();
            log.debug("Leaving JdbcOrdersBooksDao class, delete(userID, bookID) method.");
        } catch (SQLException e) {
            log.error("Error: JdbcOrdersBooksDao class delete(userID, bookID) method. I can not delete book of order by id. {}", e);
            throw new DaoException("Error: JdbcOrdersBooksDao class delete(userID, bookID) method. I can not delete book of order by id.", e);
        } finally {
            closePreparedStatement(preparedStatement);
        }
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
    protected PreparedStatement setFieldsInDeleteByEntityStatement(PreparedStatement preparedStatement, OrderBook orderBook) throws DaoException {
        log.debug("Entering JdbcOrdersBooksDao class, setFieldsInDeleteByEntityStatement() method.");
        try {
            log.debug("Set user ID: {}", orderBook.getUser().getId());
            preparedStatement.setInt(1, orderBook.getUser().getId());
            log.debug("Set book ID: {}", orderBook.getBook().getId());
            preparedStatement.setInt(2, orderBook.getBook().getId());
            log.debug("Leaving JdbcOrdersBooksDao class, setFieldsInDeleteByEntityStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcOrdersBooksDao class setFieldsInDeleteByEntityStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcOrdersBooksDao class setFieldsInDeleteByEntityStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement setFieldsInUpdateByEntityPreparedStatement(PreparedStatement preparedStatement, OrderBook orderBook) throws DaoException {

        return preparedStatement;
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM orders_books WHERE orders_books.user_id LIKE ? AND orders_books.book_id LIKE ?";
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
        return "INSERT INTO orders_books(user_id, book_id, order_id) VALUES(?, ?, ?)";
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
        return "SELECT user.id AS user_id,  user.login, user.firstname,  user.surname, user.patronymic,  user.address, user.email,  user.mobile_phone, book.id AS book_id,  book.title, book.authors,  book.publish_year, book.total_amount - ordered_books.CNT AS available_amount,  orders.id AS order_id, orders.order_date,  orders.date_from,  orders.date_to, orders.status FROM orders_books, (SELECT orders_books.book_id, COUNT(*) AS CNT FROM orders_books GROUP BY orders_books.book_id) AS ordered_books INNER JOIN user ON orders_books.user_id = user.id INNER JOIN book ON orders_books.book_id = book.id INNER JOIN orders ON orders_books.order_id = orders.id WHERE orders.id = ? AND book.id = ordered_books.book_id";
    }

    @Override
    protected PreparedStatement setFieldsInReadByEntityPreparedStatement(PreparedStatement preparedStatement, OrderBook orderBook) throws DaoException {
        return null;
    }
}

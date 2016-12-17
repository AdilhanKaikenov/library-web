package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.BookDao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.model.enums.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

/**
 * JdbcBookDao class created on 1.12.2016
 *
 * @author Kaikenov Adilhan
 * @see BookDao
 */
public class JdbcBookDao extends JdbcDao<Book> implements BookDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcBookDao.class);

    private static final String TABLE_NAME = "book";
    private static final String SELECT_ALL = queryProperties.get("select.all.book");
    private static final String CREATE_QUERY = queryProperties.get("insert.book");
    private static final String SELECT_BY_ID = queryProperties.get("select.book.by.id");
    private static final String SELECT_RANGE_BY_GENRE = queryProperties.get("select.range.books.by.genre");
    private static final String SELECT_COUNT_BY_GENRE_ID = queryProperties.get("select.count.by.genre.id");
    private static final String SELECT_RANGE_QUERY = queryProperties.get("select.range.books");
    private static final String UPDATE_QUERY = queryProperties.get("update.book");
    private static final String SELECT_COUNT_ROWS_QUERY = queryProperties.get("select.count.rows.number");
    private static final String SELECT_FOUND_QUERY_PART_ONE = queryProperties.get("select.found.books.part.one");
    private static final String SELECT_FOUND_QUERY_PART_TWO = queryProperties.get("select.found.books.part.two");
    private static final String SELECT_COUNT_AVAILABLE_AMOUNT = "SELECT book.total_amount - cnt FROM book, (SELECT COUNT(*) AS cnt FROM orders_books WHERE orders_books.book_id = ?) WHERE book.id = ?";

    public JdbcBookDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<Book> createListFrom(ResultSet resultSet) throws DaoException {
        log.debug("Entering JdbcBookDao class, createListFrom() method");
        List<Book> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Book book = new Book();
                log.debug("Creating book from resultSet");
                book.setId(resultSet.getInt("ID"));
                book.setTitle(resultSet.getString("TITLE"));
                book.setCover(resultSet.getString("COVER"));
                book.setAuthors(resultSet.getString("AUTHORS"));
                book.setPublishYear(Year.of(resultSet.getInt("PUBLISH_YEAR")));
                book.setGenre(Genre.from(resultSet.getString("GENRE")));
                book.setDescription(resultSet.getString("DESCRIPTION"));
                book.setTotalAmount(resultSet.getInt("TOTAL_AMOUNT"));
                log.debug("Book successfully created in createListFrom() method. Book id = {}", book.getId());
                result.add(book);
            }
            log.debug("Leaving JdbcBookDao class, createListFrom() method. Amount of books = {}", result.size());
        } catch (SQLException e) {
            log.error("Error: JdbcBookDao class createListFrom() method. I can not create List of books from resultSet. {}", e);
            throw new DaoException("Error: JdbcBookDao class createListFrom() method. I can not create List of books from resultSet.", e);
        }
        return result;
    }

    @Override
    public List<Book> findByTitle(String title) throws DaoException {
        log.debug("Entering JdbcBookDao class, findByTitle() method");
        List<Book> result;
        String correctTitle = title;
        ResultSet resultSet = null;
        try (Statement statement = getConnection().createStatement()) {
            if (title.contains("'")) {
                correctTitle = title.replaceAll("'", "''");
            }
            resultSet = statement.executeQuery(SELECT_FOUND_QUERY_PART_ONE + correctTitle + SELECT_FOUND_QUERY_PART_TWO);
            result = createListFrom(resultSet);
            log.debug("Leaving JdbcBookDao class, findByTitle() method. Book found = {}", result.size());
        } catch (SQLException e) {
            log.error("Error: JdbcBookDao class findByTitle() method. I can not find books. {}", e);
            throw new DaoException("Error: JdbcBookDao class findByTitle() method. I can not find books.", e);
        } finally {
            closeResultSet(resultSet);
        }
        return result;
    }

    @Override
    public int countAvailableAmount(int bookID) throws DaoException {
        log.debug("Entering JdbcBookDao class, countAvailableAmount() method. Book ID = {}", bookID);
        int count = 0;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement = getConnection().prepareStatement(SELECT_COUNT_AVAILABLE_AMOUNT);
            preparedStatement.setInt(1, bookID);
            preparedStatement.setInt(2, bookID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            log.debug("Leaving JdbcBookDao class, countAvailableAmount() method. Book amount = {}", count);
        } catch (SQLException e) {
            log.error("Error: JdbcBookDao class countAvailableAmount() method. {}", e);
            throw new DaoException("Error: JdbcBookDao class countAvailableAmount() method.", e);
        }
        return count;
    }

    @Override
    protected PreparedStatement setFieldsInUpdateByEntityPreparedStatement(PreparedStatement preparedStatement, Book book) throws DaoException {
        log.debug("Entering JdbcBookDao class, setFieldsInUpdateByEntityPreparedStatement() method.");
        try {
            log.debug("Set title: {}", book.getTitle());
            preparedStatement.setString(1, book.getTitle());
            log.debug("Set cover: {}", book.getCover());
            preparedStatement.setString(2, book.getCover());
            log.debug("Set authors: {}", book.getAuthors());
            preparedStatement.setString(3, book.getAuthors());
            log.debug("Set publish year: {}", book.getPublishYear());
            preparedStatement.setInt(4, book.getPublishYear().getValue());
            log.debug("Set genre: {}", book.getGenre());
            preparedStatement.setInt(5, book.getGenre().ordinal());
            log.debug("Set description: length = {}", book.getDescription().length());
            preparedStatement.setString(6, book.getDescription());
            log.debug("Set total amount: {}", book.getTotalAmount());
            preparedStatement.setInt(7, book.getTotalAmount());
            log.debug("Set boolean, is deleted: {}", book.isDeleted());
            preparedStatement.setBoolean(8, book.isDeleted());
            log.debug("Set id: {}", book.getId());
            preparedStatement.setInt(9, book.getId());
            log.debug("Leaving JdbcBookDao class, setFieldsInUpdateByEntityPreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcBookDao class setFieldsInUpdateByEntityPreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcBookDao class setFieldsInUpdateByEntityPreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, Book book) throws DaoException {
        log.debug("Entering JdbcBookDao class, setFieldsInCreatePreparedStatement() method.");
        try {
            log.debug("Set title: {}", book.getTitle());
            preparedStatement.setString(1, book.getTitle());
            log.debug("Set authors: {}", book.getAuthors());
            preparedStatement.setString(2, book.getAuthors());
            log.debug("Set publish year: {}", book.getPublishYear());
            preparedStatement.setInt(3, book.getPublishYear().getValue());
            log.debug("Set genre: {}", book.getGenre());
            preparedStatement.setInt(4, book.getGenre().ordinal());
            log.debug("Set description: length = {}", book.getDescription().length());
            preparedStatement.setString(5, book.getDescription());
            log.debug("Set total amount: {}", book.getTotalAmount());
            preparedStatement.setInt(6, book.getTotalAmount());
            log.debug("Set cover: {}", book.getCover());
            preparedStatement.setString(7, book.getCover());
            log.debug("Leaving JdbcBookDao class, setFieldsInCreatePreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcBookDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcBookDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected String getCountNumberRowsQuery() {
        return SELECT_COUNT_ROWS_QUERY;
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
        return CREATE_QUERY;
    }

    @Override
    protected String getReadByIdQuery() {
        return SELECT_BY_ID;
    }

    @Override
    protected String getReadAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected String getReadRangeByIdParameterQuery() {
        return SELECT_RANGE_BY_GENRE;
    }

    @Override
    protected String getReadRangeQuery() {
        return SELECT_RANGE_QUERY;
    }

    @Override
    protected String getCountNumberRowsByIdParameterQuery() {
        return SELECT_COUNT_BY_GENRE_ID;
    }

    @Override
    protected String getReadByEntityQuery() {
        return null;
    }

    @Override
    protected String getReadAllByIdParameterQuery() {
        return null;
    }

    @Override
    protected PreparedStatement setFieldsInReadByEntityPreparedStatement(PreparedStatement preparedStatement, Book entity) throws DaoException {
        return null;
    }

}

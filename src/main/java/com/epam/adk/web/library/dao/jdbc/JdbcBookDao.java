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
    private static final String SELECT_ALL = "SELECT book.id, book.title, book.cover, book.authors, book.publish_year, " +
            "genre.type AS genre, book.description, book.total_amount FROM (SELECT * FROM book WHERE deleted = FALSE) AS book " +
            "INNER JOIN genre ON book.genre = genre.id";
    private static final String CREATE_QUERY = "INSERT INTO book (title, cover, authors, publish_year, genre, " +
            "description, total_amount) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT book.id, book.title, book.cover, book.authors, book.publish_year," +
            "genre.type AS genre, book.description, book.total_amount FROM (SELECT * FROM book WHERE deleted = FALSE) AS book " +
            "INNER JOIN genre ON book.genre = genre.id WHERE book.id = ?";
    private static final String SELECT_RANGE_BY_GENRE = "SELECT book.id, book.title, book.cover, book.authors, book.publish_year, " +
            "genre.type AS genre, book.description, book.total_amount FROM (SELECT * FROM book WHERE deleted = FALSE) AS book INNER JOIN genre ON " +
            "book.genre = genre.id AND genre.id = ? ORDER BY book.publish_year LIMIT ? OFFSET ?";
    private static final String SELECT_COUNT_BY_GENRE_ID = "SELECT COUNT(*) FROM book WHERE deleted = FALSE AND genre = ?";
    private static final String SELECT_BY_RANGE_QUERY = "SELECT book.id, book.title, book.cover, book.authors, book.publish_year, " +
            "genre.type AS genre, book.description, book.total_amount FROM (SELECT * FROM book WHERE deleted = FALSE) AS book " +
            "INNER JOIN genre ON book.genre = genre.id ORDER BY book.publish_year LIMIT ? OFFSET ?";
    private static final String UPDATE_QUERY = "UPDATE book SET title = ?, cover = ?, authors = ?, publish_year = ?, genre = ?, description = ?, total_amount = ?, deleted = ? WHERE id LIKE ?";
    private static final String SELECT_COUNT_ROWS_QUERY = "SELECT COUNT(*) FROM book WHERE deleted = FALSE";

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
                log.debug("Book successfully created in createFrom() method. Book id = {}", book.getId());
                result.add(book);
            }
            log.debug("Leaving JdbcBookDao class, createListFrom() method. Amount of books = {}", result.size());
        } catch (SQLException e) {
            log.error("Error: JdbcBookDao class createListFrom() method. I can not create List of books from resultSet. {}", e);
            throw new DaoException("Error: JdbcDao class createListFrom() method. I can not create List of books from resultSet.", e);
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
            resultSet = statement.executeQuery("SELECT book.id, book.title, book.cover, book.authors, book.publish_year, " +
                    "genre.type AS genre, book.description, book.total_amount FROM (SELECT * FROM book WHERE deleted = FALSE AND LOWER(book.title) " +
                    "LIKE LOWER('%" + correctTitle +"%')) AS book INNER JOIN genre ON book.genre = genre.id ORDER BY LOWER(book.title)");
            result = createListFrom(resultSet);
            log.debug("Leaving JdbcBookDao class, findByTitle() method. Book found = {}", result.size());
        } catch (SQLException e) {
            log.error("Error: JdbcBookDao class findByTitle() method. I can not find books. {}", e);
            throw new DaoException("Error: JdbcDao class findByTitle() method. I can not find books.", e);
        } finally {
            closeResultSet(resultSet);
        }
        return result;
    }

    @Override
    protected PreparedStatement setFieldsInUpdateByEntityPreparedStatement(PreparedStatement preparedStatement, Book book) throws DaoException {
        log.debug("Entering JdbcBookDao class, setFieldsInUpdateByEntityPreparedStatement() method.");
        try {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getCover());
            preparedStatement.setString(3, book.getAuthors());
            preparedStatement.setInt(4, book.getPublishYear().getValue());
            preparedStatement.setInt(5, book.getGenre().ordinal());
            preparedStatement.setString(6, book.getDescription());
            preparedStatement.setInt(7, book.getTotalAmount());
            preparedStatement.setBoolean(8, book.isDeleted());
            preparedStatement.setInt(9, book.getId());
            log.debug("Leaving JdbcBookDao class, setFieldsInUpdateByEntityPreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcBookDao class setFieldsInUpdateByEntityPreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcBookDao class setFieldsInUpdateByEntityPreparedStatement() method. I can not set fields into statement.", e);
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
        return SELECT_BY_RANGE_QUERY;
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
    protected PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, Book entity) throws DaoException {
        return null;
    }

    @Override
    protected PreparedStatement setFieldsInReadByEntityPreparedStatement(PreparedStatement preparedStatement, Book entity) throws DaoException {
        return null;
    }

}

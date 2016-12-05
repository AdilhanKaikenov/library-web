package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.BookDao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.model.enums.Genre;
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
 * JdbcBookDao class created on 1.12.2016
 *
 * @author Kaikenov Adilhan
 * @see BookDao
 */
public class JdbcBookDao extends JdbcDao<Book> implements BookDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcBookDao.class);

    private static final String TABLE_NAME = "book";
    private static final String SELECT_ALL = "SELECT book.id, book.title, book.cover, book.authors, book.publish_year, " +
            "genre.type AS genre, book.description, book.total_amount, book.available_amount FROM book " +
            "INNER JOIN genre ON book.genre = genre.id";
    private static final String CREATE_QUERY = "INSERT INTO book (title, cover, authors, publish_year, genre, " +
            "description, total_amount, available_amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT book.id, book.title, book.cover, book.authors, book.publish_year," +
            "genre.type AS genre, book.description, book.total_amount, book.available_amount FROM BOOK " +
            "INNER JOIN genre ON book.genre = genre.id WHERE book.id = ?";
    private static final String SELECT_RANGE_BY_GENRE = "SELECT book.id, book.title, book.cover, book.authors, book.publish_year, " +
            "genre.type AS genre, book.description, book.total_amount, book.available_amount FROM book INNER JOIN genre ON " +
            "book.genre = genre.id WHERE genre.id = ? ORDER BY book.publish_year LIMIT ? OFFSET ?";
    private static final String SELECT_COUNT_BY_GENRE_ID = "SELECT COUNT(*) FROM book WHERE genre = ?";
    private static final String SELECT_BY_RANGE_QUERY = "SELECT book.id, book.title, book.cover, book.authors, book.publish_year, " +
            "genre.type AS genre, book.description, book.total_amount, book.available_amount FROM book " +
            "INNER JOIN genre ON book.genre = genre.id ORDER BY book.publish_year LIMIT ? OFFSET ?";

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
                book.setAvailableAmount(resultSet.getInt("AVAILABLE_AMOUNT"));
                log.debug("Book successfully created in createFrom() method. Book id = {}", book.getId());
                result.add(book);
            }
        } catch (SQLException e) {
            log.error("Error: JdbcBookDao class createListFrom() method. I can not create List of books from resultSet. {}", e);
            throw new DaoException("Error: JdbcDao class createListFrom() method. I can not create List of books from resultSet.", e);
        }
        log.debug("Leaving JdbcBookDao class, createListFrom() method. Amount of books = {}", result.size());
        return result;
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

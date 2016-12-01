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

/**
 * JdbcBookDao class created on 1.12.2016
 *
 * @author Kaikenov Adilhan
 */
public class JdbcBookDao extends JdbcDao<Book> implements BookDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcBookDao.class);

    private static final String TABLE_NAME = "Book";
    private static final String SELECT_ALL_FROM_BOOK = "SELECT BOOK.ID, BOOK.TITLE, BOOK.COVER, BOOK.AUTHORS, BOOK.PUBLISH_YEAR, " +
            "GENRE.GENRE_TYPE AS GENRE, BOOK.DESCRIPTION, BOOK.TOTAL_AMOUNT, BOOK.AVAILABLE_AMOUNT FROM BOOK INNER JOIN GENRE ON BOOK.GENRE = GENRE.ID";

    public JdbcBookDao(Connection connection) {
        super(connection);
    }

    @Override
    protected String getReadAllQuery() {
        return SELECT_ALL_FROM_BOOK;
    }

    @Override
    protected Book createFrom(ResultSet resultSet) throws DaoException {
        log.debug("Entering JdbcBookDao class, createFrom() method");
        Book book = null;
        try {
            if (resultSet.next()) {
                book = new Book();
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
            }
        } catch (SQLException e) {
            log.error("Error: JdbcBookDao class createFrom() method. I can not create book from resultSet. {}", e);
            throw new DaoException("Error: JdbcBookDao class createFrom() method. I can not create book from resultSet.", e);
        }
        log.debug("Leaving JdbcBookDao class, createFrom() method.");
        return book;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getCreateQuery() {
        return null;
    }

    @Override
    protected String getReadByEntityQuery() {
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

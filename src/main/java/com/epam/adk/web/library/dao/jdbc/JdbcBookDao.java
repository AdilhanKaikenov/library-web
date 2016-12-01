package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.BookDao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

/**
 * JdbcBookDao class created on 1.12.2016
 *
 * @author Kaikenov Adilhan
 */
public class JdbcBookDao extends JdbcDao<Book> implements BookDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcBookDao.class);

    private static final String TABLE_NAME = "Book";
    private static final String SELECT_ALL_FROM_BOOK = "SELECT * FROM BOOK";

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
            int row = resultSet.getRow();
            log.debug("ResultSet size = {}", row);
            if (row != 0) {
                book = new Book();
                log.debug("Creating book from resultSet");
                book.setId(resultSet.getInt("ID"));
                // TODO: add fields & get and set methods
                log.debug("Book successfully created in createFrom() method. Book id = {}", book.getId());
            }
        } catch (SQLException e) {
            log.error("Error: JdbcBookDao class createFrom() method. " +
                    "I can not create book from resultSet. {}", e);
            throw new DaoException(MessageFormat.format(
                    "Error: JdbcBookDao class createFrom() method. " +
                            "I can not create book from resultSet. {0}", e));
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

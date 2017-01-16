package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.BookDao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.exception.DaoUnsupportedOperationException;
import com.epam.adk.web.library.model.Author;
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
    private static final String CREATE_QUERY = queryProperties.get("insert.book");
    private static final String DELETE_QUERY = queryProperties.get("delete.book");
    private static final String UPDATE_QUERY = queryProperties.get("update.book");
    private static final String SELECT_BY_ID = queryProperties.get("select.book.by.id");
    private static final String SELECT_RANGE_QUERY = queryProperties.get("select.range.books");
    private static final String CREATE_AUTHOR_QUERY = queryProperties.get("insert.author");
    private static final String SELECT_COLUMNS_PART = queryProperties.get("select.book.columns");
    private static final String SELECT_RANGE_BY_GENRE = queryProperties.get("select.range.books.by.genre");
    private static final String SELECT_COUNT_ROWS_QUERY = queryProperties.get("select.count.books.rows.number");
    private static final String SELECT_COUNT_BY_GENRE_ID = queryProperties.get("select.count.books.by.genre.id");
    private static final String SELECT_FOUND_QUERY_PART_ONE = queryProperties.get("select.found.books.part.one");
    private static final String SELECT_FOUND_QUERY_PART_TWO = queryProperties.get("select.found.books.part.two");
    private static final String SELECT_AUTHOR_BY_NAME_QUERY = queryProperties.get("select.author.by.name");

    private static final String SINGLE_QUOTE = "'";
    private static final String DOUBLE_QUOTE = "''";

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
                log.debug("Creating book getFromValue resultSet");
                book.setId(resultSet.getInt(ID_COLUMN_NAME));
                book.setTitle(resultSet.getString(TITLE_COLUMN_NAME));
                book.setCover(resultSet.getString(COVER_COLUMN_NAME));
                Author authorInstance = new Author();
                authorInstance.setName(resultSet.getString(AUTHOR_COLUMN_NAME));
                book.setAuthor(readAuthorByName(authorInstance));
                book.setPublishYear(Year.of(resultSet.getInt(PUBLISH_YEAR_COLUMN_NAME)));
                book.setGenre(Genre.getFromValue(resultSet.getString(GENRE_COLUMN_NAME)));
                book.setDescription(resultSet.getString(DESCRIPTION_COLUMN_NAME));
                book.setTotalAmount(resultSet.getInt(TOTAL_AMOUNT_COLUMN_NAME));
                log.debug("Book successfully created in createListFrom() method. Book id = {}", book.getId());
                result.add(book);
            }
            log.debug("Leaving JdbcBookDao class, createListFrom() method. Amount of books = {}", result.size());
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcBookDao class createListFrom() method. I can not create List of books getFromValue resultSet.", e);
        }
        return result;
    }

    /**
     * The method creates a new author in the reference table.
     *
     * @param author
     * @return Author
     * @throws DaoException
     */
    private Author createAuthor(Author author) throws DaoException {
        log.debug("Entering JdbcBookDao class, createAuthor() method.");
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement(CREATE_AUTHOR_QUERY);
            preparedStatement.setString(FIRST_PARAMETER_INDEX, author.getName());
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            Integer id = getID(resultSet);
            if (id != null) {
                author.setId(id);
            }
            log.debug("New author successfully created, type = {}, id = {}", author.getId());
            log.debug("Leaving JdbcBookDao class, createAuthor() method.");
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcBookDao class, createAuthor() method.", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return author;
    }

    /**
     * The method of reading the author getFromValue the reference table.
     *
     * @param author
     * @return Author
     * @throws DaoException
     */
    private Author readAuthorByName(Author author) throws DaoException {
        log.debug("Entering JdbcBookDao class, readAuthorByName() method");
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement(SELECT_AUTHOR_BY_NAME_QUERY);
            preparedStatement.setString(FIRST_PARAMETER_INDEX, author.getName());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                author.setId(resultSet.getInt(ID_COLUMN_NAME));
            } else {
                return null;
            }
            log.debug("Leaving JdbcBookDao class, readAuthorByName() method.");
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcBookDao class readAuthorByName() method. I can not read author.", e);
        } finally {
            close(preparedStatement, resultSet);
        }
        return author;
    }

    @Override
    public List<Book> findByTitle(String title) throws DaoException {
        log.debug("Entering JdbcBookDao class, findByTitle() method");
        List<Book> result;
        String correctTitle = title;
        ResultSet resultSet = null;
        try (Statement statement = getConnection().createStatement()) {
            if (title.contains(SINGLE_QUOTE)) {
                correctTitle = title.replaceAll(SINGLE_QUOTE, DOUBLE_QUOTE);
            }
            resultSet = statement.executeQuery(SELECT_COLUMNS_PART + SELECT_FOUND_QUERY_PART_ONE + correctTitle + SELECT_FOUND_QUERY_PART_TWO);
            result = createListFrom(resultSet);
            log.debug("Leaving JdbcBookDao class, findByTitle() method. Book found = {}", result.size());
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcBookDao class findByTitle() method. I can not find books.", e);
        } finally {
            closeResultSet(resultSet);
        }
        return result;
    }

    @Override
    protected PreparedStatement setFieldsInUpdateByEntityPreparedStatement(PreparedStatement preparedStatement, Book book) throws DaoException {
        log.debug("Entering JdbcBookDao class, setFieldsInUpdateByEntityPreparedStatement() method.");
        try {
            preparedStatement.setString(FIRST_PARAMETER_INDEX, book.getTitle());
            preparedStatement.setString(SECOND_PARAMETER_INDEX, book.getCover());
            preparedStatement.setInt(THIRD_PARAMETER_INDEX, book.getAuthor().getId());
            preparedStatement.setInt(FOURTH_PARAMETER_INDEX, book.getPublishYear().getValue());
            preparedStatement.setInt(FIFTH_PARAMETER_INDEX, book.getGenre().ordinal());
            preparedStatement.setString(SIXTH_PARAMETER_INDEX, book.getDescription());
            preparedStatement.setInt(SEVENTH_PARAMETER_INDEX, book.getTotalAmount());
            preparedStatement.setBoolean(EIGHTH_PARAMETER_INDEX, book.isDeleted());
            preparedStatement.setInt(NINTH_PARAMETER_INDEX, book.getId());
            log.debug("Leaving JdbcBookDao class, setFieldsInUpdateByEntityPreparedStatement() method.");
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcBookDao class setFieldsInUpdateByEntityPreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, Book book) throws DaoException {
        log.debug("Entering JdbcBookDao class, setFieldsInCreatePreparedStatement() method.");
        try {
            preparedStatement.setString(FIRST_PARAMETER_INDEX, book.getTitle());
            Author authorInstance = book.getAuthor();
            Author author = readAuthorByName(authorInstance);
            if (author == null){
                author = createAuthor(authorInstance);
            }
            preparedStatement.setInt(SECOND_PARAMETER_INDEX, author.getId());
            preparedStatement.setInt(THIRD_PARAMETER_INDEX, book.getPublishYear().getValue());
            preparedStatement.setInt(FOURTH_PARAMETER_INDEX, book.getGenre().ordinal());
            preparedStatement.setString(FIFTH_PARAMETER_INDEX, book.getDescription());
            preparedStatement.setInt(SIXTH_PARAMETER_INDEX, book.getTotalAmount());
            preparedStatement.setString(SEVENTH_PARAMETER_INDEX, book.getCover());
            log.debug("Leaving JdbcBookDao class, setFieldsInCreatePreparedStatement() method.");
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcBookDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    protected String getCreateQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String getUpdateByEntityQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String getCountNumberRowsQuery() {
        return SELECT_COUNT_ROWS_QUERY;
    }

    @Override
    protected String getReadByIdQuery() {
        return SELECT_COLUMNS_PART + SELECT_BY_ID;
    }

    @Override
    protected String getReadRangeQuery() {
        return SELECT_COLUMNS_PART + SELECT_RANGE_QUERY;
    }

    @Override
    protected String getCountNumberRowsByIdParameterQuery() {
        return SELECT_COUNT_BY_GENRE_ID;
    }

    @Override
    protected String getReadRangeByIdParameterQuery() {
        return SELECT_COLUMNS_PART + SELECT_RANGE_BY_GENRE;
    }

    @Override
    protected String getDeleteByIdQuery() {
        throw new DaoUnsupportedOperationException("Not implemented yet");
    }

    @Override
    protected String getReadByEntityQuery() {
        throw new DaoUnsupportedOperationException("Not implemented yet");
    }

    @Override
    protected String getReadAllByIdParameterQuery() {
        throw new DaoUnsupportedOperationException("Not implemented yet");
    }

    @Override
    protected PreparedStatement setFieldsInReadByEntityPreparedStatement(PreparedStatement preparedStatement, Book entity) throws DaoException {
        throw new DaoUnsupportedOperationException("Not implemented yet");
    }
}

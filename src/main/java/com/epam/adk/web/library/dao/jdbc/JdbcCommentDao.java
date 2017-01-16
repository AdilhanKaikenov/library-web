package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.CommentDao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.exception.DaoUnsupportedOperationException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.model.Comment;
import com.epam.adk.web.library.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JdbcCommentDao class created on 03.12.2016
 *
 * @author Kaikenov Adilhan
 * @see CommentDao
 **/
public class JdbcCommentDao extends JdbcDao<Comment> implements CommentDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcCommentDao.class);

    private static final String TABLE_NAME = "comment";
    private static final String SELECT_COLUMNS_PART = queryProperties.get("select.comment.columns");
    private static final String CREATE_QUERY = queryProperties.get("insert.comment");
    private static final String SELECT_ALL_BY_BOOK_ID_QUERY = queryProperties.get("select.all.comments.by.book.id");
    private static final String SELECT_COUNT_BY_BOOK_ID = queryProperties.get("select.count.comments.by.book.id");
    private static final String SELECT_RANGE_BY_ID_QUERY = queryProperties.get("select.range.comments.by.book.id");
    private static final String DELETE_QUERY = queryProperties.get("delete.comment");

    public JdbcCommentDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<Comment> createListFrom(ResultSet resultSet) throws DaoException {
        log.debug("Entering JdbcCommentDao class, createListFrom() method");
        List<Comment> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Comment comment = new Comment();
                log.debug("Creating comment getFromValue resultSet");
                comment.setId(resultSet.getInt(ID_COLUMN_NAME));
                User user = new User();
                user.setId(resultSet.getInt(USER_ID_COLUMN_NAME));
                user.setLogin(resultSet.getString(LOGIN_COLUMN_NAME));
                user.setFirstname(resultSet.getString(FIRSTNAME_COLUMN_NAME));
                user.setSurname(resultSet.getString(SURNAME_COLUMN_NAME));
                comment.setUser(user);
                Book book = new Book();
                book.setId(resultSet.getInt(BOOK_ID_COLUMN_NAME));
                comment.setBook(book);
                comment.setTime(resultSet.getTimestamp(DATE_COLUMN_NAME));
                comment.setText(resultSet.getString(TEXT_COLUMN_NAME));
                log.debug("Comment successfully created in createListFrom() method. Comment id = {}", comment.getId());
                result.add(comment);
            }
            log.debug("Leaving JdbcCommentDao class, createListFrom() method.");
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcCommentDao class createListFrom() method. I can not create List of comments getFromValue resultSet.", e);
        }
        return result;
    }

    @Override
    protected PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, Comment comment) throws DaoException {
        log.debug("Entering JdbcCommentDao class, setFieldsInCreatePreparedStatement() method.");
        try {
            preparedStatement.setInt(FIRST_PARAMETER_INDEX, comment.getUser().getId());
            preparedStatement.setInt(SECOND_PARAMETER_INDEX, comment.getBook().getId());
            preparedStatement.setTimestamp(THIRD_PARAMETER_INDEX, comment.getTime());
            preparedStatement.setString(FOURTH_PARAMETER_INDEX, comment.getText());
            log.debug("Leaving JdbcCommentDao class, setFieldsInCreatePreparedStatement() method.");
        } catch (SQLException e) {
            throw new DaoException("Error: JdbcCommentDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement.", e);
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
    protected String getReadRangeByIdParameterQuery() {
        return SELECT_COLUMNS_PART + SELECT_RANGE_BY_ID_QUERY;
    }

    @Override
    protected String getReadAllByIdParameterQuery() {
        return SELECT_COLUMNS_PART + SELECT_ALL_BY_BOOK_ID_QUERY;
    }

    @Override
    protected String getCountNumberRowsByIdParameterQuery() {
        return SELECT_COUNT_BY_BOOK_ID;
    }

    protected String getReadByIdQuery() {
        throw new DaoUnsupportedOperationException("Not implemented yet");
    }

    @Override
    protected String getReadRangeQuery() {
        throw new DaoUnsupportedOperationException("Not implemented yet");
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
    protected String getUpdateByEntityQuery() {
        throw new DaoUnsupportedOperationException("Not implemented yet");
    }

    @Override
    protected PreparedStatement setFieldsInUpdateByEntityPreparedStatement(PreparedStatement preparedStatement, Comment entity) {
        throw new DaoUnsupportedOperationException("Not implemented yet");
    }

    @Override
    protected PreparedStatement setFieldsInReadByEntityPreparedStatement(PreparedStatement preparedStatement, Comment entity) throws DaoException {
        throw new DaoUnsupportedOperationException("Not implemented yet");
    }
}

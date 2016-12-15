package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.CommentDao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.Comment;
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
    private static final String CREATE_QUERY = getQueriesProp().get("insert.comment");
    private static final String SELECT_ALL_BY_BOOK_ID_QUERY = getQueriesProp().get("select.all.by.book.id");
    private static final String SELECT_COUNT_BY_BOOK_ID = getQueriesProp().get("select.count.by.book.id");
    private static final String SELECT_RANGE_BY_ID_QUERY = getQueriesProp().get("select.range.comment.by.book.id");

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
                log.debug("Creating comment from resultSet");
                comment.setId(resultSet.getInt("ID"));
                comment.setUserID(resultSet.getInt("USER_ID"));
                comment.setUserLogin(resultSet.getString("LOGIN"));
                comment.setUserFirstname(resultSet.getString("FIRSTNAME"));
                comment.setUserSurname(resultSet.getString("SURNAME"));
                comment.setBookID(resultSet.getInt("BOOK_ID"));
                comment.setTime(resultSet.getTimestamp("DATE"));
                comment.setText(resultSet.getString("TEXT"));
                log.debug("Comment successfully created in createFrom() method. Comment id = {}", comment.getId());
                result.add(comment);
            }
            log.debug("Leaving JdbcCommentDao class, createListFrom() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcCommentDao class createListFrom() method. I can not create List of comments from resultSet. {}", e);
            throw new DaoException("Error: JdbcCommentDao class createListFrom() method. I can not create List of comments from resultSet.", e);
        }
        return result;
    }

    @Override
    protected PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, Comment comment) throws DaoException {
        log.debug("Entering JdbcCommentDao class, setFieldsInCreatePreparedStatement() method.");
        try {
            log.debug("Set user ID: {}", comment.getUserID());
            preparedStatement.setInt(1, comment.getUserID());
            log.debug("Set book ID: {}", comment.getBookID());
            preparedStatement.setInt(2, comment.getBookID());
            log.debug("Set time: {}", comment.getTime());
            preparedStatement.setTimestamp(3, comment.getTime());
            log.debug("Set text:length = {}", comment.getText().length());
            preparedStatement.setString(4, comment.getText());
            log.debug("Leaving JdbcCommentDao class, setFieldsInCreatePreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcCommentDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcCommentDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
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
    protected String getReadAllByIdParameterQuery() {
        return SELECT_ALL_BY_BOOK_ID_QUERY;
    }

    @Override
    protected String getCountNumberRowsByIdParameterQuery() {
        return SELECT_COUNT_BY_BOOK_ID;
    }

    @Override
    protected String getReadRangeByIdParameterQuery() {
        return SELECT_RANGE_BY_ID_QUERY;
    }

    @Override
    protected String getUpdateByEntityQuery() {
        return null;
    }

    @Override
    protected String getReadByEntityQuery() {
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
    protected PreparedStatement setFieldsInReadByEntityPreparedStatement(PreparedStatement preparedStatement, Comment entity) throws DaoException {
        return null;
    }

    @Override
    protected PreparedStatement setFieldsInUpdateByEntityPreparedStatement(PreparedStatement preparedStatement, Comment entity) {
        return null;
    }

}

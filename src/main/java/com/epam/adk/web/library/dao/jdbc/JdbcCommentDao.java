package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.CommentDao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * JdbcCommentDao class created on 03.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class JdbcCommentDao extends JdbcDao<Comment> implements CommentDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcCommentDao.class);
    private static final String CREATE_QUERY = "INSERT INTO PUBLIC.COMMENT(USER_ID, BOOK_ID, DATE, TEXT) VALUES (?, ?, ?, ?)";

    public JdbcCommentDao(Connection connection) {
        super(connection);
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
    protected Comment createFrom(ResultSet resultSet) throws DaoException {
        return null;
    }

    @Override
    protected String getTableName() {
        return null;
    }

    @Override
    protected String getCreateQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String getReadByEntityQuery() {
        return null;
    }

    @Override
    protected PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, Comment entity) throws DaoException {
        log.debug("Entering JdbcCommentDao class, setFieldsInCreatePreparedStatement() method.");
        try {
            preparedStatement.setInt(1, entity.getUserID());
            preparedStatement.setInt(2, entity.getBookID());
            long time = entity.getDate().getTime();
            Timestamp timestamp = new Timestamp(time);
            preparedStatement.setTimestamp(3, timestamp);
            preparedStatement.setString(4, entity.getText());
            log.debug("Leaving JdbcCommentDao class, setFieldsInCreatePreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcCommentDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcCommentDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement setFieldsInReadByEntityPreparedStatement(PreparedStatement preparedStatement, Comment entity) throws DaoException {
        return null;
    }
}

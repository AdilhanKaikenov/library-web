package com.epam.adk.web.library.dao.jdbc;

import com.epam.adk.web.library.dao.CommentDao;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JdbcCommentDao class created on 03.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class JdbcCommentDao extends JdbcDao<Comment> implements CommentDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcCommentDao.class);
    private static final String CREATE_QUERY = "INSERT INTO PUBLIC.COMMENT(USER_ID, BOOK_ID, DATE, TEXT) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_BY_BOOK_ID_QUERY = "SELECT COMMENT.*, USER.LOGIN, USER.FIRSTNAME, USER.SURNAME FROM COMMENT, USER WHERE COMMENT.USER_ID = USER.ID AND COMMENT.BOOK_ID = ?";

    public JdbcCommentDao(Connection connection) {
        super(connection);
    }

    @Override
    protected PreparedStatement setFieldInReadAllByIdParameterPreparedStatement(PreparedStatement preparedStatement, int id) throws DaoException {
        log.debug("Entering JdbcCommentDao class, setFieldInReadAllByIdParameterPreparedStatement() method. Book id = {}", id);
        try {
            preparedStatement.setInt(1, id);
            log.debug("Leaving JdbcCommentDao class, setFieldInReadAllByIdParameterPreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcCommentDao class setFieldInReadAllByIdParameterPreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcCommentDao class setFieldInReadAllByIdParameterPreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected PreparedStatement setFieldsInCreatePreparedStatement(PreparedStatement preparedStatement, Comment entity) throws DaoException {
        log.debug("Entering JdbcCommentDao class, setFieldsInCreatePreparedStatement() method.");
        try {
            preparedStatement.setInt(1, entity.getUserID());
            preparedStatement.setInt(2, entity.getBookID());
            preparedStatement.setTimestamp(3, entity.getTime());
            preparedStatement.setString(4, entity.getText());
            log.debug("Leaving JdbcCommentDao class, setFieldsInCreatePreparedStatement() method.");
        } catch (SQLException e) {
            log.error("Error: JdbcCommentDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement. {}", e);
            throw new DaoException("Error: JdbcCommentDao class setFieldsInCreatePreparedStatement() method. I can not set fields into statement.", e);
        }
        return preparedStatement;
    }

    @Override
    protected List<Comment> createListFrom(ResultSet resultSet) throws DaoException {
        log.debug("Entering JdbcCommentDao class, createListFrom() method");
        List<Comment> result = new ArrayList<>();
        try {
            while (resultSet.next()){
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
        } catch (SQLException e) {
            log.error("Error: JdbcCommentDao class createListFrom() method. I can not create List of comments from resultSet. {}", e);
            throw new DaoException("Error: JdbcCommentDao class createListFrom() method. I can not create List of comments from resultSet.", e);
        }
        log.debug("Leaving JdbcCommentDao class, createListFrom() method.");
        return result;
    }

    @Override
    protected String getReadAllByIdParameterQuery() {
        return SELECT_ALL_BY_BOOK_ID_QUERY;
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
    protected String getReadByIdQuery() {
        return null;
    }

    @Override
    protected String getReadAllQuery() {
        return null;
    }

    @Override
    protected String getTableName() {
        return null;
    }

    @Override
    protected PreparedStatement setFieldsInReadByEntityPreparedStatement(PreparedStatement preparedStatement, Comment entity) throws DaoException {
        return null;
    }

}

package com.epam.adk.web.library.service;

import com.epam.adk.web.library.dao.CommentDao;
import com.epam.adk.web.library.dao.DaoFactory;
import com.epam.adk.web.library.dao.jdbc.JdbcDaoFactory;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * CommentService class created on 03.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class CommentService {

    private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    public Comment writeComment(Comment comment) throws ServiceException {
        log.debug("Entering CommentService class writeComment() method.");
        Comment addedComment;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                CommentDao commentDao = jdbcDaoFactory.commentDao();
                addedComment = commentDao.create(comment);
                jdbcDaoFactory.endTransaction();
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: CommentService class, writeComment() method. TRANSACTION error :", e);
            }
        } catch (DaoException | SQLException e) {
            throw new ServiceException("Error: CommentService class, writeComment() method.", e);
        }
        log.debug("Leaving CommentService class writeComment() method.");
        return addedComment;
    }

    public List<Comment> getAllBookComments(int id) throws ServiceException {
        log.debug("Entering CommentService class getAllBookComments() method. Book id = {}", id);
        List<Comment> result;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)){
            try {
                jdbcDaoFactory.beginTransaction();
                CommentDao commentDao = jdbcDaoFactory.commentDao();
                result = commentDao.readAllByIdParameter(id);
                jdbcDaoFactory.endTransaction();
            } catch (SQLException e){
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: CommentService class, getAllBookComments() method. TRANSACTION error :", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: CommentService class, getAllBookComments() method.", e);
        }
        log.debug("Leaving CommentService class getAllBookComments() method. Amount od book comment = {}", result.size());
        return result;
    }

}

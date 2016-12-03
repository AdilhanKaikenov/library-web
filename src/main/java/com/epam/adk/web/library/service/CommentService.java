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

}

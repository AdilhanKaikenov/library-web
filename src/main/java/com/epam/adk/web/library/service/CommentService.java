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
            CommentDao commentDao = jdbcDaoFactory.getCommentDao();
            addedComment = commentDao.create(comment);
            log.debug("Leaving CommentService class writeComment() method.");
        } catch (DaoException | SQLException e) {
            throw new ServiceException("Error: CommentService class, writeComment() method.", e);
        }
        return addedComment;
    }

    /**
     * This method returns all the comments related the book in parts according to page number.
     *
     * @param pageNumber number of pages
     * @param pageSize the number of comments per page
     * @return List of comments
     * @throws ServiceException
     */
    public List<Comment> getPaginatedComments(int bookID, int pageNumber, int pageSize) throws ServiceException {
        log.debug("Entering CommentService class getPaginatedComments() method. Book id = {}", bookID);
        List<Comment> result;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            CommentDao commentDao = jdbcDaoFactory.getCommentDao();
            int offset = pageSize * pageNumber - pageSize;
            result = commentDao.readRangeByIdParameter(bookID, offset, pageSize);
            log.debug("Leaving CommentService class getPaginatedComments() method. Amount of books comment = {}", result.size());
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: CommentService class, getPaginatedComments() method.", e);
        }
        return result;
    }

    public int getCommentsNumberByBookId(int id) throws ServiceException {
        log.debug("Entering CommentService class getCommentsNumberByBookId() method.");
        int commentsNumber;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            CommentDao commentDao = jdbcDaoFactory.getCommentDao();
            commentsNumber = commentDao.getNumberRowsByIdParameter(id);
            log.debug("Leaving CommentService class getCommentsNumberByBookId() method.");
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: CommentService class, getCommentsNumberByBookId() method.", e);
        }
        return commentsNumber;
    }
}

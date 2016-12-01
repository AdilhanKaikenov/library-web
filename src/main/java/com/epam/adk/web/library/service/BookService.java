package com.epam.adk.web.library.service;

import com.epam.adk.web.library.dao.BookDao;
import com.epam.adk.web.library.dao.DaoFactory;
import com.epam.adk.web.library.dao.jdbc.JdbcDaoFactory;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * BookService class created on 01.12.2016
 *
 * @author Kaikenov Adilhan
 */
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    public List<Book> getAllBooks() throws ServiceException {
        log.debug("Entering BookService class getAllBooks() method.");
        List<Book> result;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                BookDao bookDao = jdbcDaoFactory.bookDao();
                result = bookDao.readAll();
                log.debug("BookService class, getAllBooks() method: result size = {}", result.size());
                jdbcDaoFactory.endTransaction();
            } catch (SQLException e){
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: BookService class, getAllBooks() method. TRANSACTION error :", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: BookService class, getAllBooks() method.", e);
        }
        log.debug("Leaving BookService class getAllBooks() method.");
        return result;
    }

}

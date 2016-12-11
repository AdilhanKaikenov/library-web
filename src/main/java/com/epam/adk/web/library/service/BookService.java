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

    public Book add(Book book) throws ServiceException {
        log.debug("Entering BookService class add() method, book title = {}", book.getTitle());
        Book addedBook;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                BookDao bookDao = jdbcDaoFactory.bookDao();
                addedBook = bookDao.create(book);
                jdbcDaoFactory.endTransaction();
                log.debug("Leaving BookService class add() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: BookService class, add() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: BookService class, add() method.", e);
        }
        return addedBook;
    }

    public List<Book> getPaginated(int pageNumber, int pageSize) throws ServiceException {
        log.debug("Entering BookService class getPaginated() method.");
        List<Book> result;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                BookDao bookDao = jdbcDaoFactory.bookDao();
                int offset = pageSize * pageNumber - pageSize;
                result = bookDao.readRange(offset, pageSize);
                log.debug("BookService class, getPaginated() method: result size = {}", result.size());
                jdbcDaoFactory.endTransaction();
                log.debug("Leaving BookService class getPaginated() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: BookService class, getPaginated() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: BookService class, getPaginated() method.", e);
        }
        return result;
    }

    public int getBooksNumber() throws ServiceException {
        log.debug("Entering BookService class getBooksNumber() method.");
        int booksNumber;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                BookDao bookDao = jdbcDaoFactory.bookDao();
                booksNumber = bookDao.getNumberRows();
                jdbcDaoFactory.endTransaction();
                log.debug("Leaving BookService class getBooksNumber() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: BookService class, getBooksNumber() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: BookService class, getBooksNumber() method.", e);
        }
        return booksNumber;
    }

    public int getBooksNumberByGenre(int id) throws ServiceException {
        log.debug("Entering BookService class getBooksNumberByGenre() method.");
        int booksNumber;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                BookDao bookDao = jdbcDaoFactory.bookDao();
                booksNumber = bookDao.getNumberRowsByIdParameter(id);
                jdbcDaoFactory.endTransaction();
                log.debug("Leaving BookService class getBooksNumberByGenre() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: BookService class, getBooksNumberByGenre() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: BookService class, getBooksNumberByGenre() method.", e);
        }
        return booksNumber;
    }

    public Book getBookById(int id) throws ServiceException {
        log.debug("Entering BookService class getBookById() method. Id = {}", id);
        Book book;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                BookDao bookDao = jdbcDaoFactory.bookDao();
                book = bookDao.read(id);
                jdbcDaoFactory.endTransaction();
                log.debug("Leaving BookService class getBookById() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: BookService class, getBookById() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: BookService class, getBookById() method.", e);
        }
        return book;
    }

    public List<Book> getPaginatedByGenre(int genreId, int pageNumber, int pageSize) throws ServiceException {
        log.debug("Entering BookService class getPaginatedByGenre() method. Genre Id = {}", genreId);
        List<Book> result;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                BookDao bookDao = jdbcDaoFactory.bookDao();
                int offset = pageSize * pageNumber - pageSize;
                result = bookDao.readRangeByIdParameter(genreId, offset, pageSize);
                jdbcDaoFactory.endTransaction();
                log.debug("Leaving BookService class getPaginatedByGenre() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: BookService class, getPaginatedByGenre() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: BookService class, getPaginatedByGenre() method.", e);
        }
        return result;
    }

    public void updateBook(Book book) throws ServiceException {
        log.debug("Entering BookService class deleteBook() method. Book Id = {}", book.getId());
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                BookDao bookDao = jdbcDaoFactory.bookDao();
                bookDao.update(book);
                jdbcDaoFactory.endTransaction();
                log.debug("Leaving BookService class deleteBook() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: BookService class, deleteBook() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: BookService class, deleteBook() method.", e);
        }
    }

    public List<Book> findByTitle(String title) throws ServiceException {
        log.debug("Entering BookService class findByTitle() method.");
        List<Book> result;
        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)) {
            try {
                jdbcDaoFactory.beginTransaction();
                BookDao bookDao = jdbcDaoFactory.bookDao();
                result = bookDao.findByTitle(title);
                jdbcDaoFactory.endTransaction();
                log.debug("Leaving BookService class findByTitle() method.");
            } catch (SQLException e) {
                jdbcDaoFactory.rollbackTransaction();
                throw new ServiceException("Error: BookService class, findByTitle() method. TRANSACTION error:", e);
            }
        } catch (SQLException | DaoException e) {
            throw new ServiceException("Error: BookService class, findByTitle() method.", e);
        }
        return result;
    }
}

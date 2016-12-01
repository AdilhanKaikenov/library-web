package com.epam.adk.web.library.action;

import com.epam.adk.web.library.dao.BookDao;
import com.epam.adk.web.library.dao.DaoFactory;
import com.epam.adk.web.library.dao.jdbc.JdbcDaoFactory;
import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.DaoException;
import com.epam.adk.web.library.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 * TODO: add comment
 *
 * @author Kaikenov Adilhan
 */
public class ShowWelcomeAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ShowWelcomeAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {

        try (JdbcDaoFactory jdbcDaoFactory = DaoFactory.newInstance(JdbcDaoFactory.class)){
            try {
                jdbcDaoFactory.beginTransaction();
                BookDao bookDao = jdbcDaoFactory.bookDao();
                List<Book> books = bookDao.readAll();
                request.getSession().setAttribute("books", books);
                jdbcDaoFactory.endTransaction();
            } catch (SQLException e){
                jdbcDaoFactory.rollbackTransaction();
            }
        } catch (SQLException | DaoException e) {
            e.printStackTrace();
        }

        return "welcome";
    }
}

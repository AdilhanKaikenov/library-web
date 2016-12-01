package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ShowWelcomeAction class created on 01.12.2016
 *
 * @author Kaikenov Adilhan
 */
public class ShowWelcomeAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ShowWelcomeAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The show welcome page action started execute.");
        BookService bookService = new BookService();

        try {
            List<Book> books = bookService.getAllBooks();
            request.setAttribute("books", books);
        } catch (ServiceException e) {
            throw new ActionException("Error: ShowWelcomeAction class, execute() method.", e);
        }
        return "welcome";
    }
}

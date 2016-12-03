package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BookAboutAction class created on 03.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class BookAboutAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(BookAboutAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The book about action started execute.");

        int id = Integer.parseInt(request.getParameter("id"));

        BookService bookService = new BookService();

        try {
            Book book = bookService.getBookById(id);

            request.setAttribute("book", book);
        } catch (ServiceException e) {
            throw new ActionException("Error: BookAboutAction class, execute() method.", e);
        }

        return "book";
    }
}

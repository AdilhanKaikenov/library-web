package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.service.BookService;
import com.epam.adk.web.library.util.Pagination;
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

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int LINE_PER_PAGE_NUMBER = 6;
    private static final String WELCOME_PAGE = "welcome";
    private static final String PAGE_PARAMETER = "page";
    private static final String BOOKS_REQUEST_ATTRIBUTE = "books";
    private static final String PAGES_NUMBER_REQUEST_ATTRIBUTE = "pagesNumber";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The ShowWelcomeAction started execute.");
        BookService bookService = new BookService();

            int page = DEFAULT_PAGE_NUMBER;

            String pageParameter = request.getParameter(PAGE_PARAMETER);

            if (pageParameter != null) {
                page = Integer.parseInt(pageParameter);
                log.debug("Page #{}", page);
            }

        try {
            int booksNumber = bookService.getBooksNumber();
            log.debug("Total books number = {}", booksNumber);
            Pagination pagination = new Pagination();
            int pagesNumber = pagination.getPagesNumber(booksNumber, LINE_PER_PAGE_NUMBER);
            log.debug("Total pages number = {}", pagesNumber);
            List<Book> books = bookService.getPaginated(page, LINE_PER_PAGE_NUMBER);

            request.setAttribute(PAGES_NUMBER_REQUEST_ATTRIBUTE, pagesNumber);
            request.setAttribute(BOOKS_REQUEST_ATTRIBUTE, books);
        } catch (ServiceException e) {
            throw new ActionException("Error: ShowWelcomeAction class, execute() method.", e);
        }
        return WELCOME_PAGE;
    }
}

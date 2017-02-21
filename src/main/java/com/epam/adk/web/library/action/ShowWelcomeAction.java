package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.service.BookService;
import com.epam.adk.web.library.util.Pagination;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.epam.adk.web.library.util.ConstantsHolder.WELCOME_PAGE;

/**
 * ShowWelcomeAction class created on 01.12.2016
 *
 * @author Kaikenov Adilhan
 */
public class ShowWelcomeAction implements Action {

    private static final int LINE_PER_PAGE_NUMBER = 6;
    private static final String BOOKS_REQUEST_ATTRIBUTE = "books";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        BookService bookService = new BookService();

        try {
            int booksNumber = bookService.getBooksNumber();

            Pagination pagination = new Pagination();
            int pageNumber = pagination.getPageNumber(request, booksNumber, LINE_PER_PAGE_NUMBER);

            List<Book> books = bookService.getPaginated(pageNumber, LINE_PER_PAGE_NUMBER);

            request.setAttribute(BOOKS_REQUEST_ATTRIBUTE, books);
        } catch (ServiceException e) {
            throw new ActionException("Error: ShowWelcomeAction class, execute() method.", e);
        }
        return WELCOME_PAGE;
    }
}

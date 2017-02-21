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
 * BookSearchAction class created on 09.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class BookSearchAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(BookSearchAction.class);

    private static final String DATA_FOR_SEARCH_PARAMETER = "dataForSearch";
    private static final String BOOK_SEARCH_RESULT_PAGE_NAME = "book-search-result";
    private static final String FOUND_BOOKS_REQUEST_ATTRIBUTE = "foundBooks";
    private static final String DATA_FOR_SEARCH_REQUEST_ATTRIBUTE = "dataForSearch";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The BookSearchAction started execute.");

        String dataForSearch = request.getParameter(DATA_FOR_SEARCH_PARAMETER);
        log.debug("Data for searching = {}", dataForSearch);
        log.debug("Request parameters valid.");

        BookService bookService = new BookService();

        try {
            List<Book> foundBooks = bookService.findByTitle(dataForSearch);
            log.debug("Found book amount = {}", foundBooks.size());
            request.setAttribute(DATA_FOR_SEARCH_REQUEST_ATTRIBUTE, dataForSearch);
            request.setAttribute(FOUND_BOOKS_REQUEST_ATTRIBUTE, foundBooks);
        } catch (ServiceException e) {
            throw new ActionException("Error: BookSearchAction class, Can not find books:", e);
        }
        return BOOK_SEARCH_RESULT_PAGE_NAME;
    }
}

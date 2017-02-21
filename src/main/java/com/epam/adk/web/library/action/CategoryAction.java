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

import static com.epam.adk.web.library.util.ConstantsHolder.GENRE_ID_REQUEST_ATTRIBUTE;

/**
 * CategoryAction class created on 03.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class CategoryAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(CategoryAction.class);

    private static final int LINE_PER_PAGE_NUMBER = 2;
    private static final String GENRE_PARAMETER = "genre";
    private static final String CATEGORY_PAGE = "category";
    private static final String GENRE_BOOKS_REQUEST_ATTRIBUTE = "genreBooks";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The CategoryAction started execute.");

        int genreID = Integer.parseInt(request.getParameter(GENRE_PARAMETER));
        log.debug("Genre ID = {}", genreID);
        log.debug("Request parameters valid.");

        BookService bookService = new BookService();

        try {
            int booksNumber = bookService.getBooksNumberByGenre(genreID);

            Pagination pagination = new Pagination();
            int pageNumber = pagination.getPageNumber(request, booksNumber, LINE_PER_PAGE_NUMBER);

            List<Book> genreBooks = bookService.getPaginatedByGenre(genreID, pageNumber, LINE_PER_PAGE_NUMBER);

            request.setAttribute(GENRE_ID_REQUEST_ATTRIBUTE, genreID);
            request.setAttribute(GENRE_BOOKS_REQUEST_ATTRIBUTE, genreBooks);
        } catch (ServiceException e) {
            throw new ActionException("Error: CategoryAction class, execute() method.", e);
        }
        return CATEGORY_PAGE;
    }
}

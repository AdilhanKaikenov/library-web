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
 * CategoryAction class created on 03.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class CategoryAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(CategoryAction.class);
    private static final String GENRE_PARAMETER = "genre";
    private static final String PAGE_PARAMETER = "page";
    private static final int LINE_PER_PAGE_NUMBER = 4;
    private static final int DEFAULT_PAGE_NUMBER = 1;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The CategoryAction started execute.");

        int genreID = Integer.parseInt(request.getParameter(GENRE_PARAMETER));

        BookService bookService = new BookService();

        int page = DEFAULT_PAGE_NUMBER;
        String pageParameter = request.getParameter(PAGE_PARAMETER);
        if (pageParameter != null) {
            page = Integer.parseInt(pageParameter);
            log.debug("CategoryAction: page #{}", page);
        }

        try {
            int booksNumber = bookService.getBooksNumberByGenre(genreID);
            log.debug("CategoryAction: total books number = {}", booksNumber);
            Pagination pagination = new Pagination();
            int pagesNumber = pagination.getPagesNumber(booksNumber, LINE_PER_PAGE_NUMBER);
            log.debug("CategoryAction: total pages number = {}", pagesNumber);
            List<Book> genreBooks = bookService.getPaginatedByGenre(genreID, page, LINE_PER_PAGE_NUMBER);

            request.setAttribute("pagesNumber", pagesNumber);
            request.setAttribute("genreBooks", genreBooks);
            request.setAttribute("genreID", genreID);
        } catch (ServiceException e) {
            throw new ActionException("Error: CategoryAction class, execute() method.", e);
        }
        return "category";
    }
}

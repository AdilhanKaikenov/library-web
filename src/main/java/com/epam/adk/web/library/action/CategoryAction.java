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
 * CategoryAction class created on 03.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class CategoryAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(CategoryAction.class);
    private static final String GENRE_PARAMETER = "genre";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The category action started execute.");

        int genreID = Integer.parseInt(request.getParameter(GENRE_PARAMETER));

        BookService bookService = new BookService();

        try {
            List<Book> genreBooks = bookService.getAllByGenre(genreID);
            request.setAttribute("genreBooks", genreBooks);
        } catch (ServiceException e) {
            throw new ActionException("Error: CategoryAction class, execute() method.", e);
        }

        return "category";
    }
}

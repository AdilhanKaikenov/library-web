package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.model.Comment;
import com.epam.adk.web.library.service.BookService;
import com.epam.adk.web.library.service.CommentService;
import com.epam.adk.web.library.util.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BookAboutAction class created on 03.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class BookAboutAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(BookAboutAction.class);
    private static final String BOOK_ID_PARAMETER = "id";
    private static final String PAGE_PARAMETER = "page";
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int LINE_PER_PAGE_NUMBER = 4;
    private static final String ABOUT_BOOK_PAGE = "about-book";
    private static final String BOOK_REQUEST_ATTRIBUTE = "book";
    private static final String PAGES_NUMBER_REQUEST_ATTRIBUTE = "pagesNumber";
    private static final String BOOK_COMMENTS_REQUEST_ATTRIBUTE = "bookComments";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The BookAboutAction started execute.");

        int bookId = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));

        BookService bookService = new BookService();
        CommentService commentService = new CommentService();

        int page = DEFAULT_PAGE_NUMBER;
        String pageParameter = request.getParameter(PAGE_PARAMETER);

        if (pageParameter != null) {
            page = Integer.parseInt(pageParameter);
            log.debug("BookAboutAction: page #{}", page);
        }

        try {
            Book book = bookService.getBookById(bookId);
            int commentsNumber = commentService.getCommentsNumberByBookId(bookId);
            log.debug("BookAboutAction: total comments number = {}", commentsNumber);
            Pagination pagination = new Pagination();
            int pagesNumber = pagination.getPagesNumber(commentsNumber, LINE_PER_PAGE_NUMBER);
            log.debug("BookAboutAction: total pages number = {}", pagesNumber);
            List<Comment> bookComments = commentService.getPaginatedComments(bookId, page, LINE_PER_PAGE_NUMBER);

            if (bookComments.size() != 0) {
                request.setAttribute(BOOK_COMMENTS_REQUEST_ATTRIBUTE, bookComments);
            }
            request.setAttribute(BOOK_REQUEST_ATTRIBUTE, book);
            request.setAttribute(PAGES_NUMBER_REQUEST_ATTRIBUTE, pagesNumber);
        } catch (ServiceException e) {
            throw new ActionException("Error: BookAboutAction class, execute() method. Can not give info about book:", e);
        }
        return ABOUT_BOOK_PAGE;
    }
}

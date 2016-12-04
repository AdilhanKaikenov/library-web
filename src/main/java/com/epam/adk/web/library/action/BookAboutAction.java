package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.model.Comment;
import com.epam.adk.web.library.service.BookService;
import com.epam.adk.web.library.service.CommentService;
import com.epam.adk.web.library.service.UserService;
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

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The book about action started execute.");

        int id = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));

        BookService bookService = new BookService();
        CommentService commentService = new CommentService();

        try {
            Book book = bookService.getBookById(id);
            request.setAttribute("book", book);

            List<Comment> bookComments = commentService.getAllBookComments(id);

            if (bookComments.size() != 0) {
                request.setAttribute("bookComments", bookComments);
            }


        } catch (ServiceException e) {
            throw new ActionException("Error: BookAboutAction class, execute() method. Can not give info about book:", e);
        }

        return "about-book";
    }
}

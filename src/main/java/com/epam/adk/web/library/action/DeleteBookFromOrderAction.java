package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * DeleteBookFromOrderAction class created on 16.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class DeleteBookFromOrderAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(DeleteBookFromOrderAction.class);

    private static final String USER_PARAMETER = "user";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String MY_ORDER_PAGE_NAME = "my-order";
    private static final String BOOK_ID_PARAMETER = "bookID";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The DeleteBookFromOrderAction started execute.");

        boolean isCreated = false;
        HttpSession session = request.getSession(isCreated);

        User user = ((User) session.getAttribute(USER_PARAMETER));
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));
        log.debug("Book ID = {}", bookID);

        BookService bookService = new BookService();

        try {
            Book book = bookService.getBookById(bookID);

            List<Book> readingRoomBooks = user.getReadingRoomBooks();
            List<Book> subscriptionBooks = user.getSubscriptionBooks();

            if (!readingRoomBooks.remove(book)) {
                subscriptionBooks.remove(book);
            }

        } catch (ServiceException e) {
            throw new ActionException("Error: DeleteBookFromOrderAction class, execute() method.", e);
        }

        return REDIRECT_PREFIX + MY_ORDER_PAGE_NAME;
    }
}

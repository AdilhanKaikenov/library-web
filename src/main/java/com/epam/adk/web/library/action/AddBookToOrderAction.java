package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.model.OrderBook;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.model.enums.OrderType;
import com.epam.adk.web.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * AddBookToOrderAction class created on 16.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class AddBookToOrderAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(AddBookToOrderAction.class);

    private static final String USER_PARAMETER = "user";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String BOOK_ID_PARAMETER = "bookID";
    private static final String ORDER_TYPE_PARAMETER = "order_type";
    private static final String ABOUT_BOOK_ID_PAGE_NAME = "about-book&id=";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The AddBookToOrderAction started execute.");

        boolean isCreated = false;
        HttpSession session = request.getSession(isCreated);

        User user = ((User) session.getAttribute(USER_PARAMETER));
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));
        log.debug("Book ID = {}", bookID);
        OrderType orderType = OrderType.from(request.getParameter(ORDER_TYPE_PARAMETER));
        log.debug("Order type = {}", orderType.getValue());

        BookService bookService = new BookService();

        request.setAttribute(BOOK_ID_PARAMETER, bookID);

        try {
            Book book = bookService.getBookById(bookID);

            OrderBook orderBook = new OrderBook();
            orderBook.setUser(user);
            orderBook.setBook(book);

            switch (orderType){
                case READING_ROOM:
                    user.addReadingRoomBook(book);
                    break;
                case SUBSCRIPTION:
                    user.addSubscriptionBook(book);
                    break;
            }
        } catch (ServiceException e) {
            throw new ActionException("Error: AddBookToOrderAction class, execute() method.", e);
        }
        return REDIRECT_PREFIX + ABOUT_BOOK_ID_PAGE_NAME + bookID;
    }
}

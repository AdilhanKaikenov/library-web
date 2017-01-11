package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.epam.adk.web.library.util.ConstantsHolder.MY_ORDER_PAGE_NAME;
import static com.epam.adk.web.library.util.ConstantsHolder.USER_PARAMETER;

/**
 * ShowMyOrderAction class created on 16.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowMyOrderAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ShowMyOrderAction.class);

    private static final String SUBSCRIPTION_BOOKS_REQUEST_ATTRIBUTE = "subscriptionBooks";
    private static final String READING_ROOM_BOOKS_REQUEST_ATTRIBUTE = "readingRoomBooks";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The ShowMyOrderAction started execute.");

        boolean isCreated = false;
        HttpSession session = request.getSession(isCreated);

        User user = ((User) session.getAttribute(USER_PARAMETER));

        List<Book> subscriptionBooks = user.getSubscriptionBooks();
        if (!subscriptionBooks.isEmpty()) {
            request.setAttribute(SUBSCRIPTION_BOOKS_REQUEST_ATTRIBUTE, subscriptionBooks);
        }
        List<Book> readingRoomBooks = user.getReadingRoomBooks();
        if (!readingRoomBooks.isEmpty()) {
            request.setAttribute(READING_ROOM_BOOKS_REQUEST_ATTRIBUTE, readingRoomBooks);
        }

        return MY_ORDER_PAGE_NAME;
    }
}

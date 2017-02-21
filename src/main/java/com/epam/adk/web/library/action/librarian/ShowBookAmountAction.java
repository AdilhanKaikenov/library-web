package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.service.BookService;
import com.epam.adk.web.library.service.OrdersBooksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.adk.web.library.util.ConstantsHolder.BOOK_ID_PARAMETER;

/**
 * ShowBookAmountAction class created on 09.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowBookAmountAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ShowBookAmountAction.class);

    private static final String BOOK_REQUEST_ATTRIBUTE = "book";
    private static final String EDIT_BOOK_AMOUNT_PAGE_NAME = "edit-book-amount";
    private static final String ORDERED_BOOK_NUMBER_REQUEST_ATTRIBUTE = "orderedBookNumber";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The ShowBookAmountAction started execute.");

        int bookID = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));
        log.debug("Book ID = {}", bookID);
        log.debug("Request parameters valid.");

        BookService bookService = new BookService();
        OrdersBooksService ordersBooksService = new OrdersBooksService();

        try {
            Book book = bookService.getBookById(bookID);

            int orderedBookNumber = ordersBooksService.getOrdersNumberByBookID(bookID);

            request.setAttribute(ORDERED_BOOK_NUMBER_REQUEST_ATTRIBUTE, orderedBookNumber);
            request.setAttribute(BOOK_REQUEST_ATTRIBUTE, book);
        } catch (ServiceException e) {
            throw new ActionException("Error: ShowBookAmountAction class. ", e);

        }
        return EDIT_BOOK_AMOUNT_PAGE_NAME;
    }
}

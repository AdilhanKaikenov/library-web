package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.service.BookService;
import com.epam.adk.web.library.service.OrderBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ShowBookAmountAction class created on 09.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowBookAmountAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ShowBookAmountAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The ShowBookAmountAction started execute.");

        int bookID = Integer.parseInt(request.getParameter("bookID"));

        BookService bookService = new BookService();
        OrderBookService orderBookService = new OrderBookService();

        try {
            Book book = bookService.getBookById(bookID);

            int orderedBookNumber = orderBookService.getOrdersNumberByBookID(bookID);

            request.setAttribute("orderedBookNumber", orderedBookNumber);
            request.setAttribute("book", book);
        } catch (ServiceException e) {
            throw new ActionException("Error: ShowBookAmountAction class. ", e);
        }
        return "edit-book-amount";
    }
}

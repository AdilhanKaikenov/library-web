package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * EditBookAmountAction class created on 09.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class EditBookAmountAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(EditBookAmountAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The EditBookAmountAction started execute.");

        int bookID = Integer.parseInt(request.getParameter("bookID"));
        System.out.println(bookID);
        int bookAmount = Integer.parseInt(request.getParameter("bookAmount"));
        System.out.println(bookAmount);

        BookService bookService = new BookService();

        try {
            Book book = bookService.getBookById(bookID);
            book.setTotalAmount(bookAmount);

            bookService.updateBook(book);

        } catch (ServiceException e) {
            throw new ActionException("Error: EditBookAmountAction class. ", e);
        }
        return "redirect:book-amount&bookID="+bookID;
    }

}

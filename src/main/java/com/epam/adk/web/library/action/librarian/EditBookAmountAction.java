package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.action.Action;
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

    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String BOOK_ID_PARAMETER = "bookID";
    private static final String BOOK_AMOUNT_PARAMETER = "bookAmount";
    private static final String BOOK_AMOUNT_BOOK_ID_PAGE_NAME = "book-amount&bookID=";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The EditBookAmountAction started execute.");

        int bookID = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));
        int bookAmount = Integer.parseInt(request.getParameter(BOOK_AMOUNT_PARAMETER));

        BookService bookService = new BookService();

        try {
            Book book = bookService.getBookById(bookID);
            book.setTotalAmount(bookAmount);

            bookService.updateBook(book);
        } catch (ServiceException e) {
            throw new ActionException("Error: EditBookAmountAction class. ", e);
        }
        return REDIRECT_PREFIX + BOOK_AMOUNT_BOOK_ID_PAGE_NAME + bookID;
    }

}

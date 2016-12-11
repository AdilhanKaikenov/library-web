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
 * DeleteBookAction class created on 08.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class DeleteBookAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(DeleteBookAction.class);
    private static final String BOOK_ID_PARAMETER = "bookID";
    private static final String BOOK_DELETE_RESULT_PAGE_NAME = "book-delete-result";
    private static final String BOOK_DELETED_ATTRIBUTE = "bookDeleted";
    private static final String IMPOSSIBLE_TO_REMOVE_ATTRIBUTE = "impossibleToRemove";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The DeleteBookAction started execute.");

        int bookID = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));

        OrderBookService orderBookService = new OrderBookService();
        BookService bookService = new BookService();

        try {
            int bookOrderNumber = orderBookService.getOrdersNumberByBookID(bookID);

            if (bookOrderNumber != 0){
                request.setAttribute(IMPOSSIBLE_TO_REMOVE_ATTRIBUTE, "book.delete.failed.message");
                return BOOK_DELETE_RESULT_PAGE_NAME;
            }

            Book book = bookService.getBookById(bookID);
            book.setDeleted(true);

            bookService.updateBook(book);

            request.setAttribute(BOOK_DELETED_ATTRIBUTE, "book.delete.success.message");

        } catch (ServiceException e) {
            throw new ActionException("Error: DeleteBookAction class. Can not delete book properly:", e);
        }
        return BOOK_DELETE_RESULT_PAGE_NAME;
    }
}

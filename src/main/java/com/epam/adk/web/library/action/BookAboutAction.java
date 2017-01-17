package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.model.Comment;
import com.epam.adk.web.library.model.OrderBook;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.service.BookService;
import com.epam.adk.web.library.service.CommentService;
import com.epam.adk.web.library.service.OrdersBooksService;
import com.epam.adk.web.library.util.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.epam.adk.web.library.util.ConstantsHolder.*;

/**
 * BookAboutAction class created on 03.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class BookAboutAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(BookAboutAction.class);

    private static final int LINE_PER_PAGE_NUMBER = 4;
    private static final String BOOK_REQUEST_ATTRIBUTE = "book";
    private static final String BOOK_ORDERED_REQUEST_ATTRIBUTE = "bookOrdered";
    private static final String BOOK_COMMENTS_REQUEST_ATTRIBUTE = "bookComments";
    private static final String BOOK_ADDED_TO_ORDER_STORED_MESSAGE = "book.added.to.order";
    private static final String BOOK_ALREADY_ORDERED_STORED_MESSAGE = "book.already.ordered";
    private static final String BOOK_ADDED_TO_ORDER_REQUEST_ATTRIBUTE = "bookAddedToOrder";
    private static final String AVAILABLE_BOOK_AMOUNT_REQUEST_ATTRIBUTE = "availableBookAmount";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The BookAboutAction started execute.");

        boolean isCreated = false;
        HttpSession session = request.getSession(isCreated);

        User user = ((User) session.getAttribute(USER_PARAMETER));

        int bookID = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));
        log.debug("Book ID = {}", bookID);

        BookService bookService = new BookService();
        CommentService commentService = new CommentService();
        OrdersBooksService ordersBooksService = new OrdersBooksService();

        try {
            Book book = bookService.getBookById(bookID);

            int availableBookAmount = ordersBooksService.getAvailableBookAmount(book.getId());

            Pagination pagination = new Pagination();
            int pageNumber = pagination.getPageNumber(request, availableBookAmount, LINE_PER_PAGE_NUMBER);

            List<Comment> bookComments = commentService.getPaginatedComments(bookID, pageNumber, LINE_PER_PAGE_NUMBER);
            if (!bookComments.isEmpty()) {
                request.setAttribute(BOOK_COMMENTS_REQUEST_ATTRIBUTE, bookComments);
            }

            proceedTo(request, user, ordersBooksService, book);

            request.setAttribute(BOOK_REQUEST_ATTRIBUTE, book);
            request.setAttribute(AVAILABLE_BOOK_AMOUNT_REQUEST_ATTRIBUTE, availableBookAmount);

        } catch (ServiceException e) {
            throw new ActionException("Error: BookAboutAction class, execute() method. Can not give info about book:", e);
        }
        return ABOUT_BOOK_PAGE;
    }

    private void proceedTo(HttpServletRequest request, User user, OrdersBooksService ordersBooksService, Book book) throws ServiceException {
        if (user != null) {

            OrderBook orderBook = createOrderBook(user, book);

            List<Book> subscriptionBooks = user.getSubscriptionBooks();
            List<Book> readingRoomBooks = user.getReadingRoomBooks();

            int orderedBooksNumber = ordersBooksService.getOrderedBooksNumber(orderBook);

            if (orderedBooksNumber > 0) { // If the book is already ordered
                request.setAttribute(BOOK_ORDERED_REQUEST_ATTRIBUTE, BOOK_ALREADY_ORDERED_STORED_MESSAGE);
            }

            if (subscriptionBooks.contains(book) || readingRoomBooks.contains(book)) {
                request.setAttribute(BOOK_ADDED_TO_ORDER_REQUEST_ATTRIBUTE, BOOK_ADDED_TO_ORDER_STORED_MESSAGE);
            }
        }
    }

    private OrderBook createOrderBook(User user, Book book) {
        OrderBook orderBook = new OrderBook();
        orderBook.setUser(user);
        orderBook.setBook(book);
        return orderBook;
    }
}

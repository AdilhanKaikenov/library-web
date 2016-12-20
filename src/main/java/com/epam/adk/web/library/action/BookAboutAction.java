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

/**
 * BookAboutAction class created on 03.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class BookAboutAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(BookAboutAction.class);

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int LINE_PER_PAGE_NUMBER = 4;
    private static final String BOOK_ID_PARAMETER = "id";
    private static final String USER_PARAMETER = "user";
    private static final String PAGE_PARAMETER = "page";
    private static final String ABOUT_BOOK_PAGE = "about-book";
    private static final String BOOK_REQUEST_ATTRIBUTE = "book";
    private static final String PAGES_NUMBER_REQUEST_ATTRIBUTE = "pagesNumber";
    private static final String BOOK_ORDERED_REQUEST_ATTRIBUTE = "bookOrdered";
    private static final String BOOK_COMMENTS_REQUEST_ATTRIBUTE = "bookComments";
    private static final String BOOK_ADDED_TO_ORDER_REQUEST_ATTRIBUTE = "bookAddedToOrder";
    private static final String AVAILABLE_BOOK_AMOUNT_REQUEST_ATTRIBUTE = "availableBookAmount";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The BookAboutAction started execute.");

        HttpSession session = request.getSession(false);

        User user = ((User) session.getAttribute(USER_PARAMETER));

        int bookID = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));
        log.debug("Book ID = {}", bookID);

        BookService bookService = new BookService();
        CommentService commentService = new CommentService();
        OrdersBooksService ordersBooksService = new OrdersBooksService();

        int page = DEFAULT_PAGE_NUMBER;
        String pageParameter = request.getParameter(PAGE_PARAMETER);

        if (pageParameter != null) {
            page = Integer.parseInt(pageParameter);
            log.debug("Page #{}", page);
        }

        try {
            Book book = bookService.getBookById(bookID);
            int availableBookAmount = ordersBooksService.getAvailableBookAmount(bookID);
            int commentsNumber = commentService.getCommentsNumberByBookId(bookID);
            log.debug("Total comments number = {}", commentsNumber);
            Pagination pagination = new Pagination();
            int pagesNumber = pagination.getPagesNumber(commentsNumber, LINE_PER_PAGE_NUMBER);
            log.debug("Total pages number = {}", pagesNumber);
            List<Comment> bookComments = commentService.getPaginatedComments(bookID, page, LINE_PER_PAGE_NUMBER);

            if (bookComments.size() != 0) {
                request.setAttribute(BOOK_COMMENTS_REQUEST_ATTRIBUTE, bookComments);
            }

            request.setAttribute(BOOK_REQUEST_ATTRIBUTE, book);
            request.setAttribute(PAGES_NUMBER_REQUEST_ATTRIBUTE, pagesNumber);
            request.setAttribute(AVAILABLE_BOOK_AMOUNT_REQUEST_ATTRIBUTE, availableBookAmount);

            if (user != null) {

                OrderBook orderBook = new OrderBook();
                orderBook.setUser(user);
                orderBook.setBook(book);

                List<Book> subscriptionBooks = user.getSubscriptionBooks();
                List<Book> readingRoomBooks = user.getReadingRoomBooks();

                int orderedBooksNumber = ordersBooksService.getOrderedBooksNumber(orderBook);

                if (orderedBooksNumber > 0) {
                    request.setAttribute(BOOK_ORDERED_REQUEST_ATTRIBUTE, "book.already.ordered");
                }

                if (subscriptionBooks.contains(book) || readingRoomBooks.contains(book)) {
                    request.setAttribute(BOOK_ADDED_TO_ORDER_REQUEST_ATTRIBUTE, "book.added.to.order");
                }
            }

        } catch (ServiceException e) {
            throw new ActionException("Error: BookAboutAction class, execute() method. Can not give info about book:", e);
        }
        return ABOUT_BOOK_PAGE;
    }
}

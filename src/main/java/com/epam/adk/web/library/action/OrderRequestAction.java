package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.model.OrderBook;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.model.enums.OrderType;
import com.epam.adk.web.library.service.OrdersBooksService;
import com.epam.adk.web.library.service.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;

/**
 * OrderRequestAction class created on 16.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class OrderRequestAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(OrderRequestAction.class);

    private static final String USER_PARAMETER = "user";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String MY_ORDER_PAGE_NAME = "my-order";
    private static final String ORDER_TYPE_PARAMETER = "orderType";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The OrderRequestAction started execute.");

        HttpSession session = request.getSession(false);

        User user = ((User) session.getAttribute(USER_PARAMETER));

        OrderType orderType = OrderType.from(request.getParameter(ORDER_TYPE_PARAMETER));

        java.util.Date currentDate = new java.util.Date();
        Date orderDate = new Date(currentDate.getTime());

        OrdersService registerOrderService = new OrdersService();

        Order order = new Order();

        order.setUser(user);
        order.setOrderDate(orderDate);
        order.setOrderType(orderType);

        try {
            Order addedOrder = registerOrderService.add(order);

            List<Book> subscriptionBooks = user.getSubscriptionBooks();
            List<Book> readingRoomBooks = user.getReadingRoomBooks();

            switch (orderType) {
                case SUBSCRIPTION:
                    submitOrdersBooks(user, addedOrder, subscriptionBooks);
                    subscriptionBooks.clear();
                    break;
                case READING_ROOM:
                    submitOrdersBooks(user, addedOrder, readingRoomBooks);
                    readingRoomBooks.clear();
                    break;
            }

        } catch (ServiceException e) {
            throw new ActionException("Error: OrderRequestAction class, execute() method.", e);
        }

        return REDIRECT_PREFIX + MY_ORDER_PAGE_NAME;
    }

    private void submitOrdersBooks(User user, Order order, List<Book> books) throws ServiceException {
        OrdersBooksService ordersBooksService = new OrdersBooksService();

        for (Book book : books) {
            OrderBook orderBook = new OrderBook();
            orderBook.setUser(user);
            orderBook.setBook(book);
            orderBook.setOrder(order);
            ordersBooksService.submitOrderBook(orderBook);
        }
    }

}

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

import static com.epam.adk.web.library.util.ConstantsHolder.*;

/**
 * OrderRequestAction class created on 16.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class OrderRequestAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(OrderRequestAction.class);

    private static final String ORDER_TYPE_PARAMETER = "orderType";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The OrderRequestAction started execute.");

        boolean isCreated = false;
        HttpSession session = request.getSession(isCreated);

        User user = ((User) session.getAttribute(USER_PARAMETER));

        OrderType orderType = OrderType.getFromValue(request.getParameter(ORDER_TYPE_PARAMETER));
        log.debug("Order type = {}", orderType.getValue());

        java.util.Date currentDate = new java.util.Date();
        Date orderDate = new Date(currentDate.getTime());

        OrdersService registerOrderService = new OrdersService();

        Order order = createOrder(user, orderType, orderDate);

        try {
            Order addedOrder = registerOrderService.add(order);

            submitBooksAccordingOrderType(user, orderType, addedOrder);

        } catch (ServiceException e) {
            throw new ActionException("Error: OrderRequestAction class, execute() method.", e);
        }

        return REDIRECT_PREFIX + MY_ORDER_PAGE_NAME;
    }

    /**
     * Creating Order.
     *
     * @param user User from session
     * @param orderType Type of order (subscription or reading room)
     * @param orderDate date of order
     * @return Order
     */
    private Order createOrder(User user, OrderType orderType, Date orderDate) {
        Order order = new Order();

        order.setUser(user);
        order.setOrderDate(orderDate);
        order.setOrderType(orderType);
        log.debug("New Order created.");
        return order;
    }

    /**
     * Method sends the books according to the order type.
     *
     * @param user User from session
     * @param orderType Type of order (subscription or reading room)
     * @param addedOrder Order
     * @throws ServiceException
     */
    private void submitBooksAccordingOrderType(User user, OrderType orderType, Order addedOrder) throws ServiceException {
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

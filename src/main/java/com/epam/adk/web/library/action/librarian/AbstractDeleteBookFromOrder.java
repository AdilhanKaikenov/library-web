package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.model.OrderBook;
import com.epam.adk.web.library.service.OrdersBooksService;
import com.epam.adk.web.library.service.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.adk.web.library.util.ConstantsHolder.*;

/**
 * AbstractDeleteBookFromOrder class created on 19.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public abstract class AbstractDeleteBookFromOrder implements Action {

    private static final Logger log = LoggerFactory.getLogger(AbstractDeleteBookFromOrder.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The AbstractDeleteBookFromOrder started execute.");

        int userID = Integer.parseInt(request.getParameter(USER_ID_PARAMETER));
        log.debug("User ID = {}", userID);
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));
        log.debug("Book ID = {}", bookID);
        int orderID = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER));
        log.debug("Order ID = {}", orderID);
        log.debug("Request parameters valid.");

        OrdersService ordersService = new OrdersService();

        OrdersBooksService ordersBooksService = new OrdersBooksService();

        try {
            OrderBook orderBook = ordersBooksService.getOrderBookById(userID, bookID);
            ordersBooksService.delete(orderBook);

            int orderBooksNumber = ordersBooksService.getOrderBooksNumberByOrderId(orderID);

            if (orderBooksNumber == 0){ // if there are no books
                Order order = new Order();
                order.setId(orderID);
                ordersService.delete(order);
                return REDIRECT_PREFIX + getPage();
            }

        } catch (ServiceException e) {
            throw new ActionException("Error: DeleteBookFromOrderRequestAction class, execute() method.", e);
        }
        log.debug("The AbstractDeleteBookFromOrder class finished work. Return processing page = {}", getOrderProcessPage());
        return REDIRECT_PREFIX + getOrderProcessPage() + orderID;
    }

    protected abstract String getOrderProcessPage();

    protected abstract String getPage();

}

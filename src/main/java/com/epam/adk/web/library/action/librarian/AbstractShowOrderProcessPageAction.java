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
import java.util.List;

/**
 *  AbstractShowOrderProcessPageAction class created on 18.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public abstract class AbstractShowOrderProcessPageAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(AbstractDeleteBookFromOrder.class);

    protected static final String ORDER_ID_PARAMETER = "orderID";
    private static final String ORDER_REQUEST_ATTRIBUTE = "order";
    private static final String ORDERS_BOOKS_REQUEST_ATTRIBUTE = "ordersBooks";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The AbstractShowOrderProcessPageAction started execute.");

        int orderID = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER));
        log.debug("Order ID = {}", orderID);

        OrdersBooksService ordersBooksService = new OrdersBooksService();
        OrdersService ordersService = new OrdersService();

        try {
            List<OrderBook> ordersBooks = ordersBooksService.getOrdersBooks(orderID);
            Order order = ordersService.getOrderById(orderID);

            request.setAttribute(ORDERS_BOOKS_REQUEST_ATTRIBUTE, ordersBooks);
            request.setAttribute(ORDER_REQUEST_ATTRIBUTE, order);

        } catch (ServiceException e) {
            throw new ActionException("Error: AbstractShowOrderProcessPageAction class, execute() method.", e);
        }
        log.debug("The AbstractShowOrderProcessPageAction class finished work. Return page = {}", getPage());
        return getPage();
    }

    protected abstract String getPage();

}

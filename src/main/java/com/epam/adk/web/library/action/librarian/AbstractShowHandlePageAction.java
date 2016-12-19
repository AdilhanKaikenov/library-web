package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.model.OrderBook;
import com.epam.adk.web.library.service.OrdersBooksService;
import com.epam.adk.web.library.service.OrdersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  AbstractShowHandlePageAction class created on 18.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public abstract class AbstractShowHandlePageAction implements Action {

    protected static final String ORDER_ID_PARAMETER = "orderID";
    private static final String ORDERS_BOOKS_REQUEST_ATTRIBUTE = "ordersBooks";
    private static final String ORDER_REQUEST_ATTRIBUTE = "order";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {

        int orderID = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER));

        OrdersBooksService ordersBooksService = new OrdersBooksService();
        OrdersService ordersService = new OrdersService();

        try {
            List<OrderBook> ordersBooks = ordersBooksService.getOrdersBooks(orderID);
            Order order = ordersService.getOrderById(orderID);

            request.setAttribute(ORDERS_BOOKS_REQUEST_ATTRIBUTE, ordersBooks);
            request.setAttribute(ORDER_REQUEST_ATTRIBUTE, order);

        } catch (ServiceException e) {
            throw new ActionException("Error: AbstractShowHandlePageAction class, execute() method.", e);
        }

        return getPage();
    }

    protected abstract String getPage();

}

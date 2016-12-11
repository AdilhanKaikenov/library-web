package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.service.OrderBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * BookReturnedAction class created on 07.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class BookReturnedAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(BookReturnedAction.class);
    private static final String ORDER_ID_PARAMETER = "orderID";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String ORDERS_PAGE_NAME = "orders";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The BookReturnedAction started execute.");

        int orderID = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER));

        OrderBookService orderBookService = new OrderBookService();

        try {
            Order order = orderBookService.getOrderById(orderID);
            orderBookService.writeToHistory(order);
        } catch (ServiceException e) {
            throw new ActionException("Error: BookReturnedAction class:", e);
        }
        return REDIRECT_PREFIX + ORDERS_PAGE_NAME;
    }
}

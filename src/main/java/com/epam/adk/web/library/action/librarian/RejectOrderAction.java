package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.service.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RejectOrderAction class created on 07.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class RejectOrderAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(LendOutAction.class);

    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String ORDER_ID_PARAMETER = "orderID";
    private static final String REQUESTS_PAGE_NAME = "requests";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The RejectOrderAction started execute.");

        int orderID = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER));
        log.debug("Order ID = {}", orderID);

        OrdersService ordersService = new OrdersService();

        try {
            Order order = ordersService.getOrderById(orderID);

            ordersService.delete(order);

        } catch (ServiceException e) {
            throw new ActionException("Error: RejectOrderAction class, execute() method.", e);
        }

        return REDIRECT_PREFIX + REQUESTS_PAGE_NAME;
    }
}
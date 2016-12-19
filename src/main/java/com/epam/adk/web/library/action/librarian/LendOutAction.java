package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.model.enums.OrderType;
import com.epam.adk.web.library.service.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

/**
 * LendOutAction class created on 07.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class LendOutAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(LendOutAction.class);

    private static final int TWO_WEEKS_TIME_DURATION = 1209600000;
    private static final String ORDER_ID_PARAMETER = "orderID";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String REQUESTS_PAGE_NAME = "requests";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The LendOutAction started execute.");

        int orderID = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER));

        OrdersService ordersService = new OrdersService();

        try {
            Order order = ordersService.getOrderById(orderID);

            order.setStatus(true);

            java.util.Date today = new java.util.Date();
            Date fromDate = new Date(today.getTime());
            Date toDate = new Date(today.getTime() + TWO_WEEKS_TIME_DURATION);

            if (order.getOrderType() == OrderType.SUBSCRIPTION) {
                order.setFrom(fromDate);
                order.setTo(toDate);
            }

            if (order.getOrderType() == OrderType.READING_ROOM) {
                order.setFrom(fromDate);
                order.setTo(fromDate);
            }

            ordersService.update(order);

        } catch (ServiceException e) {
            throw new ActionException("Error: LendOutAction class, execute() method.", e);
        }

        return REDIRECT_PREFIX + REQUESTS_PAGE_NAME;
    }
}

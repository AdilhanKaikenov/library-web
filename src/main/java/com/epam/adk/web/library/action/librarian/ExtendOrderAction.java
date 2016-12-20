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
import java.sql.Date;

/**
 * ExtendOrderAction class created on 19.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ExtendOrderAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ExtendOrderAction.class);

    private static final int ONE_WEEK_TIME_DURATION = 604800000;
    private static final String ORDER_ID_PARAMETER = "orderID";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String HANDLE_RETURN_BOOKS_ORDER_ID_PAGE_NAME = "process-return-books&orderID=";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {

        int orderID = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER));
        log.debug("Order ID = {}", orderID);

        OrdersService ordersService = new OrdersService();

        try {
            Order order = ordersService.getOrderById(orderID);

            Date to = order.getTo();
            long time = to.getTime() + ONE_WEEK_TIME_DURATION;
            to.setTime(time);
            order.setTo(to);

            ordersService.update(order);

        } catch (ServiceException e) {
            throw new ActionException("Error: ExtendOrderAction class. Validation failed:", e);
        }

        return REDIRECT_PREFIX + HANDLE_RETURN_BOOKS_ORDER_ID_PAGE_NAME + orderID;
    }
}

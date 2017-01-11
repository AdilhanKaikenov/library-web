package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.service.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

import static com.epam.adk.web.library.util.ConstantsHolder.ORDER_ID_PARAMETER;

/**
 * ShowProcessReturnBooksPageAction class created on 18.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowProcessReturnBooksPageAction extends AbstractShowOrderProcessPageAction {

    private static final Logger log = LoggerFactory.getLogger(ShowProcessReturnBooksPageAction.class);

    private static final int ONE_DAY_DURATION = 86400000;
    private static final String HANDLE_RETURN_BOOKS_PAGE_NAME = "process-return-books";
    private static final String OPTION_TO_EXTEND_REQUEST_ATTRIBUTE = "optionToExtend";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The ShowProcessReturnBooksPageAction started execute.");

        int orderID = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER));
        log.debug("Order ID = {}", orderID);

        OrdersService ordersService = new OrdersService();

        try {
            Order order = ordersService.getOrderById(orderID);

            java.util.Date curDay = new java.util.Date();
            Date today = new Date(curDay.getTime());

            Date dateTo = order.getTo();

            // diffTime - the number of days till the last day of the subscription period
            long diffTime = (dateTo.getTime() - today.getTime()) / ONE_DAY_DURATION;
            log.debug("diffTime = {}", diffTime);
            boolean optionToExtend = false;
            if (diffTime <= 0) { // If the subscription has expired
                optionToExtend = true;
            }
            request.setAttribute(OPTION_TO_EXTEND_REQUEST_ATTRIBUTE, optionToExtend);

        } catch (ServiceException e) {
            throw new ActionException("Error: ShowProcessReturnBooksPageAction class.", e);
        }
        return super.execute(request, response);
    }

    @Override
    protected String getPage() {
        return HANDLE_RETURN_BOOKS_PAGE_NAME;
    }
}

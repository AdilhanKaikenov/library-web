package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.service.OrdersService;
import com.epam.adk.web.library.util.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * AbstractShowOrdersAction class created on 07.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public abstract class AbstractShowOrdersAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(AbstractShowOrdersAction.class);

    private static final int LINE_PER_PAGE_NUMBER = 5;
    private static final String ORDERS_REQUEST_ATTRIBUTE = "orders";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The AbstractShowOrdersAction started execute.");

        OrdersService ordersService = new OrdersService();

        try {
            int ordersNumber = ordersService.getOrdersNumberByStatus(getOrderStatus());
            log.debug("Orders number = {}", ordersNumber);

            Pagination pagination = new Pagination();
            int pageNumber = pagination.getPageNumber(request, ordersNumber, LINE_PER_PAGE_NUMBER);

            List<Order> orders = ordersService.getPaginatedByOrderStatus(getOrderStatus(), pageNumber, LINE_PER_PAGE_NUMBER);

            if (!orders.isEmpty()) {
                log.debug("There is no one order.");
                request.setAttribute(ORDERS_REQUEST_ATTRIBUTE, orders);
            }

        } catch (ServiceException e) {
            throw new ActionException("Error: AbstractShowOrdersAction class, execute() method.", e);
        }
        log.debug("The AbstractShowOrdersAction class finished work. Return page = {}", getPage());
        return getPage();
    }

    protected abstract String getPage();

    protected abstract boolean getOrderStatus();

}

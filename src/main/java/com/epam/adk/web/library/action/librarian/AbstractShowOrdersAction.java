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

import static com.epam.adk.web.library.util.ConstantsHolder.PAGES_NUMBER_REQUEST_ATTRIBUTE;
import static com.epam.adk.web.library.util.ConstantsHolder.PAGE_PARAMETER;

/**
 * AbstractShowOrdersAction class created on 07.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public abstract class AbstractShowOrdersAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(AbstractShowOrdersAction.class);

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int LINE_PER_PAGE_NUMBER = 5;
    private static final String ORDERS_REQUEST_ATTRIBUTE = "orders";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The AbstractShowOrdersAction started execute.");

        OrdersService ordersService = new OrdersService();

        try {
            int page = getPageNumber(request, ordersService);

            List<Order> orders = ordersService.getPaginatedByOrderStatus(getOrderStatus(), page, LINE_PER_PAGE_NUMBER);

            if (!orders.isEmpty()) {
                request.setAttribute(ORDERS_REQUEST_ATTRIBUTE, orders);
            }

        } catch (ServiceException e) {
            throw new ActionException("Error: AbstractShowOrdersAction class, execute() method.", e);
        }
        log.debug("The AbstractShowOrdersAction class finished work. Return page = {}", getPage());
        return getPage();
    }

    private int getPageNumber(HttpServletRequest request, OrdersService ordersService) throws ServiceException {
        int page = DEFAULT_PAGE_NUMBER;
        String pageParameter = request.getParameter(PAGE_PARAMETER);
        if (pageParameter != null) {
            page = Integer.parseInt(pageParameter);
            log.debug("Page #{}", page);
        }
        int ordersNumber = ordersService.getOrdersNumberByStatus(getOrderStatus());
        log.debug("Total orders number = {}", ordersNumber);
        Pagination pagination = new Pagination();
        int pagesNumber = pagination.getPagesNumber(ordersNumber, LINE_PER_PAGE_NUMBER);
        log.debug("Total pages number = {}", pagesNumber);
        request.setAttribute(PAGES_NUMBER_REQUEST_ATTRIBUTE, pagesNumber);
        return page;
    }

    protected abstract String getPage();

    protected abstract boolean getOrderStatus();

}

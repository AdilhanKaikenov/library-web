package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.service.OrderBookService;
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
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int LINE_PER_PAGE_NUMBER = 10;
    private static final String PAGE_PARAMETER = "page";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The AbstractShowOrdersAction started execute.");

        int page = DEFAULT_PAGE_NUMBER;
        String pageParameter = request.getParameter(PAGE_PARAMETER);
        if (pageParameter != null) {
            page = Integer.parseInt(pageParameter);
            log.debug("AbstractShowOrdersAction: page #{}", page);
        }

        OrderBookService orderBookService = new OrderBookService();

        try {
            int ordersNumber = orderBookService.getOrdersNumberByStatusID(getOrderStatusID());
            log.debug("AbstractShowOrdersAction: total orders number = {}", ordersNumber);
            Pagination pagination = new Pagination();
            int pagesNumber = pagination.getPagesNumber(ordersNumber, LINE_PER_PAGE_NUMBER);
            log.debug("AbstractShowOrdersAction: total pages number = {}", pagesNumber);

            List<Order> orders = orderBookService.getPaginatedByOrderStatus(getOrderStatusID(), page, LINE_PER_PAGE_NUMBER);

            request.setAttribute("pagesNumber", pagesNumber);
            request.setAttribute("orders", orders);

        } catch (ServiceException e) {
            throw new ActionException("Error: AbstractShowOrdersAction class, execute() method.", e);
        }
        log.debug("The AbstractShowOrdersAction class finished work. Return page = {}", getPage());
        return getPage();
    }

    protected abstract String getPage();

    protected abstract int getOrderStatusID();

}

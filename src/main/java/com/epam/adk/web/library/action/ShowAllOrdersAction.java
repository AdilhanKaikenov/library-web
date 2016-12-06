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
 * BookAboutAction class created on 06.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowAllOrdersAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ShowAllOrdersAction.class);
    private static final String PAGE_PARAMETER = "page";
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int LINE_PER_PAGE_NUMBER = 10;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The ShowAllOrdersAction started execute.");

        int page = DEFAULT_PAGE_NUMBER;
        String pageParameter = request.getParameter(PAGE_PARAMETER);
        if (pageParameter != null) {
            page = Integer.parseInt(pageParameter);
            log.debug("ShowAllOrdersAction: page #{}", page);
        }

        OrderBookService orderBookService = new OrderBookService();

        try {
            int ordersNumber = orderBookService.getOrdersNumber();
            log.debug("ShowAllOrdersAction: total orders number = {}", ordersNumber);
            Pagination pagination = new Pagination();
            int pagesNumber = pagination.getPagesNumber(ordersNumber, LINE_PER_PAGE_NUMBER);
            log.debug("ShowAllOrdersAction: total pages number = {}", pagesNumber);

            List<Order> orders = orderBookService.getPaginated(page, LINE_PER_PAGE_NUMBER);

            request.setAttribute("pagesNumber", pagesNumber);
            request.setAttribute("orders", orders);

        } catch (ServiceException e) {
            throw new ActionException("Error: ShowAllOrdersAction class, execute() method.", e);
        }
        return "orders";
    }
}

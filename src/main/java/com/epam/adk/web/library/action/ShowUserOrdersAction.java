package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.service.OrderBookService;
import com.epam.adk.web.library.util.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * BookAboutAction class created on 06.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowUserOrdersAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ShowUserOrdersAction.class);
    private static final String USER_PARAMETER = "user";
    private static final String PAGE_PARAMETER = "page";
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int LINE_PER_PAGE_NUMBER = 10;
    private static final String USER_ORDERS_REQUEST_ATTRIBUTE = "userOrders";
    private static final String PAGES_NUMBER_REQUEST_ATTRIBUTE = "pagesNumber";
    private static final String USER_ORDERS_PAGE_NAME = "user-orders";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The ShowUserOrdersAction started execute.");

        HttpSession session = request.getSession(false);

        User user = ((User) session.getAttribute(USER_PARAMETER));
        int userID = user.getId();

        int page = DEFAULT_PAGE_NUMBER;
        String pageParameter = request.getParameter(PAGE_PARAMETER);
        if (pageParameter != null) {
            page = Integer.parseInt(pageParameter);
            log.debug("ShowUserOrdersAction: page #{}", page);
        }

        OrderBookService orderBookService = new OrderBookService();

        try {
            int userOrdersNumber = orderBookService.getOrdersNumberByUser(userID);
            log.debug("ShowUserOrdersAction: total orders number = {}", userOrdersNumber);
            Pagination pagination = new Pagination();
            int pagesNumber = pagination.getPagesNumber(userOrdersNumber, LINE_PER_PAGE_NUMBER);
            log.debug("ShowUserOrdersAction: total pages number = {}", pagesNumber);

            List<Order> userOrders = orderBookService.getPaginatedUserOrders(userID, page, LINE_PER_PAGE_NUMBER);

            if (userOrders.size() != 0) {
                request.setAttribute(USER_ORDERS_REQUEST_ATTRIBUTE, userOrders);
            }

            request.setAttribute(PAGES_NUMBER_REQUEST_ATTRIBUTE, pagesNumber);

        } catch (ServiceException e) {
            throw new ActionException("Error: ShowUserOrdersAction class, execute() method.", e);
        }
        return USER_ORDERS_PAGE_NAME;
    }
}

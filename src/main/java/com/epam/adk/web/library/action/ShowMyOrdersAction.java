package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.service.OrdersService;
import com.epam.adk.web.library.util.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.epam.adk.web.library.util.ConstantsHolder.USER_PARAMETER;

/**
 * BookAboutAction class created on 06.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowMyOrdersAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ShowMyOrdersAction.class);

    private static final int LINE_PER_PAGE_NUMBER = 5;
    private static final String USER_ORDERS_PAGE_NAME = "my-orders";
    private static final String USER_ORDERS_REQUEST_ATTRIBUTE = "userOrders";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The ShowMyOrdersAction started execute.");

        boolean isCreated = false;
        HttpSession session = request.getSession(isCreated);

        User user = ((User) session.getAttribute(USER_PARAMETER));
        int userID = user.getId();

        OrdersService ordersService = new OrdersService();

        try {
            int userOrdersNumber = ordersService.getOrdersNumberByUserID(userID);

            Pagination pagination = new Pagination();
            int pageNumber = pagination.getPageNumber(request, userOrdersNumber, LINE_PER_PAGE_NUMBER);

            List<Order> userOrders = ordersService.getPaginatedUserOrders(userID, pageNumber, LINE_PER_PAGE_NUMBER);
            if (!userOrders.isEmpty()) {
                request.setAttribute(USER_ORDERS_REQUEST_ATTRIBUTE, userOrders);
            }

        } catch (ServiceException e) {
            throw new ActionException("Error: ShowMyOrdersAction class, execute() method.", e);
        }
        return USER_ORDERS_PAGE_NAME;
    }
}

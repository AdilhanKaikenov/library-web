package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.model.enums.OrderType;
import com.epam.adk.web.library.service.OrderBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;

/**
 * BookOrderRequestAction class created on 05.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class BookOrderRequestAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(BookOrderRequestAction.class);
    private static final String USER_PARAMETER = "user";
    private static final String BOOK_ID_PARAMETER = "book_id";
    private static final String ORDER_TYPE_PARAMETER = "order_type";
    private static final String ORDER_REQUEST_INFO_PAGE = "order-request-info";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The BookOrderRequestAction started execute.");
        HttpSession session = request.getSession(false);

        User user = ((User) session.getAttribute(USER_PARAMETER));
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));
        OrderType orderType = OrderType.from(request.getParameter(ORDER_TYPE_PARAMETER));
        java.util.Date currentDate = new java.util.Date();
        Date orderDate = new Date(currentDate.getTime());

        Order order = new Order();
        order.setUserID(user.getId());
        order.setBookID(bookID);
        order.setOrderDate(orderDate);
        order.setType(orderType);
        request.setAttribute("bookID", bookID);

        OrderBookService orderService = new OrderBookService();

        try {
            int orderNumber = orderService.getOrderNumber(order);

            if (orderNumber > 0) {
                request.setAttribute("sentRequestFailed", "book.order.request.failed");
                return ORDER_REQUEST_INFO_PAGE;
            }

            Order sentRequest = orderService.sendRequest(order);
            if (sentRequest != null) {
                request.setAttribute("sentRequestSuccessful", "book.order.request.successful");
            }

        } catch (ServiceException e) {
            throw new ActionException("Error: BookOrderRequestAction class, Can not send new order book request:", e);
        }
        return ORDER_REQUEST_INFO_PAGE;
    }
}

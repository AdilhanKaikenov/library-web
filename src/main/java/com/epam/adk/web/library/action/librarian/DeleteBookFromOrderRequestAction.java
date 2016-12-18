package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.OrderBook;
import com.epam.adk.web.library.service.OrdersBooksService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DeleteBookFromOrderRequestAction class created on 18.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class DeleteBookFromOrderRequestAction implements Action {

    private static final String USER_ID_PARAMETER = "userID";
    private static final String BOOK_ID_PARAMETER = "bookID";
    private static final String ORDER_ID_PARAMETER = "orderID";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String HANDLE_ORDER_REQUEST_ORDER_ID_PAGE_NAME = "handle-order-request&orderID=";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {

        int userID = Integer.parseInt(request.getParameter(USER_ID_PARAMETER));
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));
        int orderID = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER));

        OrdersBooksService ordersBooksService = new OrdersBooksService();

        try {
            OrderBook orderBook = ordersBooksService.getOrderBookById(userID, bookID);

            ordersBooksService.delete(orderBook);

        } catch (ServiceException e) {
            throw new ActionException("Error: DeleteBookFromOrderRequestAction class, execute() method.", e);
        }

        return REDIRECT_PREFIX + HANDLE_ORDER_REQUEST_ORDER_ID_PAGE_NAME + orderID;
    }
}

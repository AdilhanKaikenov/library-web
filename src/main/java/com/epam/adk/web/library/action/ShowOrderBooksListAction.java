package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.OrderBook;
import com.epam.adk.web.library.service.OrdersBooksService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.epam.adk.web.library.util.ConstantsHolder.ORDER_ID_PARAMETER;

/**
 * ShowOrderBooksListAction class created on 18.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowOrderBooksListAction implements Action {

    private static final String ORDER_BOOK_LIST_PAGE_NAME = "order-book-list";
    private static final String ORDERS_BOOKS_REQUEST_ATTRIBUTE = "ordersBooks";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {

        int orderID = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER));

        OrdersBooksService ordersBooksService = new OrdersBooksService();

        try {
            List<OrderBook> ordersBooks = ordersBooksService.getOrdersBooks(orderID);

            request.setAttribute(ORDERS_BOOKS_REQUEST_ATTRIBUTE, ordersBooks);

        } catch (ServiceException e) {
            throw new ActionException("Error: ShowOrderBooksListAction class, execute() method.", e);
        }

        return ORDER_BOOK_LIST_PAGE_NAME;
    }
}

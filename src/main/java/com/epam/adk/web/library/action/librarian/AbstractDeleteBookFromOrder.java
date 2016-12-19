package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Order;
import com.epam.adk.web.library.model.OrderBook;
import com.epam.adk.web.library.service.OrdersBooksService;
import com.epam.adk.web.library.service.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AbstractDeleteBookFromOrder class created on 19.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public abstract class AbstractDeleteBookFromOrder implements Action {

    private static final Logger log = LoggerFactory.getLogger(AbstractDeleteBookFromOrder.class);

    private static final String USER_ID_PARAMETER = "userID";
    private static final String BOOK_ID_PARAMETER = "bookID";
    private static final String ORDER_ID_PARAMETER = "orderID";
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {

        int userID = Integer.parseInt(request.getParameter(USER_ID_PARAMETER));
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));
        int orderID = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER));

        OrdersService ordersService = new OrdersService();

        OrdersBooksService ordersBooksService = new OrdersBooksService();

        try {
            OrderBook orderBook = ordersBooksService.getOrderBookById(userID, bookID);
            ordersBooksService.delete(orderBook);

            int orderBooksNumber = ordersBooksService.getOrderBooksNumberByOrderId(orderID);

            if (orderBooksNumber == 0){
                Order order = new Order();
                order.setId(orderID);
                ordersService.delete(order);
                return REDIRECT_PREFIX + getPage();
            }

        } catch (ServiceException e) {
            throw new ActionException("Error: DeleteBookFromOrderRequestAction class, execute() method.", e);
        }

        return REDIRECT_PREFIX + getHandlePage() + orderID;
    }

    protected abstract String getHandlePage();

    protected abstract String getPage();

}

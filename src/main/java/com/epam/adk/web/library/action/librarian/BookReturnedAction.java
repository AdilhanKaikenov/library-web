package com.epam.adk.web.library.action.librarian;

/**
 * BookReturnedAction class created on 07.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class BookReturnedAction extends AbstractDeleteBookFromOrder {

    private static final String HANDLE_RETURN_BOOKS_ORDER_ID_PAGE_NAME = "process-return-books&orderID=";
    private static final String ORDERS_PAGE_NAME = "orders";

    @Override
    protected String getOrderProcessPage() {
        return HANDLE_RETURN_BOOKS_ORDER_ID_PAGE_NAME;
    }

    @Override
    protected String getPage() {
        return ORDERS_PAGE_NAME;
    }
}

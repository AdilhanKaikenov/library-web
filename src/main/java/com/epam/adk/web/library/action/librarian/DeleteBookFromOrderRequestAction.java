package com.epam.adk.web.library.action.librarian;

/**
 * DeleteBookFromOrderRequestAction class created on 18.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class DeleteBookFromOrderRequestAction extends AbstractDeleteBookFromOrder {

    private static final String HANDLE_ORDER_REQUEST_ORDER_ID_PAGE_NAME = "handle-order-request&orderID=";
    private static final String REQUESTS_PAGE_NAME = "requests";

    @Override
    protected String getHandlePage() {
        return HANDLE_ORDER_REQUEST_ORDER_ID_PAGE_NAME;
    }

    @Override
    protected String getPage() {
        return REQUESTS_PAGE_NAME;
    }
}

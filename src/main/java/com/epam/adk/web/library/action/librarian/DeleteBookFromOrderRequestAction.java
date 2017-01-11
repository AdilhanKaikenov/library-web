package com.epam.adk.web.library.action.librarian;

import static com.epam.adk.web.library.util.ConstantsHolder.REQUESTS_PAGE_NAME;

/**
 * DeleteBookFromOrderRequestAction class created on 18.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class DeleteBookFromOrderRequestAction extends AbstractDeleteBookFromOrder {

    private static final String HANDLE_ORDER_REQUEST_ORDER_ID_PAGE_NAME = "process-order-request&orderID=";

    @Override
    protected String getOrderProcessPage() {
        return HANDLE_ORDER_REQUEST_ORDER_ID_PAGE_NAME;
    }

    @Override
    protected String getPage() {
        return REQUESTS_PAGE_NAME;
    }
}

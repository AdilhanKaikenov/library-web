package com.epam.adk.web.library.action.librarian;

/**
 * ShowHandleOrderRequestPageAction class created on 18.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowHandleOrderRequestPageAction extends AbstractShowHandlePageAction {

    private static final String HANDLE_ORDER_REQUEST_PAGE_NAME = "handle-order-request";

    @Override
    protected String getPage() {
        return HANDLE_ORDER_REQUEST_PAGE_NAME;
    }
}

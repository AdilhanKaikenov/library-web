package com.epam.adk.web.library.action.librarian;

/**
 * ShowProcessOrderRequestPageAction class created on 18.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowProcessOrderRequestPageAction extends AbstractShowOrderProcessPageAction {

    private static final String HANDLE_ORDER_REQUEST_PAGE_NAME = "process-order-request";

    @Override
    protected String getPage() {
        return HANDLE_ORDER_REQUEST_PAGE_NAME;
    }
}

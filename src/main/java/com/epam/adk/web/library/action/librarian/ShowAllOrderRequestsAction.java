package com.epam.adk.web.library.action.librarian;

/**
 * ShowAllOrderRequestsAction class created on 06.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowAllOrderRequestsAction extends AbstractShowOrdersAction {

    private static final int CONSIDERED_STATUS_ID = 2;
    private static final String REQUESTS_PAGE_NAME = "requests";

    @Override
    protected String getPage() {
        return REQUESTS_PAGE_NAME;
    }

    @Override
    protected int getOrderStatusID() {
        return CONSIDERED_STATUS_ID;
    }

}

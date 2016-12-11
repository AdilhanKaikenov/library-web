package com.epam.adk.web.library.action.librarian;

/**
 * ShowAllRejectedOrdersAction class created on 07.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowAllRejectedOrdersAction extends AbstractShowOrdersAction {

    private static final int REJECTED_STATUS_ID = 1;
    private static final String REJECTED_ORDERS_PAGE_NAME = "rejected-orders";

    @Override
    protected String getPage() {
        return REJECTED_ORDERS_PAGE_NAME;
    }

    @Override
    protected int getOrderStatusID() {
        return REJECTED_STATUS_ID;
    }
}

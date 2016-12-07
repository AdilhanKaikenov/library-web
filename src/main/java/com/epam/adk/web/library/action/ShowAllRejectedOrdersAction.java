package com.epam.adk.web.library.action;

/**
 * ShowAllRejectedOrdersAction class created on 07.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowAllRejectedOrdersAction extends AbstractShowOrdersAction {

    private static final int REJECTED_STATUS_ID = 1;
    private static final String RETURN_PAGE = "rejected-orders";

    @Override
    protected String getPage() {
        return RETURN_PAGE;
    }

    @Override
    protected int getOrderStatusID() {
        return REJECTED_STATUS_ID;
    }
}

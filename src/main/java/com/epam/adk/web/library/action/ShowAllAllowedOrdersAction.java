package com.epam.adk.web.library.action;

/**
 * ShowAllAllowedOrdersAction class created on 07.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowAllAllowedOrdersAction extends AbstractShowOrdersAction {

    private static final int ALLOWED_STATUS_ID = 0;
    private static final String RETURN_PAGE = "orders";

    @Override
    protected String getPage() {
        return RETURN_PAGE;
    }

    @Override
    protected int getOrderStatusID() {
        return ALLOWED_STATUS_ID;
    }
}

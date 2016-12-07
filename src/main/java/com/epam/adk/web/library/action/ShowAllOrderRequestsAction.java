package com.epam.adk.web.library.action;

/**
 * ShowAllOrderRequestsAction class created on 06.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowAllOrderRequestsAction extends AbstractShowOrdersAction {

    private static final int CONSIDERED_STATUS_ID = 2;
    private static final String RETURN_PAGE = "requests";

    @Override
    protected String getPage() {
        return RETURN_PAGE;
    }

    @Override
    protected int getOrderStatusID() {
        return CONSIDERED_STATUS_ID;
    }

}

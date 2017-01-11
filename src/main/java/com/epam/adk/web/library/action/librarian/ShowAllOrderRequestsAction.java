package com.epam.adk.web.library.action.librarian;

import static com.epam.adk.web.library.util.ConstantsHolder.REQUESTS_PAGE_NAME;

/**
 * ShowAllOrderRequestsAction class created on 06.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowAllOrderRequestsAction extends AbstractShowOrdersAction {

    private static final boolean CONSIDERED_STATUS = false;

    @Override
    protected String getPage() {
        return REQUESTS_PAGE_NAME;
    }

    @Override
    protected boolean getOrderStatus() {
        return CONSIDERED_STATUS;
    }

}

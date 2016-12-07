package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.service.OrderBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * DeleteOldOrderRequests class created on 07.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class DeleteOldOrderRequests implements Action {

    private static final Logger log = LoggerFactory.getLogger(DeleteOldOrderRequests.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The DeleteOldOrderRequests started execute.");

        OrderBookService orderBookService = new OrderBookService();

        try {
            orderBookService.deleteRejectedOrderRequests();
        } catch (ServiceException e) {
            throw new ActionException("Error: DeleteOldOrderRequests class. I can not to delete old order requests:", e);
        }
        return "redirect:rejected-orders";
    }
}

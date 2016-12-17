package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.exception.ActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RejectBookOrderAction class created on 07.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class RejectBookOrderAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(BookLendOutAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The RejectBookOrderAction started execute.");

        return null;
    }
}
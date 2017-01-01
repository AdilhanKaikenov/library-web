package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ShowAuthorizationErrorPageAction class created on 01.01.2017
 *
 * @author Kaikenov Adilhan
 **/
public class ShowAuthorizationErrorPageAction implements Action {

    public static final String AUTHORIZATION_ERROR_PAGE_NAME = "authorization-error";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return AUTHORIZATION_ERROR_PAGE_NAME;
    }
}

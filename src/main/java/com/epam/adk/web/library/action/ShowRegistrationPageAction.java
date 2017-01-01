package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ShowRegistrationPageAction class created on 01.01.2017
 *
 * @author Kaikenov Adilhan
 **/
public class ShowRegistrationPageAction implements Action {

    public static final String REGISTRATION_PAGE_NAME = "registration";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return REGISTRATION_PAGE_NAME;
    }
}

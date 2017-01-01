package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ShowSuccessRegistrationPageAction class created on 01.01.2017
 *
 * @author Kaikenov Adilhan
 **/
public class ShowSuccessRegistrationPageAction implements Action {

    public static final String SUCCESS_REGISTRATION_PAGE_NAME = "success-registration";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return SUCCESS_REGISTRATION_PAGE_NAME;
    }
}

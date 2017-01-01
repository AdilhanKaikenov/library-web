package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ShowProfileAction class created on 01.01.2017
 *
 * @author Kaikenov Adilhan
 **/
public class ShowProfileAction implements Action {

    public static final String PROFILE_PAGE_NAME = "profile";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return PROFILE_PAGE_NAME;
    }
}

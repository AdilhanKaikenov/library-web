package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ShowEditProfilePageAction class created on 01.01.2017
 *
 * @author Kaikenov Adilhan
 **/
public class ShowEditProfilePageAction implements Action {

    public static final String EDIT_PROFILE_PAGE_NAME = "edit-profile";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return EDIT_PROFILE_PAGE_NAME;
    }
}

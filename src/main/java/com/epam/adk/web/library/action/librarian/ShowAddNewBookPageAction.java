package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ShowAddNewBookPageAction class created on 01.01.2017
 *
 * @author Kaikenov Adilhan
 **/
public class ShowAddNewBookPageAction implements Action {

    private static final String ADD_NEW_BOOK_PAGE_NAME = "add-new-book";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return ADD_NEW_BOOK_PAGE_NAME;
    }
}

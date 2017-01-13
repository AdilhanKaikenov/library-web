package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ShowBookSearchResultPageAction class created on 01.01.2017
 *
 * @author Kaikenov Adilhan
 **/
public class ShowBookSearchResultPageAction implements Action {

    private static final String BOOK_SEARCH_RESULT_PAGE_NAME = "book-search-result";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        return BOOK_SEARCH_RESULT_PAGE_NAME;
    }
}

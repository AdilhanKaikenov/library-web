package com.epam.adk.web.library.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Displays page.
 *
 * ShowPageAction class created on 27.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class ShowPageAction implements Action {

    private static final String PAGE_PARAMETER = "page";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws RuntimeException {

        return request.getParameter(PAGE_PARAMETER);
    }
}

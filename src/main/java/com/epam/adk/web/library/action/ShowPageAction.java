package com.epam.adk.web.library.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Displays page.
 *
 * Created by Kaikenov Adilhan on 23.12.2015
 *
 * @author Kaikenov Adilhan on 23.12.2015
 */
public class ShowPageAction implements Action {

    public ShowPageAction() {
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws RuntimeException {

        return request.getParameter("page");
    }
}

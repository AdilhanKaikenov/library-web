package com.epam.adk.web.library.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String login = request.getParameter("authLogin");
        String password = request.getParameter("authPassword");

        return "redirect:welcome";
    }
}

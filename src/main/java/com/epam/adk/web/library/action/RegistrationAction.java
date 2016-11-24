package com.epam.adk.web.library.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");




        return "redirect:success-registration";
    }
}

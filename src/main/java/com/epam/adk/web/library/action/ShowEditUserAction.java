package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ShowEditUserAction class created on 09.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowEditUserAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {

        int userID = Integer.parseInt(request.getParameter("id"));

        UserService userService = new UserService();

        try {
            User user = userService.getUserById(userID);

            request.setAttribute("user", user);
        } catch (ServiceException e) {
            throw new ActionException("", e);
        }

        return "edit-user";
    }
}

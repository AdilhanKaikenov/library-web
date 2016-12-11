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

    private static final String EDIT_USER_PAGE_NAME = "edit-user";
    private static final String USER_REQUEST_ATTRIBUTE = "user";
    private static final String USER_ID_PARAMETER = "id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {

        int userID = Integer.parseInt(request.getParameter(USER_ID_PARAMETER));

        UserService userService = new UserService();

        try {
            User user = userService.getUserById(userID);

            request.setAttribute(USER_REQUEST_ATTRIBUTE, user);
        } catch (ServiceException e) {
            throw new ActionException("Error: ShowEditUserAction class. ", e);
        }

        return EDIT_USER_PAGE_NAME;
    }
}

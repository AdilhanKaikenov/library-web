package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.model.enums.Role;
import com.epam.adk.web.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * EditUserAction class created on 09.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class EditUserAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(EditUserAction.class);

    private static final String ROLE_PARAMETER = "role";
    private static final String USERS_PAGE_NAME = "users";
    private static final String STATUS_PARAMETER = "status";
    private static final String USER_ID_PARAMETER = "userID";
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The EditUserAction started execute.");

        int userID = Integer.parseInt(request.getParameter(USER_ID_PARAMETER));
        log.debug("User ID = {}", userID);
        Role role = Role.valueOf(request.getParameter(ROLE_PARAMETER));
        log.debug("User role = {}", role.getValue());
        boolean status = Boolean.valueOf(request.getParameter(STATUS_PARAMETER));
        log.debug("User status = {}", status);

        UserService userService = new UserService();

        try {
            User user = userService.getUserById(userID);
            user.setRole(role);
            user.setStatus(status);

            userService.updateUserData(user);

        } catch (ServiceException e) {
            throw new ActionException("Error: EditUserAction class. ", e);
        }

        return REDIRECT_PREFIX + USERS_PAGE_NAME;
    }
}


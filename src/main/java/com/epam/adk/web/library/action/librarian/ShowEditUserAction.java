package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.adk.web.library.util.ConstantsHolder.USER_ID_PARAMETER;

/**
 * ShowEditUserAction class created on 09.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowEditUserAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ShowEditUserAction.class);

    private static final String EDIT_USER_PAGE_NAME = "edit-user";
    private static final String USER_REQUEST_ATTRIBUTE = "user";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The ShowEditUserAction started execute.");

        int userID = Integer.parseInt(request.getParameter(USER_ID_PARAMETER));
        log.debug("User ID = {}", userID);

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

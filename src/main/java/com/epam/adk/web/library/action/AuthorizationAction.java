package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.FormValidationException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.service.UserService;
import com.epam.adk.web.library.util.MD5;
import com.epam.adk.web.library.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.epam.adk.web.library.util.ConstantsHolder.*;

/**
 * AuthorizationAction class created on 28.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class AuthorizationAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationAction.class);

    private static final String AUTHORIZATION_FORM_NAME = "authorization";
    private static final String AUTHORIZATION_ERROR_PAGE = "authorization-error";
    private static final String AUTH_ERROR_STORED_MESSAGE = "auth.error";
    private static final String AUTH_ERROR_STORED_MESSAGE_ONE = "auth.error.message.one";
    private static final String INACTIVE_STATUS_REQUEST_ATTRIBUTE = "inactiveStatus";
    private static final String USER_PROFILE_INACTIVE_STORED_MESSAGE = "user.profile.inactive";
    private static final String AUTHORIZATION_ERROR_REQUEST_ATTRIBUTE = "authorizationError";
    private static final String AUTHORIZATION_FORM_INCORRECT_REQUEST_ATTRIBUTE = "authorizationFormIncorrect";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The AuthorizationAction started execute.");

        HttpSession session = request.getSession();

        String login = request.getParameter(LOGIN_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        log.debug("User login = {}", login);
        log.debug("Request parameters valid.");

        if (isFormInvalid(request)) return AUTHORIZATION_ERROR_PAGE;

        User user = new User();
        user.setLogin(login);
        user.setPassword(MD5.get(password));

        UserService userService = new UserService();

        try {
            user = userService.authorize(user);
            if (!user.isStatus()) {
                request.setAttribute(INACTIVE_STATUS_REQUEST_ATTRIBUTE, USER_PROFILE_INACTIVE_STORED_MESSAGE);
                return AUTHORIZATION_ERROR_PAGE;
            }
            log.debug("User '{}' successfully authorized", user.getLogin());
        } catch (ServiceException e) {
            request.setAttribute(AUTHORIZATION_ERROR_REQUEST_ATTRIBUTE, AUTH_ERROR_STORED_MESSAGE);
            return AUTHORIZATION_ERROR_PAGE;
        }

        session.setAttribute(USER_PARAMETER, user);

        return REDIRECT_PREFIX + WELCOME_PAGE;
    }

    private boolean isFormInvalid(HttpServletRequest request) throws ActionException {
        try {
            FormValidator formValidator = new FormValidator();
            boolean isInvalid = formValidator.isInvalid(AUTHORIZATION_FORM_NAME, request);
            log.debug("Authorization form validation, invalid = {}", isInvalid);
            if (isInvalid) {
                request.setAttribute(AUTHORIZATION_FORM_INCORRECT_REQUEST_ATTRIBUTE, AUTH_ERROR_STORED_MESSAGE_ONE);
                return true;
            }
        } catch (FormValidationException e) {
            throw new ActionException("Error: AuthorizationAction class. Validation failed:", e);
        }
        return false;
    }
}

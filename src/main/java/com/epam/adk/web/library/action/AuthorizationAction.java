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

/**
 * AuthorizationAction class created on 28.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class AuthorizationAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationAction.class);

    private static final String USER = "user";
    private static final String AUTH_LOGIN_PARAMETER = "login";
    private static final String REDIRECT_WELCOME_PAGE = "redirect:welcome";
    private static final String AUTH_PASSWORD_PARAMETER = "password";
    private static final String AUTHORIZATION_ERROR_PAGE = "authorization-error";
    private static final String INACTIVE_STATUS_REQUEST_ATTRIBUTE = "inactiveStatus";
    private static final String AUTHORIZATION_ERROR_REQUEST_ATTRIBUTE = "authorizationError";
    private static final String AUTHORIZATION_FORM_INCORRECT_REQUEST_ATTRIBUTE = "authorizationFormIncorrect";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The AuthorizationAction started execute.");

        HttpSession session = request.getSession();

        String login = request.getParameter(AUTH_LOGIN_PARAMETER);
        String password = request.getParameter(AUTH_PASSWORD_PARAMETER);
        log.debug("User login = {}", login);

        try {
            FormValidator formValidator = new FormValidator();
            boolean isInvalid = formValidator.isInvalid("authorization", request);
            log.debug("Authorization form validation, invalid = {}", isInvalid);
            if (isInvalid) {
                request.setAttribute(AUTHORIZATION_FORM_INCORRECT_REQUEST_ATTRIBUTE, "auth.error.message.one");
                return AUTHORIZATION_ERROR_PAGE;
            }
        } catch (FormValidationException e) {
            throw new ActionException("Error: AuthorizationAction class. Validation failed:", e);
        }

        User user = new User();
        user.setLogin(login);
        user.setPassword(MD5.get(password));

        UserService userService = new UserService();

        try {
            user = userService.authorize(user);
            if (!user.isStatus()) {
                request.setAttribute(INACTIVE_STATUS_REQUEST_ATTRIBUTE, "user.profile.inactive");
                return AUTHORIZATION_ERROR_PAGE;
            }
            log.debug("User '{}' successfully authorized", user.getLogin());
        } catch (ServiceException e) {
            request.setAttribute(AUTHORIZATION_ERROR_REQUEST_ATTRIBUTE, "auth.error");
            return AUTHORIZATION_ERROR_PAGE;
        }

        session.setAttribute(USER, user);

        return REDIRECT_WELCOME_PAGE;
    }
}

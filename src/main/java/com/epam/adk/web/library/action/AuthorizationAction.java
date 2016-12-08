package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.service.UserService;
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
    private static final String AUTH_LOGIN_PARAMETER = "authLogin";
    private static final String AUTH_PASSWORD_PARAMETER = "authPassword";
    private static final String AUTHORIZATION_ERROR_PAGE = "authorization-error";
    private static final String REDIRECT_WELCOME_PAGE = "redirect:welcome";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The AuthorizationAction started execute.");

        HttpSession session = request.getSession();

        String login = request.getParameter(AUTH_LOGIN_PARAMETER);
        String password = request.getParameter(AUTH_PASSWORD_PARAMETER);
        log.debug("AuthorizationAction class: login from request = {}", login);

        FormValidator formValidator = new FormValidator();
        boolean isFormInvalid = formValidator.isAuthorizationFormInvalid(request);
        log.debug("Authorization form validation, invalid = {}", isFormInvalid);
        if (isFormInvalid) {
            request.setAttribute("authorizationFormIncorrect", "auth.error.message.one");
            return AUTHORIZATION_ERROR_PAGE;
        }

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);

        UserService userService = new UserService();

        try {
            user = userService.authorize(user);
            if (!user.isStatus()) {
                request.setAttribute("inactiveStatus", "user.profile.inactive");
                return AUTHORIZATION_ERROR_PAGE;
            }
            log.debug("User '{}' successfully authorized", user.getLogin());
        } catch (ServiceException e) {
            request.setAttribute("authorizationError", "auth.error");
            return AUTHORIZATION_ERROR_PAGE;
        }

        session.setAttribute(USER, user);

        return REDIRECT_WELCOME_PAGE;
    }
}

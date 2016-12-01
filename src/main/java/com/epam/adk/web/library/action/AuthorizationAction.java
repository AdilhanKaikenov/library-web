package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.service.UserService;
import com.epam.adk.web.library.validator.AuthorizationFormValidator;
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

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The action of authorization started execute.");

        HttpSession session = request.getSession();

        String login = request.getParameter("authLogin");
        String password = request.getParameter("authPassword");

        AuthorizationFormValidator formValidator = new AuthorizationFormValidator();
        boolean isFormInvalid = formValidator.isInvalid(request);
        log.debug("Authorization form validation, invalid = {}", isFormInvalid);
        if (isFormInvalid) {
            request.setAttribute("authorizationFormIncorrect", "auth.error.message.one");
            return "authorization-error";
        }

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);

        UserService userService = new UserService();

        try {
            user = userService.authorizeUser(user);
            if (user == null) {
                request.setAttribute("authorizationError", "auth.error");
                return "authorization-error";
            }
            log.debug("User '{}' successfully authorized", user.getLogin());
        } catch (ServiceException e) {
            throw new ActionException("Error: AuthorizationAction class. Can not authorize User:", e);
        }

        session.setAttribute(USER, user);

        return "redirect:welcome";
    }
}

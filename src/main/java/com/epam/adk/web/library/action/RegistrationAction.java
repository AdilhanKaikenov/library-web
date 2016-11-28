package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.model.enums.Gender;
import com.epam.adk.web.library.model.enums.Role;
import com.epam.adk.web.library.service.UserService;
import com.epam.adk.web.library.validator.RegistrationFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RegistrationAction class created on 27.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class RegistrationAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(RegistrationAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("The action of registration started execute.");

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String surname = request.getParameter("surname");
        String patronymic = request.getParameter("patronymic");
        Gender gender = Gender.from(request.getParameter("gender"));
        String address = request.getParameter("address");
        String mobilePhone = request.getParameter("mobilePhone");

        RegistrationFormValidator formValidator = new RegistrationFormValidator();
        boolean isFormInvalid = formValidator.isInvalid(request);
        log.debug("Registration form validation = {}", isFormInvalid);
        if (isFormInvalid) {
            return "registration";
        }

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstname(firstname);
        user.setSurname(surname);
        user.setPatronymic(patronymic);
        user.setGender(gender);
        user.setRole(Role.USER);
        user.setAddress(address);
        user.setMobilePhone(mobilePhone);
        user.setStatus(true);

        UserService userService = new UserService();

        User registeredUser;
        try {
            registeredUser = userService.registerUser(user);
        } catch (ServiceException e) {
            request.setAttribute("userExist", "user.exist.message");
            return "registration";
        }
        log.debug("New User successfully registered User: id = {}, login = {}", registeredUser.getId(), registeredUser.getLogin());
        return "redirect:success-registration";
    }
}

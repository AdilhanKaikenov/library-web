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
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String EMAIL_PARAMETER = "email";
    private static final String FIRSTNAME_PARAMETER = "firstname";
    private static final String SURNAME_PARAMETER = "surname";
    private static final String PATRONYMIC_PARAMETER = "patronymic";
    private static final String GENDER_PARAMETER = "gender";
    private static final String ADDRESS_PARAMETER = "address";
    private static final String MOBILE_PHONE_PARAMETER = "mobilePhone";
    private static final String REGISTRATION_PAGE_NAME = "registration";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        log.debug("The action of registration started execute.");

        String login = request.getParameter(LOGIN_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        String email = request.getParameter(EMAIL_PARAMETER);
        String firstname = request.getParameter(FIRSTNAME_PARAMETER);
        String surname = request.getParameter(SURNAME_PARAMETER);
        String patronymic = request.getParameter(PATRONYMIC_PARAMETER);
        Gender gender = Gender.from(request.getParameter(GENDER_PARAMETER));
        String address = request.getParameter(ADDRESS_PARAMETER);
        String mobilePhone = request.getParameter(MOBILE_PHONE_PARAMETER);

        RegistrationFormValidator formValidator = new RegistrationFormValidator();
        boolean isFormInvalid = formValidator.isInvalid(request);
        log.debug("Registration form validation, invalid= {}", isFormInvalid);
        if (isFormInvalid) {
            return REGISTRATION_PAGE_NAME;
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
            registeredUser = userService.register(user);
        } catch (ServiceException e) {
            log.error("Error: RegistrationAction class, Can not register new user: {}", e);
            request.setAttribute("userExist", "user.exist.message");
            return REGISTRATION_PAGE_NAME;
        }
        log.debug("New User successfully registered User: id = {}, login = {}", registeredUser.getId(), registeredUser.getLogin());
        return "redirect:success-registration";
    }
}

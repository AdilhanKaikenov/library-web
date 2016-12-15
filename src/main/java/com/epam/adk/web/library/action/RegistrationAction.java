package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.FormValidationException;
import com.epam.adk.web.library.exception.PropertyManagerException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.model.enums.Gender;
import com.epam.adk.web.library.model.enums.Role;
import com.epam.adk.web.library.service.UserService;
import com.epam.adk.web.library.util.MD5;
import com.epam.adk.web.library.validator.FormValidator;
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
    private static final String EMAIL_PARAMETER = "email";
    private static final String GENDER_PARAMETER = "gender";
    private static final String SURNAME_PARAMETER = "surname";
    private static final String ADDRESS_PARAMETER = "address";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String FIRSTNAME_PARAMETER = "firstname";
    private static final String PATRONYMIC_PARAMETER = "patronymic";
    private static final String MOBILE_PHONE_PARAMETER = "mobile_phone";
    private static final String REGISTRATION_PAGE_NAME = "registration";
    private static final String USER_EXIST_REQUEST_ATTRIBUTE = "userExist";
    private static final String REDIRECT_SUCCESS_REGISTRATION_PAGE = "redirect:success-registration";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The RegistrationAction started execute.");

        String login = request.getParameter(LOGIN_PARAMETER);
        log.debug("Login: {}", login);
        String password = request.getParameter(PASSWORD_PARAMETER);
        log.debug("Password: {}", password);
        String email = request.getParameter(EMAIL_PARAMETER);
        log.debug("Email: {}", email);
        String firstname = request.getParameter(FIRSTNAME_PARAMETER);
        log.debug("Firstname: {}", firstname);
        String surname = request.getParameter(SURNAME_PARAMETER);
        log.debug("Surname: {}", surname);
        String patronymic = request.getParameter(PATRONYMIC_PARAMETER);
        log.debug("Patronymic: {}", patronymic);
        Gender gender = Gender.from(request.getParameter(GENDER_PARAMETER));
        log.debug("Gender: {}", gender);
        String address = request.getParameter(ADDRESS_PARAMETER);
        log.debug("Address: {}", address);
        String mobilePhone = request.getParameter(MOBILE_PHONE_PARAMETER);
        log.debug("Mobile Phone: {}", mobilePhone);

        try {
            FormValidator validator = new FormValidator();
            boolean isInvalid = validator.isInvalid(REGISTRATION_PAGE_NAME, request);
            log.debug("Registration form validation, invalid = {}", isInvalid);
            if (isInvalid){
                return REGISTRATION_PAGE_NAME;
            }

        } catch (PropertyManagerException | FormValidationException e) {
            throw new ActionException("Error: RegistrationAction class. Validation failed:", e);
        }

        User user = new User();
        user.setLogin(login);
        user.setPassword(MD5.get(password));
        user.setEmail(email);
        user.setFirstname(firstname);
        user.setSurname(surname);
        user.setPatronymic(patronymic);
        user.setGender(gender);
        user.setRole(Role.READER);
        user.setAddress(address);
        user.setMobilePhone(mobilePhone);
        user.setStatus(true);

        UserService userService = new UserService();

        User registeredUser;
        try {
            registeredUser = userService.register(user);
            log.debug("New User successfully registered User: id = {}, login = {}", registeredUser.getId(), registeredUser.getLogin());
        } catch (ServiceException e) {
            log.error("Error: RegistrationAction class, Can not register new user: {}", e);
            request.setAttribute(USER_EXIST_REQUEST_ATTRIBUTE, "user.exist.message");
            return REGISTRATION_PAGE_NAME;
        }
        return REDIRECT_SUCCESS_REGISTRATION_PAGE;
    }
}

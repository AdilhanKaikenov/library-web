package com.epam.adk.web.library.validator;

import com.epam.adk.web.library.propmanager.PropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * RegistrationFormValidator class created on 26.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class RegistrationFormValidator {

    private static final Logger log = LoggerFactory.getLogger(RegistrationFormValidator.class);
    private static final Map<String, String> validatorProperties = PropertiesManager.getInstance().getPropertyValues("validator.properties");

    private static final String CORRECT_FORM = validatorProperties.get("correct.form");
    private static final String LATIN_FORM = validatorProperties.get("latin.form");
    private static final String EMAIL_FORM = validatorProperties.get("email.form");
    private static final String DIGIT_FORM = validatorProperties.get("digit.form");
    private static final String ADDRESS_FORM = validatorProperties.get("address.form");
    private static final int LOGIN_MIN_LENGTH = 5;
    private static final int LOGIN_MAX_LENGTH = 10;
    private static final int PASSWORD_MIN_LENGTH = 5;
    private static final int PASSWORD_MAX_LENGTH = 15;
    private static final int MOB_PHONE_LENGTH = 11;
    private static final int FIELD_MIN_LENGTH = 2;
    private static final int FIELD_MAX_LENGTH = 20;

    private Validator validator = new Validator();

    public boolean isInvalid(HttpServletRequest request) {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String surname = request.getParameter("surname");
        String patronymic = request.getParameter("patronymic");
        String address = request.getParameter("address");
        String mobilePhone = request.getParameter("mobilePhone");

        boolean loginRegexValid = validator.isRegexValid(login, CORRECT_FORM);
        boolean loginLengthValid = validator.isLengthValid(login, LOGIN_MIN_LENGTH, LOGIN_MAX_LENGTH);

        boolean passwordRegexValid = validator.isRegexValid(password, CORRECT_FORM);
        boolean passwordLengthValid = validator.isLengthValid(password, PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        boolean emailRegexValid = validator.isRegexValid(email, EMAIL_FORM);

        boolean fullNameRegexValid = validator.isRegexValid(firstname, LATIN_FORM) &&
                validator.isRegexValid(surname, LATIN_FORM) &&
                validator.isRegexValid(patronymic, LATIN_FORM);

        boolean fullNameLengthValid = validator.isLengthValid(firstname, FIELD_MIN_LENGTH, FIELD_MAX_LENGTH) &&
                validator.isLengthValid(surname, FIELD_MIN_LENGTH, FIELD_MAX_LENGTH) &&
                validator.isLengthValid(patronymic, FIELD_MIN_LENGTH, FIELD_MAX_LENGTH);

        boolean addressRegexValid = validator.isRegexValid(address, ADDRESS_FORM);
        boolean addressLengthValid = validator.isLengthValid(address, FIELD_MIN_LENGTH, FIELD_MAX_LENGTH);

        boolean mobilePhoneValid = validator.isRegexValid(mobilePhone, DIGIT_FORM) &&
                validator.isLengthValid(mobilePhone, MOB_PHONE_LENGTH, MOB_PHONE_LENGTH);

        if (!loginRegexValid) {
            request.setAttribute("loginFormError", "login.form.incorrect");
        }
        if (!loginLengthValid) {
            request.setAttribute("loginLengthError", "login.length.incorrect");
        }
        if (!passwordRegexValid) {
            request.setAttribute("passwordFormError", "password.form.incorrect");
        }
        if (!passwordLengthValid) {
            request.setAttribute("passwordLengthError", "password.length.incorrect");
        }
        if (!emailRegexValid) {
            request.setAttribute("emailFormError", "email.form.incorrect");
        }
        if (!fullNameRegexValid) {
            request.setAttribute("fullNameFormError", "fullName.form.incorrect");
        }
        if (!fullNameLengthValid) {
            request.setAttribute("fullNameLengthError", "fullName.length.incorrect");
        }
        if (!addressRegexValid) {
            request.setAttribute("addressFormError", "address.form.incorrect");
        }
        if (!addressLengthValid) {
            request.setAttribute("addressLengthError", "address.length.incorrect");
        }
        if (!mobilePhoneValid) {
            request.setAttribute("mobilePhoneFormError", "mobileNumber.form.incorrect");
        }

        return !(loginRegexValid && loginLengthValid &&
                passwordRegexValid && passwordLengthValid && emailRegexValid &&
                fullNameRegexValid && fullNameLengthValid &&
                addressRegexValid && addressLengthValid && mobilePhoneValid
        );
    }
}

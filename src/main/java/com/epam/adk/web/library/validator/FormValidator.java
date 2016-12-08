package com.epam.adk.web.library.validator;

import com.epam.adk.web.library.propmanager.PropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * FormValidator class created on 30.11.2016
 *
 * @author Kaikenov Adilhan
 **/
public class FormValidator {

    private static final Logger log = LoggerFactory.getLogger(FormValidator.class);

    private static final Map<String, String> PROPERTIES = PropertiesManager.getInstance().getPropertiesAsMap("validator.properties");

    private static final String CORRECT_FORM = PROPERTIES.get("correct.form");
    private static final String LATIN_FORM = PROPERTIES.get("latin.form");
    private static final String EMAIL_FORM = PROPERTIES.get("email.form");
    private static final String DIGIT_FORM = PROPERTIES.get("digit.form");
    private static final String ADDRESS_FORM = PROPERTIES.get("address.form");

    private static final int LOGIN_MIN_LENGTH = Integer.parseInt(PROPERTIES.get("login.min.length"));
    private static final int LOGIN_MAX_LENGTH = Integer.parseInt(PROPERTIES.get("login.max.length"));
    private static final int PASSWORD_MIN_LENGTH = Integer.parseInt(PROPERTIES.get("password.min.length"));
    private static final int PASSWORD_MAX_LENGTH = Integer.parseInt(PROPERTIES.get("password.max.length"));
    private static final int MOB_PHONE_LENGTH = Integer.parseInt(PROPERTIES.get("mob.phone.length"));
    private static final int FIELD_MIN_LENGTH = Integer.parseInt(PROPERTIES.get("field.min.length"));
    private static final int FIELD_MAX_LENGTH = Integer.parseInt(PROPERTIES.get("field.max.length"));

    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String EMAIL_PARAMETER = "email";
    private static final String FIRSTNAME_PARAMETER = "firstname";
    private static final String SURNAME__PARAMETER = "surname";
    private static final String PATRONYMIC_PARAMETER = "patronymic";
    private static final String ADDRESS_PARAMETER = "address";
    private static final String MOBILE_PHONE_PARAMETER = "mobilePhone";
    private static final String AUTH_LOGIN_PARAMETER = "authLogin";
    private static final String AUTH_PASSWORD_PARAMETER = "authPassword";

    private static final String LOGIN_FORM_INCORRECT_ATTRIBUTE_NAME = "loginFormIncorrect";
    private static final String LOGIN_LENGTH_INCORRECT_ATTRIBUTE_NAME = "loginLengthIncorrect";
    private static final String PASSWORD_FORM_INCORRECT_ATTRIBUTE_NAME = "passwordFormIncorrect";
    private static final String PASSWORD_LENGTH_INCORRECT_ATTRIBUTE_NAME = "passwordLengthIncorrect";
    private static final String EMAIL_FORM_INCORRECT_ATTRIBUTE_NAME = "emailFormIncorrect";
    private static final String FULL_NAME_FORM_INCORRECT_ATTRIBUTE_NAME = "fullNameFormIncorrect";
    private static final String FULL_NAME_LENGTH_INCORRECT_ATTRIBUTE_NAME = "fullNameLengthIncorrect";
    private static final String ADDRESS_FORM_INCORRECT_ATTRIBUTE_NAME = "addressFormIncorrect";
    private static final String ADDRESS_LENGTH_INCORRECT_ATTRIBUTE_NAME = "addressLengthIncorrect";
    private static final String MOBILE_PHONE_FORM_INCORRECT_ATTRIBUTE_NAME = "mobilePhoneFormIncorrect";

    private static final String LOGIN_FORM_INCORRECT_ATTRIBUTE_STORED_MESSAGE = "login.form.incorrect";
    private static final String LOGIN_LENGTH_INCORRECT_ATTRIBUTE_STORED_MESSAGE = "login.length.incorrect";
    private static final String PASSWORD_FORM_INCORRECT_ATTRIBUTE_STORED_MESSAGE = "password.form.incorrect";
    private static final String PASSWORD_LENGTH_INCORRECT_ATTRIBUTE_STORED_MESSAGE = "password.length.incorrect";
    private static final String EMAIL_FORM_INCORRECT_ATTRIBUTE_STORED_MESSAGE = "email.form.incorrect";
    private static final String FULL_NAME_FORM_INCORRECT_ATTRIBUTE_STORED_MESSAGE = "fullName.form.incorrect";
    private static final String FULL_NAME_LENGTH_INCORRECT_ATTRIBUTE_STORED_MESSAGE = "fullName.length.incorrect";
    private static final String ADDRESS_FORM_INCORRECT_ATTRIBUTE_STORED_MESSAGE = "address.form.incorrect";
    private static final String ADDRESS_LENGTH_INCORRECT_ATTRIBUTE_STORED_MESSAGE = "address.length.incorrect";
    private static final String MOBILE_NUMBER_FORM_INCORRECT_ATTRIBUTE_STORED_MESSAGE = "mobileNumber.form.incorrect";

    private Validator validator = new Validator();

    public boolean isRegistrationFormInvalid(HttpServletRequest request) {
        log.debug("FormValidator class, isRegistrationFormInvalid() method started execute.");
        String login = request.getParameter(LOGIN_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        boolean loginValid = isLoginValid(request, login);
        boolean passwordValid = isPasswordValid(request, password);
        boolean emailValid = isEmailValid(request);
        boolean fullNameValid = isFullNameValid(request);
        boolean mobilePhoneValid = isMobilePhoneValid(request);
        boolean addressValid = isAddressValid(request);
        log.debug("Leaving FormValidator class, isRegistrationFormInvalid() method.");
        return !(loginValid && passwordValid && emailValid && fullNameValid && mobilePhoneValid && addressValid);
    }

    public boolean isAuthorizationFormInvalid(HttpServletRequest request) {
        log.debug("FormValidator class, isAuthorizationFormInvalid() method started execute.");
        String login = request.getParameter(AUTH_LOGIN_PARAMETER);
        String password = request.getParameter(AUTH_PASSWORD_PARAMETER);
        if (login == null || password == null) {
            log.debug("Login or password = NULL");
            return true;
        }
        boolean loginValid = isLoginValid(request, login);
        boolean passwordValid = isPasswordValid(request, password);
        log.debug("Leaving FormValidator class, isAuthorizationFormInvalid() method.");
        return !(loginValid && passwordValid);
    }

    public boolean isEditProfileFormInvalid(HttpServletRequest request) {
        log.debug("FormValidator class, isEditProfileFormInvalid() method started execute.");
        String password = request.getParameter(PASSWORD_PARAMETER);
        boolean passwordValid = isPasswordValid(request, password);
        boolean emailValid = isEmailValid(request);
        boolean mobilePhoneValid = isMobilePhoneValid(request);
        boolean addressValid = isAddressValid(request);
        log.debug("Leaving FormValidator class, isEditProfileFormInvalid() method.");
        return !(passwordValid && emailValid && mobilePhoneValid && addressValid);
    }

    private boolean isLoginValid(HttpServletRequest request, String login) {
        log.debug("FormValidator class, isLoginValid() method started execute.");
        boolean loginRegexValid = validator.isRegexValid(login, CORRECT_FORM);
        boolean loginLengthValid = validator.isLengthValid(login, LOGIN_MIN_LENGTH, LOGIN_MAX_LENGTH);
        if (!loginRegexValid) {
            request.setAttribute(LOGIN_FORM_INCORRECT_ATTRIBUTE_NAME, LOGIN_FORM_INCORRECT_ATTRIBUTE_STORED_MESSAGE);
        }
        if (!loginLengthValid) {
            request.setAttribute(LOGIN_LENGTH_INCORRECT_ATTRIBUTE_NAME, LOGIN_LENGTH_INCORRECT_ATTRIBUTE_STORED_MESSAGE);
        }
        boolean result = loginRegexValid && loginLengthValid;
        log.debug("Leaving FormValidator class, isLoginValid() method. Is login valid = {}", result);
        return result;
    }

    private boolean isPasswordValid(HttpServletRequest request, String password) {
        log.debug("FormValidator class, isPasswordValid() method started execute.");
        boolean passwordRegexValid = validator.isRegexValid(password, CORRECT_FORM);
        boolean passwordLengthValid = validator.isLengthValid(password, PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
        if (!passwordRegexValid) {
            request.setAttribute(PASSWORD_FORM_INCORRECT_ATTRIBUTE_NAME, PASSWORD_FORM_INCORRECT_ATTRIBUTE_STORED_MESSAGE);
        }
        if (!passwordLengthValid) {
            request.setAttribute(PASSWORD_LENGTH_INCORRECT_ATTRIBUTE_NAME, PASSWORD_LENGTH_INCORRECT_ATTRIBUTE_STORED_MESSAGE);
        }
        boolean result = passwordRegexValid && passwordLengthValid;
        log.debug("Leaving FormValidator class, isPasswordValid() method. Is password valid = {}", result);
        return result;
    }

    private boolean isEmailValid(HttpServletRequest request) {
        log.debug("FormValidator class, isEmailValid() method started execute.");
        String email = request.getParameter(EMAIL_PARAMETER);
        boolean emailRegexValid = validator.isRegexValid(email, EMAIL_FORM);
        if (!emailRegexValid) {
            request.setAttribute(EMAIL_FORM_INCORRECT_ATTRIBUTE_NAME, EMAIL_FORM_INCORRECT_ATTRIBUTE_STORED_MESSAGE);
        }
        log.debug("Leaving FormValidator class, isEmailValid() method. Is email valid = {}", emailRegexValid);
        return emailRegexValid;
    }

    private boolean isFullNameValid(HttpServletRequest request) {
        log.debug("FormValidator class, isFullNameValid() method started execute.");
        String firstname = request.getParameter(FIRSTNAME_PARAMETER);
        String surname = request.getParameter(SURNAME__PARAMETER);
        String patronymic = request.getParameter(PATRONYMIC_PARAMETER);
        boolean fullNameRegexValid = validator.isRegexValid(firstname, LATIN_FORM) &&
                validator.isRegexValid(surname, LATIN_FORM) &&
                validator.isRegexValid(patronymic, LATIN_FORM);

        boolean fullNameLengthValid = validator.isLengthValid(firstname, FIELD_MIN_LENGTH, FIELD_MAX_LENGTH) &&
                validator.isLengthValid(surname, FIELD_MIN_LENGTH, FIELD_MAX_LENGTH) &&
                validator.isLengthValid(patronymic, FIELD_MIN_LENGTH, FIELD_MAX_LENGTH);

        if (!fullNameRegexValid) {
            request.setAttribute(FULL_NAME_FORM_INCORRECT_ATTRIBUTE_NAME, FULL_NAME_FORM_INCORRECT_ATTRIBUTE_STORED_MESSAGE);
        }
        if (!fullNameLengthValid) {
            request.setAttribute(FULL_NAME_LENGTH_INCORRECT_ATTRIBUTE_NAME, FULL_NAME_LENGTH_INCORRECT_ATTRIBUTE_STORED_MESSAGE);
        }
        boolean result = fullNameRegexValid && fullNameLengthValid;
        log.debug("Leaving FormValidator class, isFullNameValid() method. Is full name valid = {}", result);
        return fullNameRegexValid && fullNameLengthValid;
    }

    private boolean isMobilePhoneValid(HttpServletRequest request) {
        log.debug("FormValidator class, isMobilePhoneValid() method started execute.");
        String mobilePhone = request.getParameter(MOBILE_PHONE_PARAMETER);
        boolean mobilePhoneValid = validator.isRegexValid(mobilePhone, DIGIT_FORM) &&
                validator.isLengthValid(mobilePhone, MOB_PHONE_LENGTH, MOB_PHONE_LENGTH);
        if (!mobilePhoneValid) {
            request.setAttribute(MOBILE_PHONE_FORM_INCORRECT_ATTRIBUTE_NAME, MOBILE_NUMBER_FORM_INCORRECT_ATTRIBUTE_STORED_MESSAGE);
        }
        log.debug("Leaving FormValidator class, isMobilePhoneValid() method. Is mobile phone valid = {}", mobilePhoneValid);
        return mobilePhoneValid;
    }

    private boolean isAddressValid(HttpServletRequest request) {
        log.debug("FormValidator class, isAddressValid() method started execute.");
        String address = request.getParameter(ADDRESS_PARAMETER);

        boolean addressRegexValid = validator.isRegexValid(address, ADDRESS_FORM);
        if (!addressRegexValid) {
            request.setAttribute(ADDRESS_FORM_INCORRECT_ATTRIBUTE_NAME, ADDRESS_FORM_INCORRECT_ATTRIBUTE_STORED_MESSAGE);
        }
        boolean addressLengthValid = validator.isLengthValid(address, FIELD_MIN_LENGTH, FIELD_MAX_LENGTH);
        if (!addressLengthValid) {
            request.setAttribute(ADDRESS_LENGTH_INCORRECT_ATTRIBUTE_NAME, ADDRESS_LENGTH_INCORRECT_ATTRIBUTE_STORED_MESSAGE);
        }
        boolean result = addressRegexValid && addressLengthValid;
        log.debug("Leaving FormValidator class, isAddressValid() method. Is address valid = {}", result);
        return result;
    }
}

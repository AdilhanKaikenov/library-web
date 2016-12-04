package com.epam.adk.web.library.validator;

import com.epam.adk.web.library.propmanager.PropertiesManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * RegistrationFormValidator class created on 26.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class RegistrationFormValidator implements FormValidation {

    private static final Map<String, String> PROPERTIES = PropertiesManager.getInstance().getPropertiesAsMap("validator.properties");

    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String EMAIL_PARAMETER = "email";
    private static final String FIRSTNAME_PARAMETER = "firstname";
    private static final String SURNAME__PARAMETER = "surname";
    private static final String PATRONYMIC_PARAMETER = "patronymic";
    private static final String ADDRESS_PARAMETER = "address";
    private static final String MOBILE_PHONE_PARAMETER = "mobilePhone";
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

    private Validator validator = new Validator();

    public boolean isInvalid(HttpServletRequest request) {
        String login = request.getParameter(LOGIN_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        String email = request.getParameter(EMAIL_PARAMETER);
        String firstname = request.getParameter(FIRSTNAME_PARAMETER);
        String surname = request.getParameter(SURNAME__PARAMETER);
        String patronymic = request.getParameter(PATRONYMIC_PARAMETER);
        String address = request.getParameter(ADDRESS_PARAMETER);
        String mobilePhone = request.getParameter(MOBILE_PHONE_PARAMETER);

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
            request.setAttribute("loginFormIncorrect", "login.form.incorrect");
        }
        if (!loginLengthValid) {
            request.setAttribute("loginLengthIncorrect", "login.length.incorrect");
        }
        if (!passwordRegexValid) {
            request.setAttribute("passwordFormIncorrect", "password.form.incorrect");
        }
        if (!passwordLengthValid) {
            request.setAttribute("passwordLengthIncorrect", "password.length.incorrect");
        }
        if (!emailRegexValid) {
            request.setAttribute("emailFormIncorrect", "email.form.incorrect");
        }
        if (!fullNameRegexValid) {
            request.setAttribute("fullNameFormIncorrect", "fullName.form.incorrect");
        }
        if (!fullNameLengthValid) {
            request.setAttribute("fullNameLengthIncorrect", "fullName.length.incorrect");
        }
        if (!addressRegexValid) {
            request.setAttribute("addressFormIncorrect", "address.form.incorrect");
        }
        if (!addressLengthValid) {
            request.setAttribute("addressLengthIncorrect", "address.length.incorrect");
        }
        if (!mobilePhoneValid) {
            request.setAttribute("mobilePhoneFormIncorrect", "mobileNumber.form.incorrect");
        }

        return !(loginRegexValid && loginLengthValid &&
                passwordRegexValid && passwordLengthValid && emailRegexValid &&
                fullNameRegexValid && fullNameLengthValid &&
                addressRegexValid && addressLengthValid && mobilePhoneValid
        );
    }
}

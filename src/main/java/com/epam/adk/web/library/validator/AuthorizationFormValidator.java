package com.epam.adk.web.library.validator;

import com.epam.adk.web.library.propmanager.PropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * AuthorizationFormValidator class created on 30.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class AuthorizationFormValidator implements FormValidation {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationFormValidator.class);

    private static final Map<String, String> PROPERTIES = PropertiesManager.getInstance().getPropertiesAsMap("validator.properties");
    private static final String CORRECT_FORM = PROPERTIES.get("correct.form");
    private static final int LOGIN_MIN_LENGTH = Integer.parseInt(PROPERTIES.get("login.min.length"));
    private static final int LOGIN_MAX_LENGTH = Integer.parseInt(PROPERTIES.get("login.max.length"));
    private static final int PASSWORD_MIN_LENGTH = Integer.parseInt(PROPERTIES.get("password.min.length"));
    private static final int PASSWORD_MAX_LENGTH = Integer.parseInt(PROPERTIES.get("password.max.length"));

    private Validator validator = new Validator();

    public boolean isInvalid(HttpServletRequest request) {
        log.debug("AuthorizationFormValidator class, isInvalid() method started execute.");

        String login = request.getParameter("authLogin");
        String password = request.getParameter("authPassword");

        if (login == null || password == null){
            log.debug("Login or password = NULL");
            return true;
        }

        boolean loginRegexValid = validator.isRegexValid(login, CORRECT_FORM);
        boolean loginLengthValid = validator.isLengthValid(login, LOGIN_MIN_LENGTH, LOGIN_MAX_LENGTH);

        boolean passwordRegexValid = validator.isRegexValid(password, CORRECT_FORM);
        boolean passwordLengthValid = validator.isLengthValid(password, PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        log.debug("Leaving AuthorizationFormValidator class, isInvalid() method.");
        return !(loginRegexValid && loginLengthValid &&
                passwordRegexValid && passwordLengthValid
        );
    }
}

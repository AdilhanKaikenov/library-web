package com.epam.adk.web.library.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegexValidator class created on 15.12.2016
 *
 * @author Kaikenov Adilhan
 * @see Validator
 **/
public class RegexValidator extends AbstractValidator {

    private Pattern pattern;

    public RegexValidator() {
    }

    public void setRegex(String regex) {
        pattern = Pattern.compile(regex);
    }

    public boolean isValid(String field){
        Matcher matcher = pattern.matcher(field);
        return matcher.matches();
    }
}

package com.epam.adk.web.library.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator class created on 26.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class Validator {

    private Pattern pattern;

    /**
     * Method for checking the field using the regular expression.
     *
     * @param field
     * @param regex regex expression
     * @return (true) if the field is valid and (false) if the field is invalid.
     */
    public boolean isRegexValid(String field, String regex){
        pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(field);
        return matcher.matches();
    }

    /**
     * The method for checking length of the field.
     *
     * @param field
     * @param min minimal boundary (inclusive).
     * @param max maximal boundary (inclusive).
     * @return (true) if the field is valid and (false) if the field is invalid.
     */
    public boolean isLengthValid(String field, int min, int max){
        int length = field.length();
        return (length >= min) && (length <= max);
    }
}

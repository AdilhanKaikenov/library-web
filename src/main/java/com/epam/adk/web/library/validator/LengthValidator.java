package com.epam.adk.web.library.validator;

/**
 * LengthValidator class created on 15.12.2016
 *
 * @author Kaikenov Adilhan
 * @see Validator
 **/
public class LengthValidator extends AbstractValidator {

    private int minLength;
    private int maxLength;

    public LengthValidator() {
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public boolean isValid(String field){
        int length = field.length();
        return (length >= minLength) && (length <= maxLength);
    }
}

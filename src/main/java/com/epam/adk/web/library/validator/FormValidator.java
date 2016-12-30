package com.epam.adk.web.library.validator;

import com.epam.adk.web.library.exception.FormValidationException;
import com.epam.adk.web.library.exception.PropertyManagerException;
import com.epam.adk.web.library.exception.ValidatorConfigurationException;
import com.epam.adk.web.library.propmanager.PropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * FormValidator class created on 15.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class FormValidator {

    private static final Logger log = LoggerFactory.getLogger(FormValidator.class);

    private static final String PROPERTIES_FILE_NAME = "validator.properties";
    private static final String LENGTH_INCORRECT = "LengthIncorrect";
    private static final String INCORRECT = "Incorrect";
    private static final String DOT_REGEX = "\\.";
    private static final String DOT = ".";
    private static final String CLASS = "class";

    protected static Map<String, String> properties;

    public static void configure() throws ValidatorConfigurationException {
        if (properties == null) {
            try {
                PropertiesManager propertiesManager = new PropertiesManager(PROPERTIES_FILE_NAME);
                properties = propertiesManager.getPropertiesAsMap();
            } catch (PropertyManagerException e) {
                throw new ValidatorConfigurationException("Error: FormValidator class, configure() method.", e);
            }
        }
    }

    /**
     * The method for checking fields of form by validators described in Property File.
     *
     * @param formName the name of the form that a validator checks.
     * @param request HttpServletRequest.
     * @return (true) if a form is invalid and (false) if the form is valid.
     * @throws FormValidationException
     */
    public boolean isInvalid(String formName, HttpServletRequest request) throws FormValidationException {
        log.debug("Entering FormValidator class, isInvalid() method. Form is {}", formName);
        Map<String, List<Validator>> fieldsValidators = getFieldsValidators(formName, request);
        Set<Map.Entry<String, List<Validator>>> entries = fieldsValidators.entrySet();
        boolean result = true;
        for (Map.Entry<String, List<Validator>> entry : entries) {
            String key = entry.getKey();
            String parameter = request.getParameter(key);
            List<Validator> value = entry.getValue();
            for (Validator validator : value) {
                if (!validator.isValid(parameter)) {
                    result = false;
                    if (validator instanceof RegexValidator) {
                        log.debug("Incorrect regex: {}", key);
                        request.setAttribute(key + INCORRECT, validator.getMessage());
                    }
                    if (validator instanceof LengthValidator) {
                        log.debug("Incorrect length: {}", key);
                        request.setAttribute(key + LENGTH_INCORRECT, validator.getMessage());
                    }
                }
            }
        }
        log.debug("Leaving isInvalid method.");
        return !(result);
    }

    /**
     * The method returns a map containing all the fields and all its validators.
     *
     * @param formName the name of the form that a validator checks.
     * @param request HttpServletRequest.
     * @return Map: field and its list validators.
     * @throws FormValidationException
     */
    private Map<String, List<Validator>> getFieldsValidators(String formName, HttpServletRequest request) throws FormValidationException {
        log.debug("Entering getFieldsValidators() method.");
        Map<String, List<Validator>> result = new HashMap<>();
        List<Validator> validators;
        Enumeration<String> attributeNames = request.getParameterNames();
        while (attributeNames.hasMoreElements()) {
            String field = attributeNames.nextElement();
            validators = getValidators(formName, field);
            result.put(field, validators);
        }
        log.debug("Leaving getFieldsValidators() method.");
        return result;
    }

    /**
     * This method returns all the validators that are described for him in Property file.
     *
     * @param formName the name of the form that a validator checks.
     * @param fieldName field name of form.
     * @return List of field validators.
     * @throws FormValidationException
     */
    private List<Validator> getValidators(String formName, String fieldName) throws FormValidationException {
        log.debug("Entering getValidators() method. Form = {}, field of form  = {}", formName, fieldName);
        List<Validator> validators = new ArrayList<>();

        Set<Map.Entry<String, String>> entries = properties.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();

            String[] split = key.split(DOT_REGEX);

            String validatorNumber = split[split.length - 2];
            String validatorIdentifier = formName + DOT + fieldName + DOT + validatorNumber;

            if (key.contains(validatorIdentifier) && key.contains(CLASS)) {
                Validator validator = getValidator(validatorIdentifier, value);
                validators.add(validator);
            }
        }
        log.debug("Leaving getValidators() method. Field {} validators size = {}", fieldName, validators.size());
        return validators;
    }

    /**
     * This method returns a validator that is described in the property file.
     *
     * @param validatorIdentifier part of the key describing the validator class
     * @param keyValue the value of a validator class key.
     * @return Validator.
     * @throws FormValidationException
     */
    private Validator getValidator(String validatorIdentifier, String keyValue) throws FormValidationException {
        log.debug("Entering getValidator() method. Validator class: {}", keyValue);
        Class clazz;
        Validator validator;
        try {
            clazz = Class.forName(keyValue);
            validator = (Validator) clazz.newInstance();
            log.debug("Validator created");
            setValidatorFields(validator, validatorIdentifier);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new FormValidationException("Error: FormValidator class, getValidator() method: ", e);
        }
        log.debug("Leaving getValidator() method.");
        return validator;
    }

    /**
     * Method sets all of the values described in the property file, corresponding to fields of validator.
     *
     * @param validator Validator.
     * @param validatorIdentifier part of the key describing a validator class.
     * @throws FormValidationException
     */
    private void setValidatorFields(Validator validator, String validatorIdentifier) throws FormValidationException {
        log.debug("Entering setValidatorFields() method.");
        Set<Map.Entry<String, String>> entries = properties.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            String[] keyParts = key.split(DOT_REGEX);
            String validatorField = keyParts[keyParts.length - 1];

            if (key.contains(validatorIdentifier)) {
                try {
                    Object objValue = getPropertyValue(value);
                    log.debug("Value of {} = {}", key, objValue);
                    BeanInfo beanInfo = Introspector.getBeanInfo(validator.getClass());
                    PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();

                    for (PropertyDescriptor descriptor : descriptors) {
                        if (!validatorField.equals(CLASS) && descriptor.getName().equals(validatorField)) {
                            descriptor.getWriteMethod().invoke(validator, objValue);
                            log.debug("Method: {}, value = {}", descriptor.getWriteMethod(), objValue);
                        }
                    }
                    log.debug("Leaving setValidatorFields() method.");
                } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                    throw new FormValidationException("Error: FormValidator class, setValidatorFields() method: ", e);
                }
            }
        }
    }

    private Object getPropertyValue(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return value;
        }
    }
}

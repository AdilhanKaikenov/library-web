package com.epam.adk.web.library.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.epam.adk.web.library.validator.FormValidator.properties;

/**
 * ImageFileSizeValidator class created on 30.12.2016
 *
 * @author Kaikenov Adilhan
 * @see Validator
 **/
public class ImageFileSizeValidator extends AbstractValidator {

    private static final Logger log = LoggerFactory.getLogger(ImageFileSizeValidator.class);

    private static final String IMAGE_MAX_FILE_SIZE = "image.max.file.size";
    private static final int DEFAULT_FILE_SIZE_VALUE = 5242880;

    private long maxFileSize;

    public ImageFileSizeValidator() {
    }

    @Override
    public boolean isValid(Long value) {
        maxFileSize = getLongValue(properties.get(IMAGE_MAX_FILE_SIZE), DEFAULT_FILE_SIZE_VALUE);
        log.debug("Max image file size = {}", maxFileSize);
        return maxFileSize >= value;
    }

    private long getLongValue(String value, long defaultValue) {
        long result = defaultValue;
        try {
            result = Long.parseLong(value);
        } catch (NumberFormatException e) {
            return result;
        }
        return result;
    }
}

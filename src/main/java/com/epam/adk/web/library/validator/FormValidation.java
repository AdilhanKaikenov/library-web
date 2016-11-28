package com.epam.adk.web.library.validator;

import javax.servlet.http.HttpServletRequest;

public interface FormValidation {

    boolean isInvalid(HttpServletRequest request);

}

package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.FormValidationException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.service.UserService;
import com.epam.adk.web.library.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.epam.adk.web.library.util.ConstantsHolder.*;

/**
 * EditProfileAction class created on 08.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class EditProfileAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(EditProfileAction.class);

    private static final String EDIT_PROFILE_FORM_NAME = "edit.profile";
    private static final String EDIT_PROFILE_PAGE_NAME = "edit-profile";
    private static final String PERSONAL_AREA_PAGE_NAME = "personal-area";
    private static final String INVALID_INFORMATION_REQUEST_ATTRIBUTE = "invalidInformation";
    private static final String CAN_NOT_UPDATE_USER_DATA_STORED_MESSAGE = "can.not.update.user.data.message";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The EditProfileAction started execute.");

        boolean isCreated = false;
        HttpSession session = request.getSession(isCreated);
        User user = ((User) session.getAttribute(USER_PARAMETER));

        String password = request.getParameter(PASSWORD_PARAMETER);
        String email = request.getParameter(EMAIL_PARAMETER);
        log.debug("Email: {}", email);
        String address = request.getParameter(ADDRESS_PARAMETER);
        log.debug("Address: {}", address);
        String mobilePhone = request.getParameter(MOBILE_PHONE_PARAMETER);
        log.debug("Mobile phone: {}", mobilePhone);

        try {
            FormValidator formValidator = new FormValidator();
            boolean isInvalid = formValidator.isInvalid(EDIT_PROFILE_FORM_NAME, request);
            log.debug("Edit profile form validation, invalid = {}", isInvalid);
            if (isInvalid) {
                return EDIT_PROFILE_PAGE_NAME;
            }
        } catch (FormValidationException e) {
            throw new ActionException("Error: EditProfileAction class. Validation failed:", e);
        }

        user.setPassword(password);
        user.setEmail(email);
        user.setAddress(address);
        user.setMobilePhone(mobilePhone);

        UserService userService = new UserService();

        try {
            userService.updateUserData(user);
        } catch (ServiceException e) {
            log.error("Error: EditProfileAction class, Can not update user: {}", e);
            request.setAttribute(INVALID_INFORMATION_REQUEST_ATTRIBUTE, CAN_NOT_UPDATE_USER_DATA_STORED_MESSAGE);
            return EDIT_PROFILE_PAGE_NAME;
        }
        return REDIRECT_PREFIX + PERSONAL_AREA_PAGE_NAME;
    }
}

package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.service.UserService;
import com.epam.adk.web.library.validator.FormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * EditProfileAction class created on 08.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class EditProfileAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(EditProfileAction.class);
    private static final String PASSWORD_PARAMETER = "password";
    private static final String EMAIL_PARAMETER = "email";
    private static final String ADDRESS_PARAMETER = "address";
    private static final String MOBILE_PHONE_PARAMETER = "mobilePhone";
    private static final String USER_PARAMETER = "user";
    private static final String EDIT_PROFILE_PAGE_NAME = "edit-profile";
    private static final String INVALID_INFORMATION_REQUEST_ATTRIBUTE = "invalidInformation";
    private static final String PERSONAL_AREA_PAGE_NAME = "personal-area";
    private static final String REDIRECT_PREFIX = "redirect:";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The EditProfileAction started execute.");

        HttpSession session = request.getSession(false);
        User user = ((User) session.getAttribute(USER_PARAMETER));

        String password = request.getParameter(PASSWORD_PARAMETER);
        String email = request.getParameter(EMAIL_PARAMETER);
        String address = request.getParameter(ADDRESS_PARAMETER);
        String mobilePhone = request.getParameter(MOBILE_PHONE_PARAMETER);

        FormValidator formValidator = new FormValidator();

        boolean isFormInvalid = formValidator.isEditProfileFormInvalid(request);
        log.debug("Edit profile form validation, invalid = {}", isFormInvalid);
        if (isFormInvalid) {
            return EDIT_PROFILE_PAGE_NAME;
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
            request.setAttribute(INVALID_INFORMATION_REQUEST_ATTRIBUTE, "can.not.update.user.data.message");
            return EDIT_PROFILE_PAGE_NAME;
        }
        return REDIRECT_PREFIX + PERSONAL_AREA_PAGE_NAME;
    }
}

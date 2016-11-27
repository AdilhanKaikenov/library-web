package com.epam.adk.web.library.action;

import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.model.enums.Gender;
import com.epam.adk.web.library.validator.RegistrationFormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RegistrationAction class created on 27.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class RegistrationAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String surname = request.getParameter("surname");
        String patronymic = request.getParameter("patronymic");
        Gender gender = Gender.from(request.getParameter("gender"));
        String address = request.getParameter("address");
        String mobilePhone = request.getParameter("mobilePhone");

        RegistrationFormValidator formValidator = new RegistrationFormValidator();
        if (formValidator.isInvalid(request)){
            return "registration";
        }

        User user = new User();


        return "redirect:success-registration";
    }
}

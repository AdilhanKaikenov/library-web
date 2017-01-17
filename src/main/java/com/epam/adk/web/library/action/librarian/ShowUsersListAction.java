package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.service.UserService;
import com.epam.adk.web.library.util.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ShowUsersListAction class created on 09.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowUsersListAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ShowUsersListAction.class);

    private static final int LINE_PER_PAGE_NUMBER = 5;
    private static final String USERS_LIST_PAGE_NAME = "users-list";
    private static final String USERS_REQUEST_ATTRIBUTE = "users";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The ShowUsersListAction started execute.");

        UserService userService = new UserService();

        try {
            int usersNumber = userService.getUsersNumber();

            Pagination pagination = new Pagination();
            int pageNumber = pagination.getPageNumber(request, usersNumber, LINE_PER_PAGE_NUMBER);

            List<User> users = userService.getPaginated(pageNumber, LINE_PER_PAGE_NUMBER);

            request.setAttribute(USERS_REQUEST_ATTRIBUTE, users);
        } catch (ServiceException e) {
            throw new ActionException("Error: ShowUsersListAction class. ", e);
        }
        return USERS_LIST_PAGE_NAME;
    }
}

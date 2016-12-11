package com.epam.adk.web.library.action;

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
public class ShowUsersListAction implements Action{

    private static final Logger log = LoggerFactory.getLogger(ShowUsersListAction.class);

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int LINE_PER_PAGE_NUMBER = 2;
    private static final String PAGE_PARAMETER = "page";
    private static final String USERS_LIST_PAGE_NAME = "users-list";
    private static final String USERS_REQUEST_ATTRIBUTE = "users";
    private static final String PAGES_NUMBER_REQUEST_ATTRIBUTE = "pagesNumber";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The ShowUsersListAction started execute.");

        UserService userService = new UserService();

        int page = DEFAULT_PAGE_NUMBER;
        String pageParameter = request.getParameter(PAGE_PARAMETER);

        if (pageParameter != null) {
            page = Integer.parseInt(pageParameter);
            log.debug("ShowUsersListAction: page #{}", page);
        }

        try {
            List<User> users = userService.getPaginated(page, LINE_PER_PAGE_NUMBER);

            int usersNumber = userService.getUsersNumber();
            log.debug("ShowUsersListAction: total users number = {}", usersNumber);
            Pagination pagination = new Pagination();
            int pagesNumber = pagination.getPagesNumber(usersNumber, LINE_PER_PAGE_NUMBER);
            log.debug("ShowUsersListAction: total pages number = {}", pagesNumber);

            request.setAttribute(USERS_REQUEST_ATTRIBUTE, users);
            request.setAttribute(PAGES_NUMBER_REQUEST_ATTRIBUTE, pagesNumber);
        } catch (ServiceException e) {
            throw new ActionException("Error: ShowUsersListAction class. ", e);
        }
        return USERS_LIST_PAGE_NAME;
    }
}

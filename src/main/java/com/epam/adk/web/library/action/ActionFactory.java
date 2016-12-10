package com.epam.adk.web.library.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * ActionFactory class created on 23.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class ActionFactory {

    private static final Logger log = LoggerFactory.getLogger(ActionFactory.class);
    private static final ActionFactory instance = new ActionFactory();

    private static Map<String, Action> actions;

    private ActionFactory() {
        log.debug("Entering constructor ActionFactory class");
        actions = new HashMap<>();
        actions.put("GET/welcome", new ShowWelcomeAction());
        actions.put("GET/set-locale", new SelectLocaleAction());
        actions.put("POST/registration", new RegistrationAction());
        actions.put("GET/registration", new ShowPageAction("registration"));
        actions.put("GET/success-registration", new ShowPageAction("success-registration"));
        actions.put("POST/authorization", new AuthorizationAction());
        actions.put("GET/authorization", new AuthorizationAction());
        actions.put("GET/authorization-error", new ShowPageAction("authorization-error"));
        actions.put("GET/logout", new LogoutAction());
        actions.put("GET/about-book", new BookAboutAction());
        actions.put("POST/comment", new CommentAction());
        actions.put("GET/category", new CategoryAction());
        actions.put("POST/order-book-request", new BookOrderRequestAction());
        actions.put("GET/user-orders", new ShowUserOrdersAction());
        actions.put("GET/requests", new ShowAllOrderRequestsAction());
        actions.put("GET/orders", new ShowAllAllowedOrdersAction());
        actions.put("GET/rejected-orders", new ShowAllRejectedOrdersAction());
        actions.put("POST/lend-out-book", new BookLendOutAction());
        actions.put("POST/reject-book-order", new RejectBookOrderAction());
        actions.put("POST/book-returned", new BookReturnedAction());
        actions.put("POST/delete-old-order-requests", new DeleteOldOrderRequests());
        actions.put("GET/personal-area", new ShowPageAction("profile"));
        actions.put("GET/edit-profile", new ShowPageAction("edit-profile"));
        actions.put("POST/edit-profile", new EditProfileAction());
        actions.put("POST/delete-book", new DeleteBookAction());
        actions.put("GET/book-search", new ShowPageAction("book-search"));
        actions.put("POST/book-search", new BookSearchAction());
        actions.put("GET/book-search-result", new ShowPageAction("book-search-result"));
        actions.put("GET/book-amount", new ShowBookAmountAction());
        actions.put("POST/edit-book-amount", new EditBookAmountAction());
        actions.put("GET/users", new ShowUsersListAction());
        actions.put("GET/edit-user", new ShowEditUserAction());
        actions.put("POST/edit-user", new EditUserAction());
        actions.put("GET/add-new-book", new ShowPageAction("add-new-book"));
        actions.put("POST/add-new-book", new AddNewBookAction());
        log.debug("Action Factory class, actions SIZE {}", actions.size());
    }

    public static ActionFactory getInstance(){
        return instance;
    }

    /**
     * The method which returns Action.
     *
     * @param actionName string key to determine the object is an Action.
     * @return Action.
     */
    public Action getAction(String actionName){
        return actions.get(actionName);
    }
}

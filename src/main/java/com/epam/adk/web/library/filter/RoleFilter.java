package com.epam.adk.web.library.filter;


import com.epam.adk.web.library.exception.PropertyManagerException;
import com.epam.adk.web.library.exception.RoleFilterConfigurationException;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.model.enums.Role;
import com.epam.adk.web.library.propmanager.PropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * RoleFilter class created on 02.12.2016
 *
 * @author Kaikenov Adilhan
 */
public final class RoleFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(RoleFilter.class);

    private static Collection<String> librarianAvailableActions;
    private static Collection<String> readerAvailableActions;
    private static Collection<String> anonAvailableActions;

    private static final String PATH_INFO = "/?action=welcome";
    private static final String READER_ROLE = "Reader";
    private static final String LIBRARIAN_ROLE = "Librarian";
    private static final String USER_PARAMETER = "user";
    private static final String SERVLET_CONTEXT = "/do";
    private static final String ACTION_PARAMETER = "action";

    private Set<String> librarianActions = new HashSet<>();
    private Set<String> readerActions = new HashSet<>();
    private Set<String> anonActions = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        log.debug("LIBRARIAN Available Actions = {}", librarianAvailableActions.size());
        log.debug("READER Available Actions = {}", readerAvailableActions.size());
        log.debug("ANON Available Actions = {}", anonAvailableActions.size());

        librarianActions.addAll(librarianAvailableActions);
        readerActions.addAll(readerAvailableActions);
        anonActions.addAll(anonAvailableActions);

    }

    public static void configure() throws RoleFilterConfigurationException {
        try {
            PropertiesManager libPropertiesManager = new PropertiesManager("/role_actions/librarian.actions.properties");
            PropertiesManager readerPropertiesManager = new PropertiesManager("/role_actions/reader.actions.properties");
            PropertiesManager anonPropertiesManager = new PropertiesManager("/role_actions/anon.actions.properties");

            librarianAvailableActions = libPropertiesManager.getAllValues();
            readerAvailableActions = readerPropertiesManager.getAllValues();
            anonAvailableActions = anonPropertiesManager.getAllValues();
        } catch (PropertyManagerException e) {
            throw new RoleFilterConfigurationException("Error: RoleFilter class, configure() method.", e);
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        log.debug("RoleFilter class, doFilter()");
        User user = null;
        Role role;

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String actionName = getActionName(request);
        log.debug("RoleFilter class, doFilter() method: actionName = {}", actionName);
        HttpSession session = request.getSession(false);

        if (session != null) {
            user = ((User) session.getAttribute(USER_PARAMETER));
        }

        if (user == null) {
            log.debug("The user is not authorized. User = NULL");
            role = Role.ANONYMOUS;
        } else {
            role = user.getRole();
        }
        log.debug("User Role = {}", role);

        if (isAvailableFor(role, actionName)) {
            log.debug("{}: Action = {}, AVAILABLE", role, actionName);
        } else {
            log.debug("{}: Action = {}, UNAVAILABLE", role, actionName);
            response.sendRedirect(request.getContextPath() + SERVLET_CONTEXT + PATH_INFO);
            return;
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }

    /**
     * The method to get action name from request.
     *
     * @param request
     * @return String
     */
    private String getActionName(HttpServletRequest request) {
        return request.getMethod() + "/" + request.getParameter(ACTION_PARAMETER);
    }

    /**
     * @return (true) if action is available and (false) if unavailable.
     */
    private boolean isAvailableFor(Role role, String actionName) {
        switch (role.getValue()) {
            case LIBRARIAN_ROLE:
                return librarianActions.contains(actionName);
            case READER_ROLE:
                return readerActions.contains(actionName);
            default:
                return anonActions.contains(actionName);
        }
    }
}

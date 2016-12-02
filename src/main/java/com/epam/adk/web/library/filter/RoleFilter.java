package com.epam.adk.web.library.filter;


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
public class RoleFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(RoleFilter.class);
    private static final Collection<String> adminAvailableActions = PropertiesManager.getInstance().getAllValues("/role_actions/admin.actions.properties");
    private static final Collection<String> userAvailableActions = PropertiesManager.getInstance().getAllValues("/role_actions/user.actions.properties");
    private static final Collection<String> anonAvailableActions = PropertiesManager.getInstance().getAllValues("/role_actions/anon.actions.properties");

    private static final String USER_PARAMETER = "user";
    private static final String SERVLET_CONTEXT = "/do";
    private static final String PATH_INFO = "/?action=welcome";
    private static final String ACTION_PARAMETER = "action";
    private static final String ADMIN_ROLE = "Admin";
    private static final String USER_ROLE = "User";

    private Set<String> adminActions = new HashSet<>();
    private Set<String> userActions = new HashSet<>();
    private Set<String> anonActions = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        log.debug("Admin Available Actions = {}", adminAvailableActions.size());
        log.debug("User Available Actions = {}", userAvailableActions.size());
        log.debug("Anon Available Actions = {}", anonAvailableActions.size());

        adminActions.addAll(adminAvailableActions);
        userActions.addAll(userAvailableActions);
        anonActions.addAll(anonAvailableActions);

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        log.info("RoleFilter class, doFilter()");
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
    public boolean isAvailableFor(Role role, String actionName) {
        switch (role.getValue()) {
            case ADMIN_ROLE:
                return adminActions.contains(actionName);
            case USER_ROLE:
                return userActions.contains(actionName);
            default:
                return anonActions.contains(actionName);
        }
    }
}

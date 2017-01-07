package com.epam.adk.web.library.servlet;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.action.ActionFactory;
import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ActionFactoryException;
import com.epam.adk.web.library.exception.PropertyManagerException;
import com.epam.adk.web.library.exception.ServletConfigurationException;
import com.epam.adk.web.library.propmanager.PropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * FrontControllerServlet class created on 18.11.2016
 *
 * @author Kaikenov Adilhan
 */
public final class FrontControllerServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(FrontControllerServlet.class);

    private static final String RIGHT_SLASH = "/";
    private static final String JSP_EXPANSION = ".jsp";
    private static final String ACTION_PARAMETER = "action";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String FRONT_CONTROLLER_SERVLET_PROPERTIES_FILE_NAME = "front.controller.servlet.properties";

    private static ActionFactory factory;
    private static PropertiesManager propertiesManager;

    private String pathInfo = propertiesManager.get("path.info");
    private String servletContext = propertiesManager.get("servlet.context");
    private String webInfDirectory = propertiesManager.get("web.inf.directory");

    @Override
    public void init() throws ServletException {
        factory = ActionFactory.getInstance();
        log.debug("Start FrontControllerServlet initialization servlet.");
    }

    public static void configure() throws ServletConfigurationException {
        try {
            propertiesManager = new PropertiesManager(FRONT_CONTROLLER_SERVLET_PROPERTIES_FILE_NAME);
        } catch (PropertyManagerException e) {
            throw new ServletConfigurationException("Error: FrontControllerServlet class, configure() method.", e);
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Action action = null;
        try {
            action = factory.getAction(getActionName(request));
        } catch (ActionFactoryException e) {
            log.error("ActionFactory getAction() method failed. {}", e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        if (action == null) {
            log.error("Error: FrontControllerServlet class, service() method: action = NULL");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String view;
        try {
            view = action.execute(request, response);
            log.debug("View = {}", view);
        } catch (ActionException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        proceedTo(request, response, view);
    }

    private void proceedTo(HttpServletRequest request, HttpServletResponse response, String view) throws IOException, ServletException {
        if (view == null){
            log.error("Error: FrontControllerServlet class, proceedTo() method: view = NULL");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (view.contains(REDIRECT_PREFIX)){
            response.sendRedirect(getRedirectURL(request, view));
        } else {
            request.getRequestDispatcher(webInfDirectory + view + JSP_EXPANSION).forward(request, response);
        }
    }

    private String getRedirectURL(HttpServletRequest request, String view) {
        return request.getContextPath() + servletContext + pathInfo + view.substring(REDIRECT_PREFIX.length());
    }

    /**
     * The method to get action name from request.
     *
     * @param request
     * @return String
     */
    private String getActionName(HttpServletRequest request) {
        return request.getMethod() + RIGHT_SLASH + request.getParameter(ACTION_PARAMETER);
    }
}

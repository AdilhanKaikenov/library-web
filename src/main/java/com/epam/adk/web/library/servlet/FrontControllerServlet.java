package com.epam.adk.web.library.servlet;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.action.ActionFactory;
import com.epam.adk.web.library.exception.ActionException;
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
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String ACTION_PARAMETER = "action";
    private static final String WEB_INF = "/WEB-INF/";
    private static final String JSP_FORMAT = ".jsp";
    private static final String SERVLET_CONTEXT = "/do";
    private static final String PATH_INFO = "/?action=";
    private static ActionFactory factory;

    @Override
    public void init() throws ServletException {
        factory = ActionFactory.getInstance();
        log.debug("Start FrontControllerServlet initialization servlet.");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Action action = factory.getAction(getActionName(request));
        if (action == null) {
            log.error("Error: FrontControllerServlet class, service() method: action = NULL");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String view;
        try {
            view = action.execute(request, response);
        } catch (ActionException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
            request.getRequestDispatcher(WEB_INF + view + JSP_FORMAT).forward(request, response);
        }
    }

    private String getRedirectURL(HttpServletRequest request, String view) {
        return request.getContextPath() + SERVLET_CONTEXT + PATH_INFO + view.substring(REDIRECT_PREFIX.length());
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
}

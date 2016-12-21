package com.epam.adk.web.library.servlet;

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
 * ErrorHandler class created on 15.12.2016
 *
 * @author Kaikenov Adilhan
 */
public class ErrorHandler extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    private static final String UNKNOWN = "Unknown";
    private static final String JSP_EXPANSION = ".jsp";
    private static final String ERROR_PAGE_NAME = "error-page";
    private static final String STATUS_CODE_REQUEST_ATTRIBUTE = "statusCode";
    private static final String ERROR_HANDLER_SERVLET_PROPERTIES_FILE_NAME = "error.handler.servlet.properties";

    private static PropertiesManager propertiesManager;

    private String servletError = propertiesManager.get("servlet.error.exception");
    private String webInfDirectory = propertiesManager.get("web.inf.directory");
    private String servletErrorStatusCode = propertiesManager.get("servlet.error.status_code");
    private String servletErrorServletName = propertiesManager.get("servlet.error.servlet_name");
    private String servletErrorRequestUri = propertiesManager.get("servlet.error.request_uri");

    public static void configure() throws ServletConfigurationException {
        try {
            propertiesManager = new PropertiesManager(ERROR_HANDLER_SERVLET_PROPERTIES_FILE_NAME);
        } catch (PropertyManagerException e) {
            throw new ServletConfigurationException("Error: FrontControllerServlet class, configure() method.", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Throwable throwable = (Throwable)
                request.getAttribute(servletError);
        Integer statusCode = (Integer)
                request.getAttribute(servletErrorStatusCode);
        String servletName = (String)
                request.getAttribute(servletErrorServletName);
        if (servletName == null){
            servletName = UNKNOWN;
        }
        String requestUri = (String) request.getAttribute(servletErrorRequestUri);
        if (requestUri == null){
            requestUri = UNKNOWN;
        }

        log.error("Error Throwable: {}", throwable);
        log.error("Error Status code: {}", statusCode);
        log.error("Error Servlet name: {}", servletName);
        log.error("Error Request URI: {}", requestUri);
        request.setAttribute(STATUS_CODE_REQUEST_ATTRIBUTE, statusCode);
        request.getRequestDispatcher(webInfDirectory + ERROR_PAGE_NAME + JSP_EXPANSION).forward(request, response);

    }
}

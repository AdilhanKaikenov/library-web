package com.epam.adk.web.library.servlet;

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
    private static final String WEB_INF_DIRECTORY = "/WEB-INF/";
    private static final String STATUS_CODE_REQUEST_ATTRIBUTE = "statusCode";
    private static final String JAVAX_SERVLET_ERROR_EXCEPTION = "javax.servlet.error.exception";
    private static final String JAVAX_SERVLET_ERROR_STATUS_CODE = "javax.servlet.error.status_code";
    private static final String JAVAX_SERVLET_ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";
    private static final String JAVAX_SERVLET_ERROR_REQUEST_URI = "javax.servlet.error.request_uri";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Throwable throwable = (Throwable)
                request.getAttribute(JAVAX_SERVLET_ERROR_EXCEPTION);
        Integer statusCode = (Integer)
                request.getAttribute(JAVAX_SERVLET_ERROR_STATUS_CODE);
        String servletName = (String)
                request.getAttribute(JAVAX_SERVLET_ERROR_SERVLET_NAME);
        if (servletName == null){
            servletName = UNKNOWN;
        }
        String requestUri = (String) request.getAttribute(JAVAX_SERVLET_ERROR_REQUEST_URI);
        if (requestUri == null){
            requestUri = UNKNOWN;
        }

        log.error("Error Throwable: {}", throwable);
        log.error("Error Status code: {}", statusCode);
        log.error("Error Servlet name: {}", servletName);
        log.error("Error Request URI: {}", requestUri);
        request.setAttribute(STATUS_CODE_REQUEST_ATTRIBUTE, statusCode);
        request.getRequestDispatcher(WEB_INF_DIRECTORY + ERROR_PAGE_NAME + JSP_EXPANSION).forward(request, response);

    }
}

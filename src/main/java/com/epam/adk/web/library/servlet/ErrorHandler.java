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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Throwable throwable = (Throwable)
                request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer)
                request.getAttribute("javax.servlet.error.status_code");
        String servletName = (String)
                request.getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null){
            servletName = "Unknown";
        }
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null){
            requestUri = "Unknown";
        }

        log.error("Error Throwable: " + throwable);
        log.error("Error Status code: " + statusCode);
        log.error("Error Servlet name: " + servletName);
        log.error("Error Request URI: " + requestUri);
        request.setAttribute("statusCode", statusCode);
        request.getRequestDispatcher("/WEB-INF/error-page.jsp").forward(request, response);

    }
}

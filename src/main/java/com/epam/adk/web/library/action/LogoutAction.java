package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LogoutAction class created on 03.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class LogoutAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(LogoutAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The LogoutAction started execute.");

        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();

        log.info("User logged out");

        return "redirect:welcome";
    }
}

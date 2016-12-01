package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Displays page.
 *
 * ShowPageAction class created on 27.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class ShowPageAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(ShowPageAction.class);

    private String page;

    public ShowPageAction(String page) {
        this.page = page;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ActionException {
        log.debug("The show page action started execute. Page = {}", page);

        return page;
    }
}

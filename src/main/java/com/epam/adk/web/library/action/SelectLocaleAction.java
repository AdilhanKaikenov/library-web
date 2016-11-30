package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

/**
 * SelectLocaleAction class created on 30.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class SelectLocaleAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(SelectLocaleAction.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The action of set locale started execute.");

        String region = request.getParameter("region");
        Config.set(request.getSession(), Config.FMT_LOCALE, new java.util.Locale(region));

        String referer = request.getHeader("referer");
        log.debug("referer = {}", referer);

        if (referer.contains("set-locale&region=")){
            return "redirect:welcome";
        }

        if (referer.contains("action=")) {
            String currentPage = referer.substring(referer.lastIndexOf("=") + 1);
            log.debug("PAY ATTENTION: Current page '{}'", currentPage);
            return "redirect:" + currentPage;
        }
        return "redirect:welcome";
    }
}

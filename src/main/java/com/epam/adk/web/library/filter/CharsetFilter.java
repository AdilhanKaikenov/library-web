package com.epam.adk.web.library.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * CharsetFilter class created on 23.11.2016
 *
 * @author Kaikenov Adilhan
 * @see Filter
 */
public final class CharsetFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(CharsetFilter.class);

    private static final String ENCODING = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("The CharsetFilter is initialized");
    }

    /**
     * Change encoding to UTF-8.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING);
        response.setCharacterEncoding(ENCODING);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

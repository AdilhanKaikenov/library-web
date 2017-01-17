package com.epam.adk.web.library.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import static com.epam.adk.web.library.util.ConstantsHolder.PAGES_NUMBER_REQUEST_ATTRIBUTE;
import static com.epam.adk.web.library.util.ConstantsHolder.PAGE_PARAMETER;

/**
 * Pagination class created on 04.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public final class Pagination {

    private static final Logger log = LoggerFactory.getLogger(Pagination.class);

    private static final int DEFAULT_PAGE_NUMBER = 1;

    /**
     * The method for calculating the total number of pages.
     *
     * @param totalLineNumber total amount.
     * @param linePerPageNumber number per page.
     * @return total number of pages.
     */
    private int getPagesNumber(int totalLineNumber, int linePerPageNumber) {
        int pageCount = totalLineNumber / linePerPageNumber;
        int residue = totalLineNumber % linePerPageNumber;
        if (residue > 0) {
            pageCount++;
        }
        return pageCount;
    }

    /**
     * The method returns the number of the page.
     *
     * @param request HttpServletRequest.
     * @param totalAmount total amount of items.
     * @param linePerPage amount of items per page.
     * @return page number.
     */
    public int getPageNumber(HttpServletRequest request, int totalAmount, int linePerPage) {
        int page = DEFAULT_PAGE_NUMBER;
        String pageParameter = request.getParameter(PAGE_PARAMETER);
        if (pageParameter != null) {
            page = Integer.parseInt(pageParameter);
            log.debug("Page #{}", page);
        }
        log.debug("Total amount = {}", totalAmount);
        int pagesNumber = getPagesNumber(totalAmount, linePerPage);
        log.debug("Total pages number = {}", pagesNumber);
        request.setAttribute(PAGES_NUMBER_REQUEST_ATTRIBUTE, pagesNumber);
        return page;
    }
}

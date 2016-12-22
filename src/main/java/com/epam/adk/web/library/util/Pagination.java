package com.epam.adk.web.library.util;

/**
 * Pagination class created on 04.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public final class Pagination {

    /**
     * The method for calculating the total number of pages.
     *
     * @param totalLineNumber total amount.
     * @param linePerPageNumber number per page.
     * @return total number of pages.
     */
    public int getPagesNumber(int totalLineNumber, int linePerPageNumber) {
        int pageCount = totalLineNumber / linePerPageNumber;
        int residue = totalLineNumber % linePerPageNumber;
        if (residue > 0) {
            pageCount++;
        }
        return pageCount;
    }
}

package com.epam.adk.web.library.action.librarian;

/**
 * ShowHandleReturnBooksPageAction class created on 18.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class ShowHandleReturnBooksPageAction extends AbstractShowHandlePageAction {

    private static final String HANDLE_RETURN_BOOKS_PAGE_NAME = "handle-return-books";

    @Override
    protected String getPage() {
        return HANDLE_RETURN_BOOKS_PAGE_NAME;
    }
}

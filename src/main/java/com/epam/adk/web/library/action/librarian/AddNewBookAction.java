package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.model.enums.Genre;
import com.epam.adk.web.library.service.BookService;
import org.apache.commons.fileupload.FileUploadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.time.Year;

/**
 * AddNewBookAction class created on 10.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class AddNewBookAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(AddNewBookAction.class);

    private static final String IMAGE_PATH = "D:\\images";
    private static final String GENRE_PARAMETER = "genre";
    private static final String TITLE_PARAMETER = "title";
    private static final String COVER_PARAMETER = "cover";
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String WELCOME_PAGE_NAME = "welcome";
    private static final String DEFAULT_COVER_JPG = "default_cover.jpg";
    private static final String AUTHORS_PARAMETER = "authors";
    private static final String FILENAME_PARAMETER = "filename";
    private static final String DESCRIPTION_PARAMETER = "description";
    private static final String PUBLISH_YEAR_PARAMETER = "publishYear";
    private static final String TOTAL_AMOUNT_PARAMETER = "totalAmount";
    private static final String CONTENT_DISPOSITION_HEADER = "content-disposition";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The AddNewBookAction started execute.");

        Part part;
        String cover = null;
        try {
            part = request.getPart(COVER_PARAMETER);
            cover = extractFileName(part);
            log.debug("Cover filename = {}", cover);
            part.write(IMAGE_PATH + File.separator + cover);
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause() instanceof FileUploadBase.FileSizeLimitExceededException) {
                throw new ActionException("Error: AddNewBookAction class. Called getPart() method failed.", e);
            }
        }

        String title = request.getParameter(TITLE_PARAMETER);
        log.debug("New book title: {}", title);
        String authors = request.getParameter(AUTHORS_PARAMETER);
        log.debug("New book authors: {}", authors);
        Year publishYear = Year.of(Integer.parseInt(request.getParameter(PUBLISH_YEAR_PARAMETER)));
        log.debug("New book publishYear: {}", publishYear);
        Genre genre = Genre.from(request.getParameter(GENRE_PARAMETER));
        log.debug("New book genre: {}", genre);
        String description = request.getParameter(DESCRIPTION_PARAMETER);
        log.debug("New book description length: {}", description.length());
        int totalAmount = Integer.parseInt(request.getParameter(TOTAL_AMOUNT_PARAMETER));
        log.debug("New totalAmount title: {}", totalAmount);

        Book book = new Book();
        book.setTitle(title);
        if (cover != null) {
            book.setCover(cover);
        } else {
            book.setCover(DEFAULT_COVER_JPG);
        }
        book.setAuthors(authors);
        book.setPublishYear(publishYear);
        book.setGenre(genre);
        book.setDescription(description);
        book.setTotalAmount(totalAmount);

        try {
            BookService bookService = new BookService();
            bookService.add(book);

        } catch (ServiceException e) {
            throw new ActionException("Error: AddNewBookAction class. ", e);
        }

        return REDIRECT_PREFIX + WELCOME_PAGE_NAME;
    }

    /**
     * The method returns file name from item that was received within a multipart/form-data POST request.
     *
     * @param part part or from item
     * @return (string) filename
     */
    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader(CONTENT_DISPOSITION_HEADER);
        String[] items = contentDisposition.split(";");
        for (String s : items) {
            if (s.trim().startsWith(FILENAME_PARAMETER)) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}

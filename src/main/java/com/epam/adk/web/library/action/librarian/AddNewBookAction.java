package com.epam.adk.web.library.action.librarian;

import com.epam.adk.web.library.action.Action;
import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.model.enums.Genre;
import com.epam.adk.web.library.service.BookService;
import com.epam.adk.web.library.validator.ImageFileSizeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.time.Year;

import static com.epam.adk.web.library.util.ConstantsHolder.REDIRECT_PREFIX;
import static com.epam.adk.web.library.util.ConstantsHolder.WELCOME_PAGE;

/**
 * AddNewBookAction class created on 10.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class AddNewBookAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(AddNewBookAction.class);

    private static final String EQUAL_SIGN = "=";
    private static final String SIGN_SEMICOLON = ";";
    private static final String IMAGE_PATH = "D:\\images";
    private static final String GENRE_PARAMETER = "genre";
    private static final String TITLE_PARAMETER = "title";
    private static final String COVER_PARAMETER = "cover";
    private static final String AUTHOR_PARAMETER = "author";
    private static final String FILENAME_PARAMETER = "filename";
    private static final String DESCRIPTION_PARAMETER = "description";
    private static final String ADD_NEW_BOOK_PAGE_NAME = "add-new-book";
    private static final String PUBLISH_YEAR_PARAMETER = "publishYear";
    private static final String TOTAL_AMOUNT_PARAMETER = "totalAmount";
    private static final String CONTENT_DISPOSITION_HEADER = "content-disposition";
    private static final String FILE_SIZE_INCORRECT_REQUEST_ATTRIBUTE = "fileSizeIncorrect";
    private static final String IMAGE_FILE_SIZE_INCORRECT_STORED_MESSAGE = "image.file.size.incorrect";


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The AddNewBookAction started execute.");

        String cover;
        try {
            Part part = request.getPart(COVER_PARAMETER);
            cover = extractFileName(part);
            log.debug("Cover filename = {}", cover);
            ImageFileSizeValidator sizeValidator = new ImageFileSizeValidator();
            long coverFileSize = part.getSize();
            log.debug("Cover file size = {}", coverFileSize);
            if (!sizeValidator.isValid(coverFileSize)) {
                request.setAttribute(FILE_SIZE_INCORRECT_REQUEST_ATTRIBUTE, IMAGE_FILE_SIZE_INCORRECT_STORED_MESSAGE);
                return ADD_NEW_BOOK_PAGE_NAME;
            }
            part.write(IMAGE_PATH + File.separator + cover);
        } catch (ServletException | IOException e) {
            throw new ActionException("Error: AddNewBookAction class.", e);
        }


        String title = request.getParameter(TITLE_PARAMETER);
        log.debug("New book title: {}", title);
        String author = request.getParameter(AUTHOR_PARAMETER);
        log.debug("New book author: {}", author);
        Year publishYear = Year.of(Integer.parseInt(request.getParameter(PUBLISH_YEAR_PARAMETER)));
        log.debug("New book publishYear: {}", publishYear);
        Genre genre = Genre.getFromValue(request.getParameter(GENRE_PARAMETER));
        log.debug("New book genre: {}", genre);
        String description = request.getParameter(DESCRIPTION_PARAMETER);
        log.debug("New book description length: {}", description.length());
        int totalAmount = Integer.parseInt(request.getParameter(TOTAL_AMOUNT_PARAMETER));
        log.debug("New totalAmount title: {}", totalAmount);

        Book book = new Book();
        book.setTitle(title);
        book.setCover(cover);
        book.getAuthor().setName(author.trim());
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

        return REDIRECT_PREFIX + WELCOME_PAGE;
    }

    /**
     * The method returns file name getFromValue item that was received within a multipart/form-data POST request.
     *
     * @param part part or getFromValue item
     * @return (string) filename
     */
    private String extractFileName(Part part) {
        log.debug("Entering extractFileName() method");
        String contentDisposition = part.getHeader(CONTENT_DISPOSITION_HEADER);
        String[] items = contentDisposition.split(SIGN_SEMICOLON);
        for (String paramKeyAndValue : items) {
            if (paramKeyAndValue.trim().startsWith(FILENAME_PARAMETER)) {
                // obtaining substring value without quotes
                String filename = paramKeyAndValue.substring(
                        paramKeyAndValue.indexOf(EQUAL_SIGN) + 2,
                        paramKeyAndValue.length() - 1);
                log.debug(paramKeyAndValue);
                log.debug("Leaving extractFileName(): filename = {}", filename);
                return filename;
            }
        }
        return "";
    }
}

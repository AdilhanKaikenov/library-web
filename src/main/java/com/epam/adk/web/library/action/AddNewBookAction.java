package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Book;
import com.epam.adk.web.library.model.enums.Genre;
import com.epam.adk.web.library.service.BookService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Year;

/**
 * AddNewBookAction class created on 10.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class AddNewBookAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {

        String title = request.getParameter("title");
        System.out.println("title: " + title);
        String cover = ((String) request.getAttribute("coverFilename"));
        System.out.println("cover: " + cover);
        String authors = request.getParameter("authors");
        System.out.println("authors: " + authors);
        Year publishYear = Year.of(Integer.parseInt(request.getParameter("publishYear")));
        System.out.println("publishYear: " + publishYear);
        Genre genre = Genre.from(request.getParameter("genre"));
        System.out.println("genre: " + genre);
        String description = request.getParameter("description");
        System.out.println("description: " + description);
        int totalAmount = Integer.parseInt(request.getParameter("totalAmount"));
        System.out.println("totalAmount: " + totalAmount);

        Book book = new Book();
        book.setTitle(title);
        book.setCover(cover);
        book.setAuthors(authors);
        book.setPublishYear(publishYear);
        book.setGenre(genre);
        book.setDescription(description);
        book.setTotalAmount(totalAmount);

        BookService bookService = new BookService();

        try {
            Book addedBook = bookService.add(book);
        } catch (ServiceException e) {

        }

        return "redirect:welcome";
    }
}

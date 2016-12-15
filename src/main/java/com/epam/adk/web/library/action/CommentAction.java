package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Comment;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;

/**
 * CommentAction class created on 03.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class CommentAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(CommentAction.class);

    private static final String REGEX_ENTER = "\\r";
    private static final String USER_PARAMETER = "user";
    private static final String LINE_BREAK_HTML_TAG = "<p>";
    private static final String BOOK_ID_PARAMETER = "bookID";
    private static final String COMMENT_PARAMETER = "comment";
    private static final String REDIRECT_ABOUT_BOOK_PAGE_ID_PARAMETER = "redirect:about-book&&id=";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {
        log.debug("The CommentAction started execute.");

        HttpSession session = request.getSession(false);

        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        User user = ((User) session.getAttribute(USER_PARAMETER));
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));
        String text = request.getParameter(COMMENT_PARAMETER).replaceAll(REGEX_ENTER, LINE_BREAK_HTML_TAG);

        Comment comment = new Comment();
        comment.setUserID(user.getId());
        comment.setUserLogin(user.getLogin());
        comment.setUserFirstname(user.getFirstname());
        comment.setUserSurname(user.getSurname());
        comment.setBookID(bookID);
        comment.setTime(time);
        comment.setText(text);

        CommentService commentService = new CommentService();

        try {
            commentService.writeComment(comment);
        } catch (ServiceException e) {
            throw new ActionException("Error: CommentAction class, execute() method. Called CommentService class, writeComment() failed.", e);
        }

        return REDIRECT_ABOUT_BOOK_PAGE_ID_PARAMETER + bookID;
    }
}

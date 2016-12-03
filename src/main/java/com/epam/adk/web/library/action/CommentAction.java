package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionException;
import com.epam.adk.web.library.exception.ServiceException;
import com.epam.adk.web.library.model.Comment;
import com.epam.adk.web.library.model.User;
import com.epam.adk.web.library.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * CommentAction class created on 03.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class CommentAction implements Action {

    private static final String USER_PARAMETER = "user";
    private static final String BOOK_ID_PARAMETER = "bookId";
    private static final String COMMENT_PARAMETER = "comment";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ActionException {

        HttpSession session = request.getSession(false);

        User user = ((User) session.getAttribute(USER_PARAMETER));
        int bookId = Integer.parseInt(request.getParameter(BOOK_ID_PARAMETER));
        Date date = new Date();
        String text = request.getParameter(COMMENT_PARAMETER);

        Comment comment = new Comment();
        comment.setUserID(user.getId());
        comment.setBookID(bookId);
        comment.setDate(date);

        comment.setText(text);

        CommentService commentService = new CommentService();

        try {
            commentService.writeComment(comment);
        } catch (ServiceException e) {
            throw new ActionException("Error: CommentAction class, execute() method. Called CommentService class, writeComment() failed.", e);
        }

        return "redirect:about-book&&id=" + bookId;
    }
}

package com.epam.adk.web.library.model;

import java.util.Date;

/**
 * Comment class created on 03.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class Comment extends BaseEntity {

    private int userID;
    private int bookID;
    private Date date;
    private String text;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

package com.epam.adk.web.library.model;

import java.sql.Timestamp;

/**
 * Comment class created on 03.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class Comment extends BaseEntity {

    private User user;
    private Book book;
    private Timestamp time;
    private String text;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

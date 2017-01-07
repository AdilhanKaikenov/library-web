package com.epam.adk.web.library.model;

import com.epam.adk.web.library.model.enums.Genre;

import java.time.Year;

/**
 * Book class created on 30.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class Book extends BaseEntity {

    private String title;
    private String cover;
    private Author author;
    private Year publishYear;
    private Genre genre;
    private String description;
    private int totalAmount;
    private boolean deleted;

    public Book() {
        author = new Author();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Year getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Year publishYear) {
        this.publishYear = publishYear;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

package com.epam.adk.web.library.model;

import com.epam.adk.web.library.model.enums.OrderStatus;
import com.epam.adk.web.library.model.enums.OrderType;

import java.sql.Date;

/**
 * Order class created on 05.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class Order extends BaseEntity {

    private User user;
    private Book book;
    private String client;
    private Date orderDate;
    private OrderType type;
    private Date from;
    private Date to;
    private int availableBookAmount;
    private OrderStatus status;

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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public int getAvailableBookAmount() {
        return availableBookAmount;
    }

    public void setAvailableBookAmount(int availableBookAmount) {
        this.availableBookAmount = availableBookAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}

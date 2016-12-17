package com.epam.adk.web.library.model;

/**
 * OrderBook class created on 05.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class OrderBook extends BaseEntity {

    private User user;
    private Book book;
    private String client;
    private Order order;
    private int availableBookAmount;

    public OrderBook() {
        user = new User();
        book = new Book();
        order = new Order();
    }

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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getAvailableBookAmount() {
        return availableBookAmount;
    }

    public void setAvailableBookAmount(int availableBookAmount) {
        this.availableBookAmount = availableBookAmount;
    }

}

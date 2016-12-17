package com.epam.adk.web.library.model;

import com.epam.adk.web.library.model.enums.OrderType;

import java.sql.Date;

/**
 * Order class created on 16.12.2016
 *
 * @author Kaikenov Adilhan
 **/
public class Order extends BaseEntity {

    private User user;
    private Date orderDate;
    private OrderType orderType;
    private Date from;
    private Date to;
    private boolean status;

    public Order() {
        user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

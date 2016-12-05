package com.epam.adk.web.library.model.enums;

/**
 * OrderType enumeration created on 05.12.2016
 *
 * @author Kaikenov Adilhan
 */
public enum OrderType {

    SUBSCRIPTION("Subscription"),
    READING_ROOM("Reading room");

    private String value;

    OrderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static OrderType from(String value){
        for (OrderType order : OrderType.values()){
            if (value != null && order.getValue().equalsIgnoreCase(value)){
                return order;
            }
        }
        throw new IllegalArgumentException("Error: OrderType enum class, from() method: illegal argument.");
    }
}

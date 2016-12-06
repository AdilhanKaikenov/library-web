package com.epam.adk.web.library.model.enums;

/**
 * OrderStatus enumeration created on 06.12.2016
 *
 * @author Kaikenov Adilhan
 */
public enum OrderStatus {

    ALLOWED("ALLOWED"),
    REJECTED("REJECTED"),
    CONSIDERED("CONSIDERED");

    private String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static OrderStatus from(String value){
        for (OrderStatus status : OrderStatus.values()){
            if (value != null && status.getValue().equalsIgnoreCase(value)){
                return status;
            }
        }
        throw new IllegalArgumentException("Error: OrderStatus enum class, from() method: illegal argument.");
    }
}

package com.epam.adk.web.library.model.enums;

/**
 * Role enumeration created on 27.11.2016
 *
 * @author Kaikenov Adilhan
 */
public enum  Role {

    LIBRARIAN("Librarian"),
    READER("Reader"),
    ANONYMOUS("Anonymous");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Role from(String value){
        for (Role role : Role.values()){
            if (value != null && role.getValue().equalsIgnoreCase(value)){
                return role;
            }
        }
        throw new IllegalArgumentException("Error: Role enum class, from() method: illegal argument.");
    }
}

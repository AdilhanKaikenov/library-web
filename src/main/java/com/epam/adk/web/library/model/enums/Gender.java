package com.epam.adk.web.library.model.enums;

/**
 * Gender enumeration created on 27.11.2016
 *
 * @author Kaikenov Adilhan
 */
public enum Gender {

    MALE("Male"),
    FEMALE("Female");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Gender from(String value){
        for (Gender gender : Gender.values()){
            if (value != null && gender.getValue().equalsIgnoreCase(value)){
                return gender;
            }
        }
        throw new IllegalArgumentException("Error: Gender enum class, from() method: illegal argument.");
    }
}

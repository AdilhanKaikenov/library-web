package com.epam.adk.web.library.model.enums;

/**
 * Genre enumeration created on 1.12.2016
 *
 * @author Kaikenov Adilhan
 */
public enum Genre {

    POETRY("Poetry"),
    DOCUMENTAL_LITERATURE("Documental literature"),
    DETECTIVE_AND_THRILLERS("Detectives and thrillers"),
    COMPUTERS_AND_INTERNET("Computers and Internet"),
    SCIENCE_AND_EDUCATION("Science and education");

    private String value;

    Genre(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Genre from(String value) {
        for (Genre genre : Genre.values()) {
            if (value != null && genre.getValue().equalsIgnoreCase(value)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Error: Genre enum class, from() method: illegal argument.");
    }
}

package com.epam.adk.web.library.propmanager;

import com.epam.adk.web.library.exception.PropertyManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;

/**
 * PropertyManager class created on 27.11.2016
 *
 * @author Kaikenov Adilhan
 */
public final class PropertiesManager {

    private static final Logger log = LoggerFactory.getLogger(PropertiesManager.class);

    private Properties properties;

    public PropertiesManager(String propertyFileName) throws PropertyManagerException {
        try (InputStream inputStream = PropertiesManager.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("Error: Failed to load properties file {}. Check the file name.: {}", propertyFileName, e);
            throw new PropertyManagerException(MessageFormat.format(
                    "Error: Failed to load properties file {0}. Check the file name.: {1}", propertyFileName, e));
        }
    }

    /**
     * Method to get all the values of the properties file as Map.
     *
     * @return values from properties.
     */
    public Map<String, String> getPropertiesAsMap() {
        Map<String, String> propertiesMap = new TreeMap<>();
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String value = properties.getProperty(key);
            propertiesMap.put(key, value);
        }
        return propertiesMap;
    }

    /**
     * Method to get all the values of the properties file as Collection.
     *
     * @return values from properties.
     */
    public Collection<String> getAllValues() {
        List<String> values = new ArrayList<>();
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String value = properties.getProperty(key);
            values.add((value));
        }
        return values;
    }

    /**
     * Method to get the property key
     *
     * @param key property key
     * @return property value
     */
    public String get(String key) {
        return properties.getProperty(key);
    }

}

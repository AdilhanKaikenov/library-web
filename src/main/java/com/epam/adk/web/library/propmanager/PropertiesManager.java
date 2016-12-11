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

    private static Properties properties;
    private static PropertiesManager instance;
    private static Map<String, String> propertiesMap = new TreeMap<>();

    /**
     * Method to get an instance of this class.
     *
     * @return instance of PropertiesManager.
     */
    public static PropertiesManager getInstance() {
        if (instance == null) {
            synchronized (PropertiesManager.class) {
                if (instance == null) {
                    instance = new PropertiesManager();
                }
            }
        }
        return instance;
    }

    /**
     * Method for reading resources on its name.
     *
     * @param propertyFileName name of properties.
     */
    private void load(String propertyFileName) {
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
     * @param fileName properties file name.
     * @return values from properties.
     */
    public Map<String, String> getPropertiesAsMap(String fileName) {
        load(fileName);
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
     * @param fileName properties file name.
     * @return values from properties.
     */
    public Collection<String> getAllValues(String fileName) {
        List<String> values = new ArrayList<>();
        load(fileName);
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String value = properties.getProperty(key);
            values.add((value));
        }
        return values;
    }

    /**
     * Method to get the property key.
     *
     * @param key property key.
     * @return property value.
     */
    public String get(String key) {
        return propertiesMap.get(key);
    }
}

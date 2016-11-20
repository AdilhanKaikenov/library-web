package com.epam.adk.web.library.propmanager;

import com.epam.adk.web.library.exception.PropertyManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * PropertyManager class created on 18.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class PropertyManager {

    private static final Logger log = LoggerFactory.getLogger(PropertyManager.class);

    private static PropertyManager instance;
    private static Properties properties = new Properties();

    private PropertyManager() {
    }

    public static String getProperty(String propertyFileName, String key) {
        if (instance == null) {
            synchronized (PropertyManager.class) {
                if (instance == null) {
                    instance = new PropertyManager();
                }
            }
        }
        load(propertyFileName);
        return properties.getProperty(key);
    }

    private static void load(String propertyFileName) {
        try (InputStream inputStream = PropertyManager.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("Error: Failed to load properties file '{}'. Check the file name.: {}", propertyFileName, e);
            throw new PropertyManagerException(MessageFormat.format(
                    "Error: Failed to load properties file {0}. Check the file name.: {1}", propertyFileName, e));
        }
    }
}

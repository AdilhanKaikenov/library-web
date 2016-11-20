package com.epam.adk.web.library.propmanager;

import com.epam.adk.web.library.exception.PropertyManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * DataBasePropertyManager class created on 18.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class DataBasePropertyManager {

    private static final Logger log = LoggerFactory.getLogger(DataBasePropertyManager.class);
    private static final String H2DB_PROPERTIES = "h2db.properties";

    private static DataBasePropertyManager instance;
    private static  Map<String, String> properties;

    private DataBasePropertyManager() {
        properties = getPropertiesAsMap();
    }

    /**
     * The method returns Map of properties by file name of properties.
     *
     * @return Map <key, value>
     */
    private static Map<String, String> getPropertiesAsMap() {
        log.debug("Entering getPropertiesAsMap(fileName = {})", H2DB_PROPERTIES);
        Map<String, String> result = new HashMap<>();
        Properties properties = new Properties();
        try (InputStream inputStream = DataBasePropertyManager.class.getClassLoader().getResourceAsStream(H2DB_PROPERTIES)){
            log.debug("Load property file...");
            properties.load(inputStream);
            Enumeration<?> propertyNames = properties.propertyNames();
            while (propertyNames.hasMoreElements()) {
                String key = (String) propertyNames.nextElement();
                String value = properties.getProperty(key);
                result.put(key, value);
            }
        } catch (IOException e) {
            log.error("Error: Failed to load properties file. Check the file name. '{}': {}", H2DB_PROPERTIES, e);
            throw new PropertyManagerException(MessageFormat.format(
                    "Error: Failed to load properties file. Check the file name. {0}: {1}", H2DB_PROPERTIES, e));
        }
        log.debug("Properties file '{}', size = {}", H2DB_PROPERTIES, result.size());
        return result;
    }

    public static String getProperty(String key){
        if (instance == null){
            synchronized (DataBasePropertyManager.class){
                if (instance == null){
                    instance = new DataBasePropertyManager();
                }
            }
        }
        return properties.get(key);
    }
}

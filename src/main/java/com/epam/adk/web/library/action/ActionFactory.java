package com.epam.adk.web.library.action;

import com.epam.adk.web.library.exception.ActionFactoryConfigurationException;
import com.epam.adk.web.library.exception.ActionFactoryException;
import com.epam.adk.web.library.exception.PropertyManagerException;
import com.epam.adk.web.library.propmanager.PropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * ActionFactory class created on 23.11.2016
 *
 * @author Kaikenov Adilhan
 */
public class ActionFactory {

    private static final Logger log = LoggerFactory.getLogger(ActionFactory.class);

    private static final ActionFactory instance = new ActionFactory();
    public static final String ACTION_PROPERTIES_FILE_NAME = "action.properties";

    private static Map<String, String> actions;

    private ActionFactory() {
    }

    public static void configure() throws ActionFactoryConfigurationException {
        try {
            PropertiesManager propertiesManager = new PropertiesManager(ACTION_PROPERTIES_FILE_NAME);
            actions = propertiesManager.getPropertiesAsMap();
            log.debug("Action Factory class, actions SIZE {}", actions.size());
        } catch (PropertyManagerException e) {
            throw new ActionFactoryConfigurationException("Error: ActionFactory class, configure() method.", e);
        }
    }

    public static ActionFactory getInstance(){
        return instance;
    }

    /**
     * The method which returns Action.
     *
     * @param actionName string key to determine the object is an Action.
     * @return Action.
     */
    public Action getAction(String actionName) throws ActionFactoryException {
        Action action;
        String clazz = actions.get(actionName);
        try {
            Class<?> actionClass = Class.forName(clazz);
            action = (Action) actionClass.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new ActionFactoryException("Error: ActionFactory class, getAction() method.", e);
        }
        return action;
    }
}

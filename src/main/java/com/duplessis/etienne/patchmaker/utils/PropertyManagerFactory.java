package com.duplessis.etienne.patchmaker.utils;
 
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 

public class PropertyManagerFactory {
   private static final Logger LOGGER = LoggerFactory.getLogger(PropertyManagerFactory.class); 
    private static PropertyManagerFactory instance = null;
    private static PropertiesConfiguration properties;

    private static FileChangedReloadingStrategy frls = new FileChangedReloadingStrategy();

    public PropertyManagerFactory() {
    }

    public static PropertiesConfiguration getInstance() throws ConfigurationException {
         LOGGER.info("----------------------------SEETTTINNG-----------");
        try {

            if (instance == null) {
                Object o = new Object();
                synchronized (o) {
                    if (instance == null) {
                        LOGGER.debug("Instance is null. Creating a new instance");
                        instance = new PropertyManagerFactory(); 
                        properties = new PropertiesConfiguration();
                        properties.setDelimiterParsingDisabled(true); 
                        properties.load("application.properties");
                        frls.setRefreshDelay(10000);
                        properties.setReloadingStrategy(frls);
                    }
                }
            }
            LOGGER.debug("Returning properties=" + properties);
            return properties;
        } catch (ConfigurationException ce) {
            String error = "ConfigurationException:" + ce.getMessage();
            throw ce;
        }
    }
    
    	
}
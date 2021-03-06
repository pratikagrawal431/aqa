/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.common;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * Web application lifecycle listener.
 *
 * @author Chhavi kumar.b
 */
public class ContextListener implements ServletContextListener {

    Logger logger = null;
   
    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        logger.info("Context Initialized.");
        System.out.println("Context Initializing...");
        String configPath = System.getProperty("api.ump.props");
        if(StringUtils.isBlank(configPath)){
          configPath = "E:\\aqa\\API\\config\\api.properties";
        }
        System.out.println("configPath*********"+configPath); 
       configPath = "/api/config/api.properties";
        if (configPath != null && configPath.length() > 0) {
            ConfigUtil configUtil = new ConfigUtil();
            boolean isInit = configUtil.init(configPath);
            if (isInit) {
                System.setProperty("props.file.path", configPath);
                PropertyConfigurator.configureAndWatch(ConfigUtil.getProperty("api.log4j.path", "/apps/oh-policy-mgr/config/log4j.properties"), Long.valueOf(ConfigUtil.getProperty("api.ump.log4j.ideal.timeout", "6000")));
                logger = Logger.getLogger(ContextListener.class);
                logger.info("UMP API Configurations loaded.");
                if (logger.isDebugEnabled()) {
                    logger.debug(ConfigUtil.getProperty("api.ump.log4j.path", ""));
                }
              


            } else {
                System.out.println("Configurations were not loaded.SO we are going to shut down the tomcat.");
                System.exit(0);
            }
        } else {
            logger.info("Configuration path is empty or coming null");
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Application Context is destroyed.");
    }
}

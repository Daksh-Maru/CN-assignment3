package com.my_first_http_server.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.my_first_http_server.util.json;

public class ConfigurationManager {

    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        if(myConfigurationManager == null) {
            myConfigurationManager = new ConfigurationManager();
        }
        return myConfigurationManager;
    }
    /* 
     * Used to load a configuration file by the path provided.
     */

    @SuppressWarnings("resource")
    public void loadConfigurationFile(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }


        StringBuffer sb = new StringBuffer();
        int i;
        try {
            while( (i = fileReader.read()) != -1) {
                sb.append((char)i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException();
        }

        JsonNode conf;
        try {
            /*The string we received from the web converts it to jsonNode*/
            conf = json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the Configuration File", e);
        }
        
        try {
            /*myCurrentConfiguration stores the object value from the jsonNode */
            myCurrentConfiguration = json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the Configuration file, internal", e);
        }
    }
    /*
     * Returns the current loaded Configuration
     */
    public Configuration getCurrentConfiguration() {
        if(myCurrentConfiguration == null) {
            throw new HttpConfigurationException("No Current Configuration Set.");
        }
        return myCurrentConfiguration;
    }
}

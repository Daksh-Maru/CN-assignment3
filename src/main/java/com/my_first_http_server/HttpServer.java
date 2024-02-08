package com.my_first_http_server;

import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.my_first_http_server.config.Configuration;
import com.my_first_http_server.config.ConfigurationManager;
import com.my_first_http_server.core.ServerListenerThread;

public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) {
        LOGGER.info("Server Starting>>>");

        /*This will convert the port and webroot into an object. */
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        /*This will fetch that object in conf. */
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using port: " + conf.getPort());
        LOGGER.info("Using WebRoot: " + conf.getWebroot());

        ServerListenerThread serverListenerThread = null;
        try {
            serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
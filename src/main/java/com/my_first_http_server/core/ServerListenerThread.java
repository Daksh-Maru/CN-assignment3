package com.my_first_http_server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerListenerThread extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    private int port;
    private ServerSocket serverSocket;
    public ServerListenerThread(int port, String webRoot) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {
        /*Listens to specific port */
        try {
            /*
             * In this process multiple requests made will be delayed as they would
             * be put up on a queue.
             */
            while(serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                /*At this point it will wait for the connection to take plase
                 * If theres no connection it doesn't execute further.*/
                LOGGER.info(" * Connection accepted : " + socket.getInetAddress());

                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();
            }
        } catch (IOException e) {
            LOGGER.error("Problem with setting socket", e);
        } finally {
            if(serverSocket!=null)
                try {
                    serverSocket.close();
                } catch (IOException e) {}
        }

    }
}

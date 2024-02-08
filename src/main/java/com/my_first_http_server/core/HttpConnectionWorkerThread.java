package com.my_first_http_server.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConnectionWorkerThread extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);


    private Socket socket;
    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            
            // int _byte;
            // while( (_byte = inputStream.read()) >= 0) {
            //     System.out.print((char)_byte);
            // }

            String html = "<html><head><title>Simple java HTTP server</title> <body><h1> This page was served using my Simple Java HTTP Server</h1></body></head></html>";
    
            final String CRLF = "\r\n"; //13,10;
            /*The browser wouldn't know what to do with it. -> For this it should be wrapped with http response*/
            String response = 
            "HTTP/1.1 200 OK" + CRLF + //Status Line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
            "Content-Length: " + html.getBytes().length + CRLF + CRLF + // HEADER
            html + CRLF + CRLF;
    
            outputStream.write(response.getBytes());

            // TODO Handle close 

            LOGGER.info("Connection Processing Finished.");
        } catch(IOException e) {
            LOGGER.error("Problem with communication", e);
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }

            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }

            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {}
            }   
        }
        
    }
}

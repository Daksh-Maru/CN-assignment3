package com.my_first_http_server.http;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HttpVersionTest {
    @Test
    void getBestCompatibleVersionExactMatch() {
        HttpVersion version = null;
        try {
            version = HttpVersion.getBestCompatibleVersion("HTTP/1.1");
        } catch (BadHttpVersionException e) {
            fail();
        }
        assertNotNull(version);
        assertEquals(version, HttpVersion.HTTP_1_1);
    }   

    @Test
    void getBestCompatibleVersionBadFormat() {
        HttpVersion version = null;
        try {
            version = HttpVersion.getBestCompatibleVersion("http/1.1");
            fail();
        } catch (BadHttpVersionException e) {
        
        }

    }

    @Test
    void getBestCompatibleVersionHigherVersion() {
        HttpVersion version = null;
        try {
            version = HttpVersion.getBestCompatibleVersion("http/1.2");
            assertNotNull(version);
            assertEquals(version, HttpVersion.HTTP_1_1);
        } catch (BadHttpVersionException e) {
            
        }

    }   
}

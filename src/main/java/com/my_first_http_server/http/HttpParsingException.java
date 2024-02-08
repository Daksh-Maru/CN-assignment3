package com.my_first_http_server.http;

public class HttpParsingException extends Exception {
    private final HttpStatusCode errorcode;

    public HttpParsingException(HttpStatusCode errorCode) {
        super(errorCode.MESSAGE);
        this.errorcode = errorCode;
    }

    public HttpStatusCode getErrorCode() {
        return errorcode;
    }
}

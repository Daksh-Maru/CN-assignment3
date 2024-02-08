package com.my_first_http_server.http;

public class HttpRequest extends HttpMessage{
    
    private HttpMethod method;
    private String requestTarget;
    private String originalhttpVersion; // literal from the request.

    public String getOriginalhttpVersion() {
        return originalhttpVersion;
    }

    private HttpVersion bestCompatibleHttpVersion;

    public HttpVersion getBestCompatibleHttpVersion() {
        return bestCompatibleHttpVersion;
    }
    HttpRequest() {
        
    }
    public String getRequestTarget() {
        return requestTarget;
    }
    
    public HttpMethod getMethod() {
        return method;
    }



    void setMethod(String methodName) throws HttpParsingException {
        for(HttpMethod method : HttpMethod.values()) {
            if(methodName.equals(method.name())) {
                this.method = method;
                return;
            }
        }
        throw new HttpParsingException(
            HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED
        );
    }

    void setRequestTarget(String requestTarget) throws HttpParsingException {
        if(requestTarget == null || requestTarget.length() == 0) {
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        }
        this.requestTarget = requestTarget;
    }

    void setHttpVersion(String originalHttpVersion) throws BadHttpVersionException, HttpParsingException {
        this.originalhttpVersion = originalHttpVersion;
        this.bestCompatibleHttpVersion = HttpVersion.getBestCompatibleVersion(originalHttpVersion);
        if(this.bestCompatibleHttpVersion == null) {
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
        }
    }

    
}

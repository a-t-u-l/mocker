package beans;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by atul on 19/06/17.
 */
public class MockRequest {

    private String requestType; //GET,POST
    @NotNull
    private String URI;
    private List<PathParamEntity> pathParams=new ArrayList<>();
    private Map<String,String> queryParams= new HashMap<>();
    private Map<String,String []> headers= new HashMap<>();
    private String requestBody="";
    private String responseBody="";
    private int responseStatusCode =200;

    public MockRequest() {
    }

    public MockRequest(String requestType, String URI, Map<String, String> queryParams, Map<String, String []> headers, String requestBody, String responseBody, int responseStatusCode) {
        this.requestType = requestType;
        this.URI = URI;
        this.queryParams = queryParams;
        this.headers = headers;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
        this.responseStatusCode = responseStatusCode;
    }

    public MockRequest(String requestType, String URI, List<PathParamEntity> pathParams, Map<String, String> queryParams, Map<String, String []> headers, String requestBody, String responseBody, int responseStatusCode) {
        this.requestType = requestType;
        this.URI = URI;
        this.pathParams = pathParams;
        this.queryParams = queryParams;
        this.headers = headers;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
        this.responseStatusCode = responseStatusCode;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public List<PathParamEntity> getPathParams() {
        return pathParams;
    }

    public void setPathParams(List<PathParamEntity> pathParams) {
        this.pathParams = pathParams;
    }

    public Map<String,String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map queryParams) {
        this.queryParams = queryParams;
    }

    public Map<String, String []> getHeaders() {
        return headers;
    }

    public void setHeaders(Map headers) {
        this.headers = headers;
    }

    public int getResponseStatusCode() {
        return responseStatusCode;
    }

    public void setResponseStatusCode(int responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}

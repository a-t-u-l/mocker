package beans;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by atul on 22/06/17.
 */
public class FormRequest {

    private String requestType=""; //GET,POST
    private String URL ="";
    private Map<String,String> queryParams= new HashMap<>();
    private Map<String,String []> headers= new HashMap<>();
    private String headerString;
    private String requestBody="";
    private String responseBody="";
    private int responseStatusCode =200;

    public FormRequest() {
    }

    public FormRequest(String requestType, String URL, String responseBody, int responseStatusCode) {
        this.requestType = requestType;
        this.URL = URL;
        this.responseBody = responseBody;
        this.responseStatusCode = responseStatusCode;
    }

    public FormRequest(String requestType, String URL, String requestBody, String responseBody, int responseStatusCode) {
        this.requestType = requestType;
        this.URL = URL;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
        this.responseStatusCode = responseStatusCode;
    }

    public FormRequest(String requestType, String URL, Map<String, String> queryParams, String responseBody, int responseStatusCode) {
        this.requestType = requestType;
        this.URL = URL;
        this.queryParams = queryParams;
        this.responseBody = responseBody;
        this.responseStatusCode = responseStatusCode;
    }

    public FormRequest(String requestType, String URL, Map<String, String> queryParams, Map<String, String []> headers, String requestBody, String responseBody, int responseStatusCode) {
        this.requestType = requestType;
        this.URL = URL;
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

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public Map<String, String []> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String []> headers) {
        this.headers = headers;
    }

    public String getHeaderString() {
        return headerString;
    }

    public void setHeaderString(String headerString) {
        this.headerString = headerString;
        if(headers==null)
            headers = new HashMap<>();
        if(headerString!=null) {
            String[] headerRows = headerString.split("\\r?\\n");
            for (String headerPair : headerRows) {
                String[] headerData = headerPair.split(":");
                if (headerData.length == 2) {
                    String [] headerValues = headerData[1].split(",");
                    headers.put(headerData[0].trim(), headerValues);
                }
            }
        }
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

    public int getResponseStatusCode() {
        return responseStatusCode;
    }

    public void setResponseStatusCode(int responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }
}

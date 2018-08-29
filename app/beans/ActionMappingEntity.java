package beans;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

/**
 * Created by atul on 19/06/17.
 */
public class ActionMappingEntity {

    private String actionName;
    private String httpMethodType;
    private String path;
    private Map<String,String[]> queryParams;
    private String uri;
    private JsonNode jsonBody;
    private Map<String,String[]> headers;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getHttpMethodType() {
        return httpMethodType;
    }

    public void setHttpMethodType(String httpMethodType) {
        this.httpMethodType = httpMethodType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String[]> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, String[]> queryParams) {
        this.queryParams = queryParams;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public JsonNode getJsonBody() {
        return jsonBody;
    }

    public void setJsonBody(JsonNode jsonBody) {
        this.jsonBody = jsonBody;
    }

    public Map<String, String[]> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String[]> headers) {
        this.headers = headers;
    }
}

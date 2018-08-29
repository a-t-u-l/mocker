package beans;

/**
 * Created by atul on 20/06/17.
 */
public class RouteMappingEntity {
    private String requestType;
    private String path;
    private String method;

    public RouteMappingEntity(String requestType, String path, String method) {
        this.requestType = requestType;
        this.path = path;
        this.method = method;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}

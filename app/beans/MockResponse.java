package beans;

/**
 * Created by atul on 20/06/17.
 */
public class MockResponse {
    private String message;
    boolean created;
    String uri;
    String body;

    public MockResponse(String message, boolean created) {
        this.message = message;
        this.created = created;
    }

    public MockResponse(String message, String uri) {
        this.message = message;
        this.uri = uri;
    }

    public MockResponse(String message, boolean created, String uri) {
        this.message = message;
        this.created = created;
        this.uri = uri;
    }

    public MockResponse(String message, boolean created, String uri, String body) {
        this.message = message;
        this.created = created;
        this.uri = uri;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

package beans;

import java.util.Map;

/**
 * Created by atul on 20/06/17.
 */
public class MockResponse {
    private String message;
    private boolean created;
    private String uri;
    private String body;
    private String mockId;
    private Map<String, String []> headers;

    public MockResponse(String message, boolean created) {
        this.message = message;
        this.created = created;
    }

    public MockResponse(String message, String mockId, String uri, Map<String, String []> headers) {
        this.message = message;
        this.uri = uri;
        this.headers = headers;
        this.mockId = mockId;
    }

    public MockResponse(String message, boolean created, String mockId, String uri, Map<String, String []> headers) {
        this.message = message;
        this.created = created;
        this.uri = uri;
        this.headers = headers;
        this.mockId = mockId;
    }

    public MockResponse(String message, boolean created, String mockId, String uri, Map<String, String []> headers, String body) {
        this.message = message;
        this.created = created;
        this.uri = uri;
        this.body = body;
        this.headers = headers;
        this.mockId = mockId;
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

    public String getMockId() {
        return mockId;
    }

    public void setMockId(String mockId) {
        this.mockId = mockId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, String[]> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String[]> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

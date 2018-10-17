package beans;

import play.mvc.Result;

/**
 * Created by AtulSharma on 17/10/18
 */
public class GeneratorResponse {

    private String mockId;
    private Result result;

    public GeneratorResponse(String mockId, Result result) {
        this.mockId = mockId;
        this.result = result;
    }

    public String getMockId() {
        return mockId;
    }

    public void setMockId(String mockId) {
        this.mockId = mockId;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}

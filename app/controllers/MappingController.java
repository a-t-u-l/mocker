package controllers;

import com.google.gson.Gson;
import core.Constant;
import core.ResultMappingHolder;
import play.mvc.Controller;
import play.mvc.Result;
import utils.FileUtils;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by atul on 21/06/17.
 */
public class MappingController extends Controller {

    private Gson gson = new Gson();

    @Inject
    public MappingController() {
    }

    public Result getResultContainer() {
        ResultMappingHolder resultMappingHolder = ResultMappingHolder.getInstance();
        String mapping = "{ \"mockedApiList\" : [";
        for (String mockId : resultMappingHolder.getMockIdVsResultKeyMap().keySet()) {
            String path;
            String requestBody = null;
            String resultMapKey = resultMappingHolder.getMockIdVsResultKeyMap().get(mockId);
            if (resultMapKey.indexOf("{") > resultMapKey.length() || !resultMapKey.contains("{"))
                path = resultMapKey;
            else {
                path = resultMapKey.substring(0, resultMapKey.indexOf("{"));
                requestBody = mockId.substring(resultMapKey.indexOf("{"), resultMapKey.length());
            }
            String[] response = resultMappingHolder.getResultForMockId(mockId);
            Map<String, String[]> headers = resultMappingHolder.getHeadersForMockId(mockId);
            mapping = mapping
                    + "{\"requestPath\":\"" + path + "\","
                    + "\"requestHeaders\": " + gson.toJson(headers) + ","
                    + "\"mockId\": \"" + mockId + "\","
                    + "\"requestBody\":" + requestBody + ","
                    + "\"responseStatus\":" + response[0] + ","
                    + "\"responseBody\":" + response[1] + "},";
        }
        mapping = FileUtils.replaceLast(mapping, ",", "");
        mapping = mapping + "]}";
        return ok(mapping).as(Constant.APPLICATION_JSON.value());
    }
}

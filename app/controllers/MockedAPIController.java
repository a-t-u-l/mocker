package controllers;

import beans.ActionMappingEntity;
import beans.MockResponse;
import core.Constant;
import core.RequestMappingHolder;
import core.ResultMappingHolder;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Debugger;

import javax.inject.Inject;

/**
 * Created by atul on 19/06/17.
 */
public class MockedAPIController extends Controller {

    @Inject
    public MockedAPIController() {
    }

    public Result getMappedResponseForGetCall() {
        return getCall();
    }

    public Result getMappedResponseForOnePathParamGetCall(String var1) {
        return getCall();
    }

    public Result getMappedResponseForTwoPathParamGetCall(String var1, String var2) {
        return getCall();
    }

    public Result getMappedResponseForThreePathParamGetCall(String var1, String var2, String var3) {
        return getCall();
    }

    public Result getMappedResponseForPostCall() {
        return postCall();
    }

    public Result getMappedResponseForOnePathParamPostCall(String var1) {
        return postCall();
    }

    public Result getMappedResponseForTwoPathParamPostCall(String var1, String var2) {
        return postCall();
    }

    public Result getMappedResponseForThreePathParamPostCall(String var1, String var2, String var3) {
        return postCall();
    }

    private Result getCall(){
        RequestMappingHolder requestObject = RequestMappingHolder.getInstance();
        ActionMappingEntity actionData = requestObject.getActionMappingEntity();
        Debugger.console("Map : " + ResultMappingHolder.getInstance().toString());
        Debugger.console("uri : " + actionData.getUri());
        String[] response = ResultMappingHolder.getInstance().getResultMapping(actionData);
        if (response == null) {
            MockResponse mockResponse = new MockResponse("No Constant mapped to URI.", null, actionData.getUri(), actionData.getHeaders());
            return notFound(Json.toJson(mockResponse));
        }
        return status(Integer.parseInt(response[0]), response[1]).as(Constant.APPLICATION_JSON.value());
    }

    private Result postCall(){
        RequestMappingHolder requestObject = RequestMappingHolder.getInstance();
        ActionMappingEntity actionData = requestObject.getActionMappingEntity();
        String[] response = ResultMappingHolder.getInstance().getResultMapping(actionData);
        if (response == null) {
            MockResponse mockResponse = new MockResponse("No Constant mapped to URI.", null, actionData.getUri(), actionData.getHeaders());
            return notFound(Json.toJson(mockResponse));
        }
        return status(Integer.parseInt(response[0]), response[1]).as(Constant.APPLICATION_JSON.value());
    }


    //TODO: Add support for more types of HTTP Request e.g. PUT, DELETE etc

}

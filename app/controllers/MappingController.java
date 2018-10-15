package controllers;

import core.Constant;
import core.ResultMappingHolder;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.FileUtils;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by atul on 21/06/17.
 */
public class MappingController extends Controller {

    @Inject
    public MappingController(){
    }

    //TODO: Add requestType:{GET,POST etc} in response body
    public Result getResultContainer(){
        ResultMappingHolder resultMappingHolder=ResultMappingHolder.getInstance();
        String mapping="{ \"mockedApiList\" : [";
        for(String key : resultMappingHolder.getResultMapping().keySet()){
            String path;
            String requestBody=null;
            if(key.indexOf("{")>key.length() || key.indexOf("{")==-1)
                path = key;
            else {
                path = key.substring(0, key.indexOf("{"));
                requestBody = key.substring(key.indexOf("{"),key.length());
            }
            String [] response=resultMappingHolder.getResultMapping(key);
            mapping=mapping+"{\"requestPath\":\""+path+"\","+"\"requestBody\":"+requestBody+","+"\"responseStatus\":"
                    +response[0]+","+"\"responseBody\":"+response[1]+"},";
        }
        mapping=FileUtils.replaceLast(mapping,",","");
        mapping=mapping+"]}";
        return ok(mapping).as(Constant.APPLICATION_JSON.value());
    }
}

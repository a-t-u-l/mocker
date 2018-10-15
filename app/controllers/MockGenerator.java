package controllers;

import beans.FormRequest;
import beans.MockRequest;
import beans.MockResponse;
import beans.RouteMappingEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import core.Constant;
import core.ResultMappingHolder;
import core.RouteMappingHolder;
import core.URLMaker;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Debugger;
import utils.FileUtils;

import javax.inject.Inject;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import views.html.*;


/**
 * Created by atul on 19/06/17.
 */
public class MockGenerator extends Controller {

    Gson GSON=new Gson();

    @Inject
    public MockGenerator() {
    }

    public Result generateMockForm(){
        Form<FormRequest> mockRequestForm =Form.form(FormRequest.class).fill(new FormRequest());
        return ok(generator.render(mockRequestForm));
    }

    public Result generateMockFromForm(){
        Form<FormRequest> mockRequestForm = Form.form(FormRequest.class).bindFromRequest();
        MockRequest mockRequest;
        try {
            mockRequest = makeInstance(mockRequestForm.get());
        } catch (IndexOutOfBoundsException e) {
            flash(Constant.ERROR.value(), "URL is not properly formatted : "+e.getMessage());
            return redirect(Constant.PATH_GENERATOR.value());
        }
        catch (IOException ioe){
            flash(Constant.ERROR.value(), "Request/Response Body is not in JSON format : "+ioe.getMessage());
            return redirect(Constant.PATH_GENERATOR.value());
        }
        Debugger.console("Request path : "+mockRequest.getURI());
        Debugger.console("Request type : "+mockRequest.getRequestType());
        Debugger.console("Request body : "+mockRequest.getRequestBody());
        Debugger.console("response code : "+mockRequest.getResponseStatusCode());
        Debugger.console("response body : "+mockRequest.getResponseBody());
        Result result=generate(mockRequest);
        if(result.status()== Constant.STATUS_OK.asInteger())
            flash(Constant.SUCCESS.value(), "Successfully generated the mock API.");
        else if(result.status()==Constant.STATUS_CONFLICT.asInteger())
            flash(Constant.CONFLICT.value(), "Mock API Already Exist.");
        else
            flash(Constant.ERROR.value(), "Failed to generate the mock API.");
        return redirect(Constant.PATH_GENERATOR.value());
    }

    private void requestProcessor(){

    }

    /**
     * Returns a Mock Request instance created from the formdata data.
     * Assumes that the formData has been validated.
     * The ID field is not assigned or managed in this application.
     * @param formRequest The student formdata data.
     * @return A MockRequest instance.
     */
    private MockRequest makeInstance(FormRequest formRequest) throws IndexOutOfBoundsException,IOException{

        Debugger.console("Form Request path : "+formRequest.getURL());
        Debugger.console("Form Request type : "+formRequest.getRequestType());
        Debugger.console("Form Request body : "+formRequest.getRequestBody());
        Debugger.console("Form response code : "+formRequest.getResponseStatusCode());
        Debugger.console("Form response body : "+formRequest.getResponseBody());

        MockRequest mockRequest = new MockRequest();
        mockRequest.setRequestType(formRequest.getRequestType());
        if(formRequest.getURL()==null || formRequest.getURL().isEmpty())
            throw new IndexOutOfBoundsException("A Path can't be empty.");
        if(!formRequest.getURL().startsWith("/"))
            throw new IndexOutOfBoundsException("A Path must start with forward slash.");
        List<String> formatURL=FileUtils.dataSplitter(formRequest.getURL(),"\\?");
        if(formatURL.size()>2)
            throw new IndexOutOfBoundsException("A Path must be properly constructed");
        mockRequest.setURI(formatURL.get(0));
        if(formatURL.size()==2)
            mockRequest.setQueryParams(getQueryParams(formatURL.get(1)));
        JsonNode requestNode;
        try {
            if(formRequest.getRequestType().equalsIgnoreCase(Constant.POST.value())) {
                requestNode = (new ObjectMapper()).readTree(formRequest.getRequestBody());
                mockRequest.setRequestBody(requestNode.toString());
            }
        } catch (IOException e) {
            throw new IOException("Please validate request JSON.");
        }
        JsonNode responseNode;
        try {
            responseNode = (new ObjectMapper()).readTree(formRequest.getResponseBody());
        } catch (IOException e) {
            throw new IOException("Please validate response JSON.");
        }
        mockRequest.setResponseBody(responseNode.toString());
        mockRequest.setResponseStatusCode(formRequest.getResponseStatusCode());
        return mockRequest;
    }

    private static Map<String, String> getQueryParams(String queryParamString) throws IndexOutOfBoundsException{
        Map<String, String> queryParams=new HashMap<>();
        List<String> params=FileUtils.dataSplitter(queryParamString,"&");
        for(String param:params){
            List<String> kvPair=FileUtils.dataSplitter(param,"=");
            if(kvPair.size()!=2)
                throw new IndexOutOfBoundsException("A Single query param must have a single key and value/s");
            queryParams.put(kvPair.get(0),kvPair.get(1));
        }
        return queryParams;
    }

    //TODO: Add support for more types of HTTP Request e.g. PUT, DELETE etc
    //TODO: Move String mappings to a interface or an ENUM
    public Result generateMock() {
        MockRequest request=GSON.fromJson(request().body().asJson().toString(),MockRequest.class);
        return generate(request);
    }

    private Result generate(MockRequest request){
        URLMaker url = new URLMaker(request);
        String body=request.getRequestBody().replace("\\","");
        ResultMappingHolder mappingHolder=ResultMappingHolder.getInstance();
        RouteMappingHolder routeMappingHolder=RouteMappingHolder.getInstance();
        String fullMethodPath;
        try{
            if (request.getRequestType().equalsIgnoreCase(Constant.GET.value())) {
                fullMethodPath = Constant.METHOD_MAPPING_GET_CALL.value();
                if (!routeMappingHolder.lookUp(Constant.GET.value(), url.getPath())) {
                    FileUtils.appendInFile(request.getRequestType() + Constant.TAB.repeat(2) + url.getPath() + Constant.TAB.repeat(4) + fullMethodPath, Constant.FILE_ROUTE.value());
                    routeMappingHolder.setRouteMappingEntity(new RouteMappingEntity(Constant.GET.value(),url.getPath(),fullMethodPath));
                }
            }
            else{
                fullMethodPath = Constant.METHOD_MAPPING_POST_CALL.value();
                if (!routeMappingHolder.lookUp(Constant.POST.value(), url.getPath())){
                    FileUtils.appendInFile(request.getRequestType() + Constant.TAB.repeat(2) + url.getPath() + Constant.TAB.repeat(4) + fullMethodPath, Constant.FILE_ROUTE.value());
                    routeMappingHolder.setRouteMappingEntity(new RouteMappingEntity(Constant.POST.value(),url.getPath(),fullMethodPath));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            MockResponse response=new MockResponse("Error while writing in routes",false);
            return internalServerError(Json.toJson(response));
        }
        if (request.getRequestType().equalsIgnoreCase(Constant.POST.value())) {
            if(mappingHolder.mappingExists(url.getUri()+body)) {
                MockResponse response=new MockResponse("Mapping Already exist. Use following identifier to access your mocked API",false,url.getUri(),body);
                return status(Constant.STATUS_CONFLICT.asInteger(), Json.toJson(response));
            }
            else {
                if(request.getHeaders() != null && !request.getHeaders().isEmpty())
                    mappingHolder.setResultMapping(url.getUri(), body, request.getHeaders(), String.valueOf(request.getResponseStatusCode()), request.getResponseBody());
                else
                    mappingHolder.setResultMapping(url.getUri(), body, String.valueOf(request.getResponseStatusCode()), request.getResponseBody());
            }
        }
        else {
            if(mappingHolder.mappingExists(url.getUri())) {
                MockResponse response=new MockResponse("Mapping Already exist. Use following identifier to access your mocked API",false,url.getUri(),body);
                return status(Constant.STATUS_CONFLICT.asInteger(), Json.toJson(response));
            }
            else{
                if(request.getHeaders() != null && !request.getHeaders().isEmpty())
                    mappingHolder.setResultMapping(url.getUri(), request.getHeaders(), String.valueOf(request.getResponseStatusCode()), request.getResponseBody());
                else
                    mappingHolder.setResultMapping(url.getUri(), String.valueOf(request.getResponseStatusCode()), request.getResponseBody());
            }
        }
        ResultMappingHolder object = ResultMappingHolder.getInstance();
        String filename = Constant.FILE_SERIALIZE.value();
        Debugger.console("Serializing value :\n"+object.toString());
        serializeResult(filename, object);
        MockResponse response=new MockResponse("Successfully generated the mock. Use following identifier to access your mocked API.",true,url.getUri(),body);
        return ok(Json.toJson(response));
    }

    private void serializeResult(String filename, Object object){
        try
        {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(object);
            out.close();
            file.close();
            Debugger.console("Object has been serialized");
        }
        catch(IOException ex)
        {
            Debugger.console("IOException is caught");
        }
    }
}
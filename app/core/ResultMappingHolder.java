package core;

import utils.Debugger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by atul on 19/06/17.
 */
public class ResultMappingHolder implements java.io.Serializable{

    private static ResultMappingHolder instance;
    private Map<String,String []> resultMap;

    private ResultMappingHolder(){
    }

    public static ResultMappingHolder getInstance(){
        if(instance==null) {
            instance = new ResultMappingHolder();
            instance.resultMap = new ConcurrentHashMap<>();
        }
        return instance;
    }

    public synchronized void setResultMapping(String uri, String statusCode, String value){
        String [] values={statusCode,value};
        resultMap.put(uri,values);
        Debugger.console("key : "+uri+",\nvalue : "+value);
    }

    public synchronized void setResultMapping(String uri, Map<String, String []> headers, String statusCode, String value){
        String [] values={statusCode,value};
        String key = uri.concat(headersToString(headers));
        resultMap.put(key,values);
        Debugger.console("key : "+key+",\nvalue : "+value);
    }

    public synchronized void setResultMapping(String uri, String body, String statusCode, String value){
        String [] values={statusCode,value};
        String key = uri.concat(body);
        resultMap.put(key,values);
        Debugger.console("key : "+key+",\nvalue : "+value);
    }

    public synchronized void setResultMapping(String uri, String body, Map<String, String []> headers,  String statusCode, String value){
        String [] values={statusCode,value};
        String key = uri.concat(body).concat(headersToString(headers));
        resultMap.put(key,values);
        Debugger.console("key : "+key+",\nvalue : "+value);
    }

    public void setResultMapping(Map<String, String[]> resultMap) {
        this.resultMap = resultMap;
    }

    public String [] getResultMapping(String uri){
        Debugger.console("Checking weather "+uri+" exist in container? : "+resultMap.containsKey(uri));
        return resultMap.get(uri);
    }

    public String [] getResultMapping(String uri, Map<String, String []> headers){
        String key = uri.concat(headersToString(headers));
        Debugger.console("Checking weather "+key+" exist in container? : "+resultMap.containsKey(key));
        return resultMap.get(key);
    }

    public String [] getResultMapping(String uri, String body){
        String key = uri.concat(body);
        Debugger.console("Checking weather "+key+" exist in container? : "+resultMap.containsKey(key));
        return resultMap.get(key);
    }

    public String [] getResultMapping(String uri, String body, Map<String, String []> headers){
        String key = uri.concat(body).concat(headersToString(headers));
        Debugger.console("Checking weather "+key+" exist in container? : "+resultMap.containsKey(key));
        return resultMap.get(key);
    }

    public Map<String, String[]> getResultMapping() {
        return resultMap;
    }

    public boolean mappingExists(String key){
        Debugger.console("Does "+key+" exist in container? : "+resultMap.containsKey(key));
        return resultMap.containsKey(key);
    }

    private String headersToString(Map<String , String []> headers){
        removeCommonHeaders(headers);
        String headerString = "";
        for(String key : headers.keySet()){
            headerString = headerString.concat(key).concat(Arrays.toString(headers.get(key)));
        }
        return headerString;
    }

    private Map<String ,String []> removeCommonHeaders(Map<String , String []> headers){
        headers.remove("Host");
        headers.remove("Connection");
        headers.remove("User-Agent");
        headers.remove("Accept-Encoding");
        return headers;
    }

    public String toString(){
        String str="{\n";
        for(String k: resultMap.keySet()){
            str=str+"\t\""+k+"\" : \""+resultMap.get(k)+"\",\n";
        }
        str=str+"}";
        return str;
    }
}

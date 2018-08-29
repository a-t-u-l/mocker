package core;

import utils.Debugger;

import java.util.HashMap;
import java.util.Map;

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
            instance.resultMap = new HashMap<>();
        }
        return instance;
    }

    public synchronized void setResultMapping(String key, String statusCode, String value){
        String [] values={statusCode,value};
        resultMap.put(key,values);
        Debugger.console("key : "+key+",\nvalue : "+value);
    }

    public void setResultMapping(Map<String, String[]> resultMap) {
        this.resultMap = resultMap;
    }

    public String [] getResultMapping(String key){
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

    public String toString(){
        String str="{\n";
        for(String k: resultMap.keySet()){
            str=str+"\t\""+k+"\" : \""+resultMap.get(k)+"\",\n";
        }
        str=str+"}";
        return str;
    }
}

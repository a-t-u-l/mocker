package core;

import beans.ActionMappingEntity;
import beans.MockRequest;
import utils.Debugger;
import utils.FileUtils;
import utils.HelperUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by atul on 19/06/17.
 */
public class ResultMappingHolder implements java.io.Serializable {

    private static ResultMappingHolder instance;
    private Map<String, String[]> resultMap;
    private Map<String, String> mockIdVsResultKeyMap;
    private Map<String, List<String>> resultKeyVsMockIdsMap;
    private Map<String, Map<String, String[]>> headerStore;

    private ResultMappingHolder() {
    }

    public static ResultMappingHolder getInstance() {
        if (instance == null) {
            instance = new ResultMappingHolder();
            instance.resultMap = new ConcurrentHashMap<>();
            instance.headerStore = new ConcurrentHashMap<>();
            instance.mockIdVsResultKeyMap = new ConcurrentHashMap<>();
            instance.resultKeyVsMockIdsMap = new ConcurrentHashMap<>();
        }
        return instance;
    }

    public synchronized String setResultMapping(String uri, String statusCode, String value) {
        Debugger.console("creating new get mapping without headers ...");
        String[] values = {statusCode, value};
        String mockId = HelperUtil.randomAlphaNumeric(12);
        mockIdVsResultKeyMap.put(mockId, uri);
        if (resultKeyVsMockIdsMap.containsKey(uri))
            resultKeyVsMockIdsMap.get(uri).add(mockId);
        else {
            List<String> mockIds = new ArrayList<>();
            mockIds.add(mockId);
            resultKeyVsMockIdsMap.put(uri, mockIds);
        }
        resultMap.put(mockId, values);
        Debugger.console("key : " + uri + ", value : " + value + ", mockId : " + mockId);
        return mockId;
    }

    public synchronized String setResultMapping(String uri, Map<String, String[]> headers, String statusCode, String value) {
        Debugger.console("creating new get mapping with headers ...");
        String[] values = {statusCode, value};
        String mockId = HelperUtil.randomAlphaNumeric(12);
        mockIdVsResultKeyMap.put(mockId, uri);
        if (resultKeyVsMockIdsMap.containsKey(uri))
            resultKeyVsMockIdsMap.get(uri).add(mockId);
        else {
            List<String> mockIds = new ArrayList<>();
            mockIds.add(mockId);
            resultKeyVsMockIdsMap.put(uri, mockIds);
        }
        headerStore.put(mockId, headers);
        resultMap.put(mockId, values);
        Debugger.console("key : " + uri + ", value : " + value + ", mockId : " + mockId);
        return mockId;
    }

    public synchronized String setResultMapping(String uri, String body, String statusCode, String value) {
        String[] values = {statusCode, value};
        String key = uri.concat(body);
        String mockId = HelperUtil.randomAlphaNumeric(12);
        mockIdVsResultKeyMap.put(mockId, key);
        if (resultKeyVsMockIdsMap.containsKey(uri))
            resultKeyVsMockIdsMap.get(key).add(mockId);
        else {
            List<String> mockIds = new ArrayList<>();
            mockIds.add(mockId);
            resultKeyVsMockIdsMap.put(key, mockIds);
        }
        resultMap.put(mockId, values);
        Debugger.console("key : " + key + ", value : " + value + ", mockId : " + mockId);
        return mockId;
    }

    public synchronized String setResultMapping(String uri, String body, Map<String, String[]> headers, String statusCode, String value) {
        String[] values = {statusCode, value};
        String key = uri.concat(body);
        String mockId = HelperUtil.randomAlphaNumeric(12);
        mockIdVsResultKeyMap.put(mockId, key);
        if (resultKeyVsMockIdsMap.containsKey(uri))
            resultKeyVsMockIdsMap.get(key).add(mockId);
        else {
            List<String> mockIds = new ArrayList<>();
            mockIds.add(mockId);
            resultKeyVsMockIdsMap.put(key, mockIds);
        }
        headerStore.put(mockId, headers);
        resultMap.put(mockId, values);
        Debugger.console("key : " + key + ", value : " + value + ", mockId : " + mockId);
        return mockId;
    }

    public void setResultMapping(Map<String, String[]> resultMap) {
        this.resultMap = resultMap;
    }

    public void setMockIdVsResultKeyMap(Map<String, String> mockIdVsResultKeyMap) {
        this.mockIdVsResultKeyMap = mockIdVsResultKeyMap;
    }

    public void setHeaderStore(Map<String, Map<String, String[]>> headerStore) {
        this.headerStore = headerStore;
    }

    public String[] getResultMapping(ActionMappingEntity mappingEntity) {
        String mockId;
        if (mappingEntity.getHeaders() != null && !mappingEntity.getHeaders().isEmpty()) {
            Debugger.console("extracting mockId from the request ...");
            if (mappingEntity.getHeaders().containsKey("mockId"))
                mockId = mappingEntity.getHeaders().get("mockId")[0];
            else
                return null;
            if (mockId == null)
                return null;
            Debugger.console("got requested mockId as : " + mockId);
            mappingEntity.getHeaders().remove("mockId");
        } else {
            return null;
        }
        Debugger.console("Does " + mockId + " exist in container? : " + mockIdVsResultKeyMap.containsKey(mockId));
        if (mockIdVsResultKeyMap.containsKey(mockId)) {
            Debugger.console("Searching header store for mockId : " + mockId);
            if (headerStore.containsKey(mockId)) {
                for (String headerKey : headerStore.get(mockId).keySet()) {
                    Debugger.console("Validating header store entry : " + headerKey + ", exist in the request header map > " + mappingEntity.getHeaders());
                    if (mappingEntity.getHeaders().containsKey(headerKey)) {
                        if (!Arrays.equals(mappingEntity.getHeaders().get(headerKey), headerStore.get(mockId).get(headerKey)))
                            return null;
                    } else {
                        return null;
                    }
                }
            }
            Debugger.console("Searching result mapping equivalent to : " + mockIdVsResultKeyMap.get(mockId));
            if (mockIdVsResultKeyMap.containsKey(mockId)) {
                String[] resultArr = resultMap.get(mockId);
                Debugger.console("Result mapping found for : " + mockId);
                return resultArr;

            }
            Debugger.console("Result mapping not found for : " + mockId);
        }
        return null;
    }

    public String[] getResultForMockId(String mockId) {
        Debugger.console("Does " + mockId + " exist in container? : " + mockIdVsResultKeyMap.containsKey(mockId));
        return resultMap.get(mockId);
    }

    public String getUriForMockId(String mockId) {
        Debugger.console("Does " + mockId + " exist in container? : " + mockIdVsResultKeyMap.containsKey(mockId));
        return mockIdVsResultKeyMap.get(mockId);
    }

    public Map<String, String[]> getHeadersForMockId(String mockId) {
        Debugger.console("Does " + mockId + " exist in container? : " + mockIdVsResultKeyMap.containsKey(mockId));
        return headerStore.get(mockId);
    }

    public Map<String, String[]> getResultMapping() {
        return resultMap;
    }

    public Map<String, String> getMockIdVsResultKeyMap() {
        return mockIdVsResultKeyMap;
    }

    public Map<String, Map<String, String[]>> getHeaderStore() {
        return headerStore;
    }

    public String mappingExists(MockRequest mockRequest) {
        Debugger.console("does mapping exist?");
        String returnVal = null;
        String key = new URLMaker(mockRequest).getUri().concat(mockRequest.getRequestBody());
        if (resultKeyVsMockIdsMap.containsKey(key)) {
            for (String mockId : resultKeyVsMockIdsMap.get(key)) {
                for (String headerKey : headerStore.get(mockId).keySet()) {
                    if (mockRequest.getHeaders().containsKey(headerKey)) {
                        if(Arrays.equals(mockRequest.getHeaders().get(headerKey), headerStore.get(mockId).get(headerKey))) {
                            returnVal = mockId;
                            break;
                        }
                    }
                }
            }
        }
        return returnVal;
    }

    public String toString() {
        String str = "{\n";
        for (String mockId : mockIdVsResultKeyMap.keySet()) {
            str = str.concat("\t\"")
                    .concat(mockIdVsResultKeyMap.get(mockId))
                    .concat("\" : \"")
                    .concat(Arrays.toString(resultMap.get(mockId)))
                    .concat("\",\n");

        }
        str = FileUtils.replaceLast(str, ",", "");
        str = str.concat("}");
        return str;
    }
}

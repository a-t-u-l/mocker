package core;

import beans.MockRequest;
import beans.PathParamEntity;
import utils.FileUtils;

import java.util.List;

/**
 * Created by atul on 19/06/17.
 */
public class URLMaker {

    private MockRequest mockRequest;
    private String uri;
    private String path;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public URLMaker(MockRequest mockRequest){
        this.mockRequest = mockRequest;
        build();
    }


    private void build(){
        String uri;
        String path;
        if(mockRequest.getPathParams()==null || mockRequest.getPathParams().isEmpty()){
            if(mockRequest.getQueryParams().isEmpty()){
                uri=mockRequest.getURI();
                path=uri;
            }
            else{
                String param="?";
                for(String par:mockRequest.getQueryParams().keySet()){
                    param=param+par+"="+mockRequest.getQueryParams().get(par);
                    param=param+"&";
                }
                param=FileUtils.replaceLast(param,"&", "");
                uri=mockRequest.getURI()+param;
                path=mockRequest.getURI();
            }
        }
        else{
            if(mockRequest.getQueryParams()==null || mockRequest.getQueryParams().isEmpty()){
                List<String> spath=FileUtils.dataSplitter(mockRequest.getRequestBody(), "/");
                for(PathParamEntity se:mockRequest.getPathParams()){
                    for(int index=0;index<spath.size();index++){
                        if(spath.get(index).equals(se.getKey())){
                            spath.set(index,se.getValue());
                        }
                    }
                }
                String npath="";
                for(String s:spath){
                    npath=npath+s+"/";
                }
                npath= FileUtils.replaceLast(npath,"/", "");
                uri=npath;
                path=uri;
            }
            else{

                List<String> spath=FileUtils.dataSplitter(mockRequest.getURI(), "/");
                for(PathParamEntity se:mockRequest.getPathParams()){
                    for(int index=0;index<spath.size();index++){
                        if(spath.get(index).equals(se.getKey())){
                            spath.set(index,se.getValue());
                        }
                    }
                }
                String npath="";
                for(String s:spath){
                    npath=npath+s+"/";
                }
                npath=FileUtils.replaceLast(npath,"/", "");
                uri=npath;
                path=uri;

                String param="?";
                for(String par:mockRequest.getQueryParams().keySet()){
                    param=param+par+"="+mockRequest.getQueryParams().get(par);
                    param=param+"&";
                }
                param=FileUtils.replaceLast(param,"&", "");
                uri=uri+param;
            }
        }
        this.uri = uri;
        this.path = path;
    }
}

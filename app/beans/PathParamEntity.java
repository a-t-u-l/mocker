package beans;

/**
 * Created by atul on 19/06/17.
 */
public class PathParamEntity {

    private String key;
    private String value;

    public PathParamEntity(String key, String value){
        this.key=key;
        this.value=value;
    }

    public String getKey(){
        return this.key;
    }

    public String getValue(){
        return this.value;
    }
}

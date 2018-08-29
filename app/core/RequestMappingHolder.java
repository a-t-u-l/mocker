package core;

import beans.ActionMappingEntity;

/**
 * Created by atul on 19/06/17.
 */
public class RequestMappingHolder {

    private static RequestMappingHolder instance;
    private ActionMappingEntity actionMappingEntity;

    private RequestMappingHolder(){
    }

    public static RequestMappingHolder getInstance(){
        if(instance==null)
            instance=new RequestMappingHolder();
        return instance;
    }

    public ActionMappingEntity getActionMappingEntity() {
        return actionMappingEntity;
    }

    public void setActionMappingEntity(ActionMappingEntity actionMappingEntity) {
        this.actionMappingEntity = actionMappingEntity;
    }

}

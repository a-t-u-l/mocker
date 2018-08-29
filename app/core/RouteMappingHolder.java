package core;

import beans.RouteMappingEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atul on 20/06/17.
 */
public class RouteMappingHolder {
    private static RouteMappingHolder instance;
    private List<RouteMappingEntity> routeMappingEntityList;

    private RouteMappingHolder(){
    }

    //TODO : Use a data structure(red-black tree) which is most suitable for insertion and search
    public static RouteMappingHolder getInstance(){
        if(instance==null) {
            instance = new RouteMappingHolder();
            instance.routeMappingEntityList=new ArrayList<>();
        }
        return instance;
    }

    public void setRouteMappingEntity(RouteMappingEntity routeMappingEntity) {
        routeMappingEntityList.add(routeMappingEntity);
    }

    public List<RouteMappingEntity> getRouteMappingEntityList() {
        return routeMappingEntityList;
    }

    public boolean lookUp(String requestType, String key){
        for(RouteMappingEntity entity: routeMappingEntityList){
            if(entity.getRequestType().equalsIgnoreCase(requestType) && entity.getPath().equalsIgnoreCase(key)){
                return true;
            }
        }
        return false;
    }
}

import beans.RouteMappingEntity;
import com.google.inject.AbstractModule;

import java.io.*;
import java.time.Clock;
import java.util.List;

import core.Constant;
import core.ResultMappingHolder;
import core.RouteMappingHolder;
import services.ApplicationTimer;
import services.AtomicCounter;
import services.Counter;
import utils.Debugger;
import utils.FileUtils;

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 *
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

    @Override
    public void configure() {
        // Use the system clock as the default implementation of Clock
        bind(Clock.class).toInstance(Clock.systemDefaultZone());
        // Ask Guice to create an instance of ApplicationTimer when the
        // application starts.
        bind(ApplicationTimer.class).asEagerSingleton();
        // Set AtomicCounter as the implementation for Counter.
        bind(Counter.class).to(AtomicCounter.class);
        deserializeResultMap();
        initRouteMap();
    }

    private void deserializeResultMap(){
        Debugger.console("------------STARTING DESERIALIZATION------------");
        ResultMappingHolder object;

        String filename = Constant.FILE_SERIALIZE.value();
        Debugger.console("READING DATA FROM CONTAINER : "+filename);
        FileInputStream file;
        try{
            file = new FileInputStream(new File(filename));
        }
        catch(FileNotFoundException ex)
        {
            Debugger.console("Container Not found.");
            Debugger.console("------------------------------------------------");
            return;
        }

        ObjectInputStream in;
        try {
            in = new ObjectInputStream(file);
        } catch (IOException e) {
            Debugger.console("Parsing error");
            Debugger.console("------------------------------------------------");
            return;
        }
        try {
            object = (ResultMappingHolder) in.readObject();
        } catch (IOException e) {
            Debugger.console("Mapping error");
            Debugger.console("------------------------------------------------");
            return;
        } catch (ClassNotFoundException e) {
            Debugger.console("Class not found");
            Debugger.console("------------------------------------------------");
            return;
        }
        ResultMappingHolder holder=ResultMappingHolder.getInstance();
        holder.setResultMapping(object.getResultMapping());
        holder.setHeaderStore(object.getHeaderStore());
        holder.setMockIdVsResultKeyMap(object.getMockIdVsResultKeyMap());
        Debugger.console("******************MAPPING*******************");
        Debugger.console(holder.toString());
        try {
            in.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Debugger.console("------------------------------------------------");
    }

    private void initRouteMap(){
        RouteMappingHolder routeMappingHolder = RouteMappingHolder.getInstance();
        try {
            String routeData = FileUtils.readFile(Constant.FILE_ROUTE.value());
            String [] lines=routeData.split(Constant.NEWLINE.value());
            for(String line:lines){
                if(!line.startsWith("#")) {
                    line=line.replaceAll("( +)"," ").trim();
                    line=line.replaceAll("(\t+)"," ").trim();
                    String [] parts = line.split(" ");
                    if(parts.length!=3){
                        if(!line.contains("controllers.Assets.versioned") )
                            if(line.equals(" "))
                                throw new IndexOutOfBoundsException("Error in parsing route file. Check line : "+line);
                    }
                    else{
                        routeMappingHolder.setRouteMappingEntity(new RouteMappingEntity(parts[0],parts[1],parts[2]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Debugger.console("------------ROUTE MAPPING------------");
        List<RouteMappingEntity> routeMappingEntityList=routeMappingHolder.getRouteMappingEntityList();
        for(RouteMappingEntity entity: routeMappingEntityList)
            Debugger.console(entity.getRequestType(),entity.getPath(),entity.getMethod());
        Debugger.console("-------------------------------------");


    }
}

package defencer.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikita on 15.04.2017.
 */
public class ControllersDataFactory {

    private static ControllersDataFactory controllersDataFactory = null;

    private Map<Class,Map<String,Object>> controllersData = new HashMap<>();



    private ControllersDataFactory() {

    }



    public void add(Class key,Map<String,Object> data){

        if(controllersData.containsKey(key)) {
            Map<String,Object> oldData = controllersData.get(key);
            oldData.putAll(data);
            controllersData.put(key,oldData);
        }else{
            controllersData.put(key,data);
        }
    }



    public void update(Class key,Map<String,Object> data){
        controllersData.put(key,data);
    }



    public Object get(Class key){
        return controllersData.get(key);
    }



    public static ControllersDataFactory getLink(){
        if(controllersDataFactory == null){
            controllersDataFactory = new ControllersDataFactory();
        }

        return controllersDataFactory;
    }

}

package defencer.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikita on 15.04.2017.
 */
public class ControllersDataFactory {

    private static ControllersDataFactory controllersDataFactory = null;

    private Map<Class, Map<String, Object>> controllersData = new HashMap<>();

    private ControllersDataFactory() {
    }

    /**
     * Adding data with.
     */
    public void add(Class key, Map<String, Object> data) {
        if (controllersData.containsKey(key)) {
            Map<String, Object> oldData = controllersData.get(key);
            oldData.putAll(data);
            controllersData.put(key, oldData);
        } else {
            controllersData.put(key, data);
        }
    }

    /**
     * Adding data if data already exist it is updating.
     */
    public void update(Class key, Map<String, Object> data) {

        controllersData.put(key, data);
    }

    /**
     * Deleting data.
     */
    public void delete(Class key) {
        controllersData.remove(key);
    }

    /**
     * Does map contains data with suck key.
     */
    public boolean contains(Class key) {

        return controllersData.containsKey(key);
    }

    /**
     * Getting data.
     */
    public Object get(Class key) {
        return controllersData.get(key);
    }

    /**
     * Getting link.
     */
    public static ControllersDataFactory getLink() {
        if (controllersDataFactory == null) {
            controllersDataFactory = new ControllersDataFactory();
        }
        return controllersDataFactory;
    }

}

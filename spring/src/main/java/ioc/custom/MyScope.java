package ioc.custom;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class MyScope implements Scope {


    private Map<String, Object> map1 = new ConcurrentHashMap<>();
    private Map<String, Object> map2 = new ConcurrentHashMap<>();

    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        if (!map1.containsKey(s)) {
            Object object = objectFactory.getObject();
            map1.put(s, object);
            return object;
        }
        if (!map2.containsKey(s)) {
            Object object = objectFactory.getObject();
            map2.put(s, object);
            return object;
        }
        int i = new Random().nextInt(2);
        if (i == 0) {
            return map1.get(s);
        } else {
            return map2.get(s);
        }
    }

    @Override
    public Object remove(String s) {

        if (map2.containsKey(s)) {
            Object object = map2.get(s);
            map2.remove(s);
            return object;
        }
        if (map1.containsKey(s)) {
            Object object = map1.get(s);
            map1.remove(s);
            return object;
        }
        return null;
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {

    }

    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}

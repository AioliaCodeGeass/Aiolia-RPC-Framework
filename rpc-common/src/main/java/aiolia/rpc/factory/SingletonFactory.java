package aiolia.rpc.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/15
 */
public class SingletonFactory
{
    private static Map<Class,Object> objectMap = new HashMap<>();

    public SingletonFactory()
    {
    }

    public static <T> T getInstance(Class<T> clazz)
    {
        Object instance = objectMap.get(clazz);
        synchronized (clazz)
        {
            if (instance == null)
            {
                try
                {
                    instance = clazz.newInstance();
                    objectMap.put(clazz, instance);
                } catch (IllegalAccessException | InstantiationException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return clazz.cast(instance);
    }
}

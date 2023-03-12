package aiolia.rpc.transport;

import aiolia.rpc.enumeration.RpcError;
import aiolia.rpc.exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/12
 */
@Slf4j
public class DefaultServiceRegistry implements ServiceRegistry
{
    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    private final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    @Override
    public synchronized <T> void register(T service)
    {
        String serviceName = service.getClass().getCanonicalName();
        if (registeredService.contains(serviceName))
            return;
        registeredService.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0)
        {
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for (Class<?> i : interfaces)
        {
            serviceMap.put(i.getCanonicalName(), service);
        }
        log.info("向接口: {} 注册服务: {}", interfaces, serviceName);
    }

    @Override
    public synchronized Object getService(String serviceName)
    {
        Object service = serviceMap.get(serviceName);
        if (service == null)
        {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}

package aiolia.rpc.transport.provider;

import aiolia.rpc.enumeration.RpcError;
import aiolia.rpc.exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的服务注册表，保存服务端本地服务
 * @author aiolia
 * @version 1.0
 * @create 2023/3/13
 */
@Slf4j
public class ServiceProviderImpl implements ServiceProvider
{
    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    private static final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    @Override
    public synchronized <T> void addServiceProvider(T service,String serviceName)
    {
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
    public synchronized Object getServiceProvider(String serviceName)
    {
        Object service = serviceMap.get(serviceName);
        if (service == null)
        {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }

}

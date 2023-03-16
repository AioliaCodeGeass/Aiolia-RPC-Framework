package aiolia.rpc.transport.transport;

import aiolia.rpc.enumeration.RpcError;
import aiolia.rpc.exception.RpcException;
import aiolia.rpc.transport.annotation.Service;
import aiolia.rpc.transport.annotation.ServiceScan;
import aiolia.rpc.transport.provider.ServiceProvider;
import aiolia.rpc.transport.registry.ServiceRegistry;
import aiolia.rpc.transport.serializer.CommonSerializer;
import aiolia.rpc.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/15
 */
@Slf4j
public abstract class AbstractRpcServer implements RpcServer
{
    protected String host;
    protected int port;
    protected ServiceRegistry serviceRegistry;
    protected ServiceProvider serviceProvider;

    protected CommonSerializer serializer;

    public void scanServices()
    {
        String mainClassName = ReflectUtil.getStackTrace();
        Class<?> startClass;
        try
        {
            startClass = Class.forName(mainClassName);
            if (!startClass.isAnnotationPresent(ServiceScan.class))
            {
                log.error("启动类缺少 @ServiceScan 注解");
                throw new RpcException(RpcError.SERVICE_SCAN_PACKAGE_NOT_FOUND);
            }
        } catch (ClassNotFoundException e)
        {
            log.error("出现未知错误");
            throw new RpcException(RpcError.UNKNOWN_ERROR);
        }
        String basePackage = startClass.getAnnotation(ServiceScan.class).value();
        if ("".equals(basePackage))
        {
            basePackage = mainClassName.substring(0, mainClassName.lastIndexOf("."));
        }
        Set<Class<?>> classSet = ReflectUtil.getClasses(basePackage);
        for (Class<?> clazz : classSet)
        {
            if (clazz.isAnnotationPresent(Service.class))
            {
                String serviceName = clazz.getAnnotation(Service.class).name();
                Object obj;
                try
                {
                    obj = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e)
                {
                    log.error("创建 " + clazz + " 时有错误发生");
                    continue;
                }
                if ("".equals(serviceName))
                {
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> oneInterface : interfaces)
                    {
                        publishService(obj, oneInterface.getCanonicalName());
                    }
                } else
                {
                    publishService(obj, serviceName);
                }
            }
        }
    }

    @Override
    public <T> void publishService(T service, String serviceName)
    {
        serviceProvider.addServiceProvider(service, serviceName);
        serviceRegistry.register(serviceName, new InetSocketAddress(host, port));
    }
}

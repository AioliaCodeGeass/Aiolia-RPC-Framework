package aiolia.rpc.transport.handler;

import aiolia.rpc.entity.RpcRequest;
import aiolia.rpc.entity.RpcResponse;
import aiolia.rpc.enumeration.ResponseCode;
import aiolia.rpc.factory.SingletonFactory;
import aiolia.rpc.transport.provider.ServiceProvider;
import aiolia.rpc.transport.provider.ServiceProviderImpl;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/12
 */
@Slf4j
public class RequestHandler
{
    private static final ServiceProvider serviceProvider;

    static
    {
        serviceProvider = new ServiceProviderImpl();
    }

    public Object handle(RpcRequest rpcRequest)
    {
        Object service = serviceProvider.getServiceProvider(rpcRequest.getInterfaceName());
        return invokeTargetMethod(rpcRequest, service);
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service)
    {
        Object result;
        try
        {
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            result = method.invoke(service, rpcRequest.getParameters());
            log.info("服务:{} 成功调用方法:{}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
        {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND, rpcRequest.getRequestId());
        }
        return result;
    }
}

package aiolia.rpc.transport;

import aiolia.rpc.entity.RpcRequest;
import aiolia.rpc.entity.RpcResponse;
import aiolia.rpc.enumeration.ResponseCode;
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
    public Object handle(RpcRequest rpcRequest, Object service)
    {
        Object result = null;
        try
        {
            result = invokeTargetMethod(rpcRequest, service);
            log.info("服务:{} 成功调用方法:{}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (IllegalAccessException | InvocationTargetException e)
        {
            log.error("调用或发送时有错误发生：", e);
        }
        return result;
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws IllegalAccessException, InvocationTargetException
    {
        Method method;
        try
        {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e)
        {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND);
        }
        return method.invoke(service, rpcRequest.getParameters());
    }
}

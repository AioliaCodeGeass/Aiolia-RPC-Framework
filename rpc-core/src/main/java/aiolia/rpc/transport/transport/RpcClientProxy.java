package aiolia.rpc.transport.transport;

import aiolia.rpc.entity.RpcRequest;
import aiolia.rpc.entity.RpcResponse;
import aiolia.rpc.util.RpcMessageChecker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * RPC客户端动态代理
 * @author aiolia
 * @version 1.0
 * @create 2023/3/11
 */
public class RpcClientProxy implements InvocationHandler
{
    private final RpcClient client;

    public RpcClientProxy(RpcClient client)
    {
        this.client = client;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz)
    {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        RpcRequest rpcRequest = RpcRequest.builder()
                .requestId(UUID.randomUUID().toString())
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .heartBeat(false)
                .build();
        RpcResponse rpcResponse=(RpcResponse) client.sendRequest(rpcRequest);
        RpcMessageChecker.check(rpcRequest,rpcResponse);
        return rpcResponse.getData();
    }
}

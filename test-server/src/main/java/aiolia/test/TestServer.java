package aiolia.test;

import aiolia.rpc.api.HelloService;
import aiolia.rpc.api.HelloServiceImpl;
import aiolia.rpc.transport.DefaultServiceRegistry;
import aiolia.rpc.transport.RpcServer;
import aiolia.rpc.transport.ServiceRegistry;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/11
 */
public class TestServer
{
    public static void main(String[] args)
    {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }
}

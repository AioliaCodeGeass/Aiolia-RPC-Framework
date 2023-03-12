package aiolia.test;

import aiolia.rpc.api.HelloService;
import aiolia.rpc.api.HelloServiceImpl;
import aiolia.rpc.transport.RpcServer;

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
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 9000);
    }
}

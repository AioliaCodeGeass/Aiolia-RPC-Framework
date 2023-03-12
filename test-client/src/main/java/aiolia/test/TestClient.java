package aiolia.test;

import aiolia.rpc.api.HelloObject;
import aiolia.rpc.api.HelloService;
import aiolia.rpc.transport.RpcClientProxy;
import aiolia.rpc.transport.RpcServer;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/11
 */
public class TestClient
{
    public static void main(String[] args)
    {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
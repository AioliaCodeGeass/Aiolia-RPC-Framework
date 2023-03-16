package aiolia.test;

import aiolia.rpc.api.HelloService;
import aiolia.rpc.transport.provider.ServiceProvider;
import aiolia.rpc.transport.provider.ServiceProviderImpl;
import aiolia.rpc.transport.serializer.CommonSerializer;
import aiolia.rpc.transport.transport.socket.server.SocketServer;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/11
 */
public class TestServer
{
    public static void main(String[] args)
    {
        SocketServer rpcServer = new SocketServer("127.0.0.1", 9999, CommonSerializer.KRYO_SERIALIZER);
        rpcServer.start();
    }
}

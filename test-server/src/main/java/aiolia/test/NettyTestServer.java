package aiolia.test;

import aiolia.rpc.transport.annotation.ServiceScan;
import aiolia.rpc.transport.transport.netty.server.NettyServer;
import aiolia.rpc.transport.serializer.CommonSerializer;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/13
 */
@ServiceScan
public class NettyTestServer
{

    public static void main(String[] args)
    {
        NettyServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.KRYO_SERIALIZER);
        server.start();
    }

}

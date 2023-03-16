package aiolia.test;

import aiolia.rpc.api.ByeObject;
import aiolia.rpc.api.ByeService;
import aiolia.rpc.transport.transport.RpcClient;
import aiolia.rpc.transport.transport.RpcClientProxy;
import aiolia.rpc.transport.transport.netty.client.NettyClient;
import aiolia.rpc.transport.serializer.CommonSerializer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/13
 */
@Slf4j
public class NettyTestClient
{
    public static void main(String[] args)
    {
        RpcClient client = new NettyClient(CommonSerializer.KRYO_SERIALIZER);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        ByeService byeService = rpcClientProxy.getProxy(ByeService.class);
        ByeObject object = new ByeObject(10, "This is a message");
        String res = byeService.bye(object);
        System.out.println(res);
    }
}

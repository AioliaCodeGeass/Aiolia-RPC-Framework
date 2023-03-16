package aiolia.rpc.transport.transport.netty.client;

import aiolia.rpc.entity.RpcRequest;
import aiolia.rpc.entity.RpcResponse;
import aiolia.rpc.enumeration.RpcError;
import aiolia.rpc.exception.RpcException;
import aiolia.rpc.transport.transport.RpcClient;
import aiolia.rpc.transport.codec.CommonDecoder;
import aiolia.rpc.transport.codec.CommonEncoder;
import aiolia.rpc.transport.loadbalancer.LoadBalancer;
import aiolia.rpc.transport.loadbalancer.RandomLoadBalancer;
import aiolia.rpc.transport.registry.NacosServiceDiscovery;
import aiolia.rpc.transport.registry.ServiceDiscovery;
import aiolia.rpc.transport.serializer.CommonSerializer;
import aiolia.rpc.transport.serializer.KryoSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/12
 */
@Slf4j

public class NettyClient implements RpcClient
{
    private CommonSerializer serializer;
    private final ServiceDiscovery serviceDiscovery;

    public NettyClient(Integer serializer)
    {
        this(serializer, new RandomLoadBalancer());
    }


    public NettyClient(Integer serializer, LoadBalancer loadBalancer)
    {
        this.serviceDiscovery = new NacosServiceDiscovery(loadBalancer);
        this.serializer = CommonSerializer.getByCode(serializer);
    }


    @Override
    public Object sendRequest(RpcRequest rpcRequest)
    {
        if (serializer == null)
        {
            log.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        AtomicReference<Object> result = new AtomicReference<>(null);

        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());

        try
        {
            Channel channel = ChannelProvider.get(inetSocketAddress,serializer);
            if (channel != null)
            {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if (future1.isSuccess())
                    {
                        log.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                    } else
                    {
                        log.error("发送消息时有错误发生: ", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse rpcResponse = channel.attr(key).get();
                return rpcResponse;
            }
        }
        catch (InterruptedException e)
        {
            log.error("发送消息时有错误发生: ", e);
        }

        return null;

    }
}
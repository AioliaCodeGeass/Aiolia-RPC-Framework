package aiolia.rpc.transport.transport.netty.server;

import aiolia.rpc.enumeration.RpcError;
import aiolia.rpc.exception.RpcException;
import aiolia.rpc.transport.transport.AbstractRpcServer;
import aiolia.rpc.transport.codec.CommonDecoder;
import aiolia.rpc.transport.codec.CommonEncoder;
import aiolia.rpc.transport.hook.ShutdownHook;
import aiolia.rpc.transport.provider.ServiceProvider;
import aiolia.rpc.transport.provider.ServiceProviderImpl;
import aiolia.rpc.transport.registry.NacosServiceRegistry;
import aiolia.rpc.transport.registry.ServiceRegistry;
import aiolia.rpc.transport.serializer.CommonSerializer;
import aiolia.rpc.transport.serializer.KryoSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/12
 */
@Slf4j
public class NettyServer extends AbstractRpcServer
{
    public NettyServer(String host, int port)
    {
        this(host,port,CommonSerializer.DEFAULT_SERIALIZER);
    }

    public NettyServer(String host, int port, Integer serializer)
    {
        this.host = host;
        this.port = port;
        this.serviceRegistry = new NacosServiceRegistry();
        this.serviceProvider = new ServiceProviderImpl();
        this.serializer = CommonSerializer.getByCode(serializer);
        scanServices();
    }

    @Override
    public void start()
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 256)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>()
                    {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception
                        {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS))
                                    .addLast(new CommonEncoder(serializer))
                                    .addLast(new CommonDecoder())
                                    .addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(host, port).sync();
            ShutdownHook.getShutdownHook().addClearAllHook();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e)
        {
            log.error("启动服务器时有错误发生: ", e);
        } finally
        {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

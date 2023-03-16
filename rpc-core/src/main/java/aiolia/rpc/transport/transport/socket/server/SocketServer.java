package aiolia.rpc.transport.transport.socket.server;

import aiolia.rpc.transport.handler.RequestHandler;
import aiolia.rpc.transport.provider.ServiceProviderImpl;
import aiolia.rpc.transport.registry.NacosServiceRegistry;
import aiolia.rpc.transport.serializer.CommonSerializer;
import aiolia.rpc.transport.transport.AbstractRpcServer;
import aiolia.rpc.transport.transport.RpcServer;
import aiolia.rpc.transport.provider.ServiceProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/11
 */
@Slf4j
public class SocketServer extends AbstractRpcServer
{

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;
    private final ExecutorService threadPool;
    private RequestHandler requestHandler = new RequestHandler();

    public SocketServer(String host,int port,Integer serializer)
    {
        this.host=host;
        this.port=port;;
        this.serviceRegistry = new NacosServiceRegistry();
        this.serviceProvider = new ServiceProviderImpl();
        this.serializer = CommonSerializer.getByCode(serializer);
        scanServices();
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workingQueue, threadFactory);

    }

    @Override
    public void start()
    {
        try (ServerSocket serverSocket = new ServerSocket(9000))
        {
            log.info("服务器启动……");
            Socket socket;
            while ((socket = serverSocket.accept()) != null)
            {
                log.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new SocketRequestHandlerThread(socket, requestHandler, serviceProvider));
            }
            threadPool.shutdown();
        } catch (IOException e)
        {
            log.error("服务器启动时有错误发生:", e);
        }
    }
}

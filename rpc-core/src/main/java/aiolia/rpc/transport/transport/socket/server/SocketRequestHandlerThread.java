package aiolia.rpc.transport.transport.socket.server;

import aiolia.rpc.entity.RpcRequest;
import aiolia.rpc.entity.RpcResponse;
import aiolia.rpc.transport.handler.RequestHandler;
import aiolia.rpc.transport.provider.ServiceProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/12
 */
@Slf4j
public class SocketRequestHandlerThread implements Runnable
{
    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceProvider serviceProvider;

    public SocketRequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceProvider serviceProvider)
    {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void run()
    {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream()))
        {
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            String interfaceName = rpcRequest.getInterfaceName();
            Object service = serviceProvider.getServiceProvider(interfaceName);
            Object result = requestHandler.handle(rpcRequest);
            objectOutputStream.writeObject(RpcResponse.success(result,rpcRequest.getRequestId()));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e)
        {
            log.error("调用或发送时有错误发生：", e);
        }
    }
}


package aiolia.rpc.transport.transport.socket.client;

import aiolia.rpc.entity.RpcRequest;
import aiolia.rpc.transport.transport.RpcClient;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/11
 */
@Slf4j
public class SocketClient implements RpcClient
{
    @Override
    public Object sendRequest(RpcRequest rpcRequest)
    {
        try (Socket socket = new Socket("127.0.0.1", 9000))
        {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e)
        {
            log.error("调用时有错误发生：", e);
            return null;
        }
    }
}

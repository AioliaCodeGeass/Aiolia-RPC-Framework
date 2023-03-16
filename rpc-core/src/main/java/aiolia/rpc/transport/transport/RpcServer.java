package aiolia.rpc.transport.transport;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/12
 */
public interface RpcServer
{
    void start();

    <T> void publishService(T service, String serviceName);

}

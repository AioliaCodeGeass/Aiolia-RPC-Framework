package aiolia.rpc.transport;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/12
 */
public interface ServiceRegistry
{
    <T> void register(T service);

    Object getService(String serviceName);
}

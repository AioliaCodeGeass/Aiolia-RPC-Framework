package aiolia.rpc.transport.provider;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/13
 */
public interface ServiceProvider
{
    <T> void addServiceProvider(T service, String serviceName);

    Object getServiceProvider(String serviceName);

}

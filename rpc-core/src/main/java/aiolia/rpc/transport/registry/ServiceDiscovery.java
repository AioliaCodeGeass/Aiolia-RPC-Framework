package aiolia.rpc.transport.registry;

import java.net.InetSocketAddress;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/14
 */
public interface ServiceDiscovery
{
    InetSocketAddress lookupService(String serviceName);
}

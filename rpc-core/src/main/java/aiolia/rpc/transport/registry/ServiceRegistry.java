package aiolia.rpc.transport.registry;

import java.net.InetSocketAddress;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/13
 */
public interface ServiceRegistry
{
    void register(String serviceName, InetSocketAddress inetSocketAddress);
}


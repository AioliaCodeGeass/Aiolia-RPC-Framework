package aiolia.rpc.transport.registry;

import aiolia.rpc.enumeration.RpcError;
import aiolia.rpc.exception.RpcException;
import aiolia.rpc.transport.loadbalancer.LoadBalancer;
import aiolia.rpc.transport.loadbalancer.RandomLoadBalancer;
import aiolia.rpc.util.NacosUtil;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/13
 */
@Slf4j
public class NacosServiceRegistry implements ServiceRegistry
{
    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress)
    {
        try
        {
            NacosUtil.registerService(serviceName,inetSocketAddress);
        } catch (NacosException e)
        {
            log.error("注册服务时有错误发生:", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }
}

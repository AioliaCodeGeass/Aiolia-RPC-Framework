package aiolia.rpc.transport.registry;

import aiolia.rpc.transport.loadbalancer.LoadBalancer;
import aiolia.rpc.transport.loadbalancer.RandomLoadBalancer;
import aiolia.rpc.util.NacosUtil;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/14
 */
@Slf4j
public class NacosServiceDiscovery implements ServiceDiscovery
{
    private final LoadBalancer loadBalancer;

    public NacosServiceDiscovery(LoadBalancer loadBalancer)
    {
        if (loadBalancer == null)
            this.loadBalancer = new RandomLoadBalancer();
        else
            this.loadBalancer = loadBalancer;
    }

    @Override
    public InetSocketAddress lookupService(String serviceName)
    {
        try
        {
            List<Instance> instances = NacosUtil.getAllInstance(serviceName);
            Instance instance = loadBalancer.select(instances);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e)
        {
            log.error("获取服务时有错误发生:", e);
        }
        return null;
    }
}
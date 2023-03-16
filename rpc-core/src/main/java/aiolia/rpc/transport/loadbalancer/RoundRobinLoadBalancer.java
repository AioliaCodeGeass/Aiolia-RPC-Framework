package aiolia.rpc.transport.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/14
 */
public class RoundRobinLoadBalancer implements LoadBalancer
{
    private int index = 0;

    @Override
    public Instance select(List<Instance> instances)
    {
        if (index >= instances.size())
        {
            index %= instances.size();
        }
        return instances.get(index++);
    }
}

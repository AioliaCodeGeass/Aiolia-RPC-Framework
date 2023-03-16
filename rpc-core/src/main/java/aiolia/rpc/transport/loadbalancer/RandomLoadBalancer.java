package aiolia.rpc.transport.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;
import java.util.Random;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/14
 */
public class RandomLoadBalancer implements LoadBalancer
{
    @Override
    public Instance select(List<Instance> instances)
    {
        return instances.get(new Random().nextInt(instances.size()));
    }
}

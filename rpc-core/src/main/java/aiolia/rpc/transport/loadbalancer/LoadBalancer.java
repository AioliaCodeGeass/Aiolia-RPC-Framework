package aiolia.rpc.transport.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/14
 */
public interface LoadBalancer
{
    Instance select(List<Instance> instances);
}

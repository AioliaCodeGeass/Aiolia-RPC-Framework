package aiolia.test;

import aiolia.rpc.api.HelloObject;
import aiolia.rpc.api.HelloService;
import aiolia.rpc.transport.annotation.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/11
 */
@Slf4j
@Service
public class HelloServiceImpl implements HelloService
{
    @Override
    public String hello(HelloObject object)
    {
        log.info("接收到：{}", object.getMessage());
        return "这是调用的返回值，id=" + object.getId();
    }

    @Override
    public String toString()
    {
        return "这是实现类的toString()方法";
    }
}
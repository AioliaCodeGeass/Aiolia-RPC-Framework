package aiolia.rpc.api;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/11
 */
@Slf4j
public class HelloServiceImpl implements HelloService
{
    @Override
    public String hello(HelloObject object)
    {
        log.info("接收到：{}", object.getMessage());
        return "这是掉用的返回值，id=" + object.getId();
    }

    @Override
    public String toString()
    {
        return "这是实现类的toString()方法";
    }
}
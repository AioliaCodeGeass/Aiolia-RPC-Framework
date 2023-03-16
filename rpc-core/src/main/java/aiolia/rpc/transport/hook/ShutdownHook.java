package aiolia.rpc.transport.hook;

import aiolia.rpc.factory.ThreadPoolFactory;
import aiolia.rpc.util.NacosUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/14
 */
@Slf4j
public class ShutdownHook
{
    private final ExecutorService threadPool = ThreadPoolFactory.createDefaultThreadPool("shutdown-hook");
    private static final ShutdownHook shutdownHook = new ShutdownHook();

    public static ShutdownHook getShutdownHook()
    {
        return shutdownHook;
    }

    public void addClearAllHook()
    {
        log.info("关闭后将自动注销所有服务");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            NacosUtil.clearRegistry();
            threadPool.shutdown();
        }));
    }

}

package aiolia.test;

import aiolia.rpc.api.ByeObject;
import aiolia.rpc.api.ByeService;
import aiolia.rpc.transport.annotation.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/15
 */
@Slf4j
@Service
public class ByeServiceImpl implements ByeService
{
    @Override
    public String bye(ByeObject object)
    {
        log.info("接收到：{}", object.getMessage());
        return "bye, " + object.getId();
    }
}

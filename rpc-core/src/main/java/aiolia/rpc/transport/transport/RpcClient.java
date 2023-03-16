package aiolia.rpc.transport.transport;

import aiolia.rpc.entity.RpcRequest;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/12
 */
public interface RpcClient
{
    Object sendRequest(RpcRequest rpcRequest);
}

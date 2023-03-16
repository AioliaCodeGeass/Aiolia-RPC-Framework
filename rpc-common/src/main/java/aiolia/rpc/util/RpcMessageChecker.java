package aiolia.rpc.util;

import aiolia.rpc.entity.RpcRequest;
import aiolia.rpc.entity.RpcResponse;
import aiolia.rpc.enumeration.ResponseCode;
import aiolia.rpc.enumeration.RpcError;
import aiolia.rpc.exception.RpcException;
import lombok.extern.slf4j.Slf4j;

/**
 * 检查RPC请求与响应
 * @author aiolia
 * @version 1.0
 * @create 2023/3/15
 */
@Slf4j
public class RpcMessageChecker
{
    public static final String INTERFACE_NAME = "interfaceName";
    
    private RpcMessageChecker()
    {
    }

    public static void check(RpcRequest rpcRequest, RpcResponse rpcResponse)
    {
        if (rpcResponse == null)
        {
            log.error("调用服务失败,serviceName:{}", rpcRequest.getInterfaceName());
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        if (!rpcRequest.getRequestId().equals(rpcResponse.getRequestId()))
        {
            throw new RpcException(RpcError.RESPONSE_NOT_MATCH, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        if (rpcResponse.getStatusCode() == null || !rpcResponse.getStatusCode().equals(ResponseCode.SUCCESS.getCode()))
        {
            log.error("调用服务失败,serviceName:{},RpcResponse:{}", rpcRequest.getInterfaceName(), rpcResponse);
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }
    }
}

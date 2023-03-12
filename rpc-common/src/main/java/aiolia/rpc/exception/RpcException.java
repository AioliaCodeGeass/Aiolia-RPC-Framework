package aiolia.rpc.exception;

import aiolia.rpc.enumeration.RpcError;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/12
 */
public class RpcException extends RuntimeException
{

    public RpcException(RpcError error, String detail)
    {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public RpcException(RpcError error)
    {
        super(error.getMessage());
    }

}

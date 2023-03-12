package aiolia.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/12
 */
@AllArgsConstructor
@Getter
public enum RpcError
{
    SERVICE_NOT_FOUND("找不到对应的服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务未实现接口");

    private final String message;
}

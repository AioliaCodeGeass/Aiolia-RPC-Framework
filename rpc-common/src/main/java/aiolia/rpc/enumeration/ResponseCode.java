package aiolia.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 方法调用的响应状态码
 * @author aiolia
 * @version 1.0
 * @create 2023/3/11
 */
@AllArgsConstructor
@Getter
public enum ResponseCode
{
    SUCCESS(200,"方法调用成功"),
    METHOD_NOT_FOUND(500, "未找到指定方法"),
    CLASS_NOT_FOUND(500, "未找到指定类");

    /**
     * 响应状态码
     */
    private final Integer code;
    /**
     * 响应状态补充信息
     */
    private final String message;


}

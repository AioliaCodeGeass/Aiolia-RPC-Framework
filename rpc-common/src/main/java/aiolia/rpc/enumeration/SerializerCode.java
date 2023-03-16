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
public enum SerializerCode
{

    KRYO(0),
    JSON(1),
    HESSIAN(2),
    PROTOBUF(3);

    private final int code;

}

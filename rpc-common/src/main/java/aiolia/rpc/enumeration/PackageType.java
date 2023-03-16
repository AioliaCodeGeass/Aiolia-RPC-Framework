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
public enum PackageType
{

    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;

}

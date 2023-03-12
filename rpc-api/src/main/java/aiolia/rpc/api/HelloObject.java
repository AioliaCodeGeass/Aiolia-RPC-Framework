package aiolia.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/11
 */
@Data
@AllArgsConstructor
public class HelloObject implements Serializable
{
    private Integer id;
    private String message;
}

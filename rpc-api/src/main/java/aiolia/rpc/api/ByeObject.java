package aiolia.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ByeObject implements Serializable
{
    private Integer id;
    private String message;
}

package aiolia.rpc.transport.serializer;

import aiolia.rpc.enumeration.SerializerCode;
import aiolia.rpc.exception.SerializeException;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 基于Hessian协议的序列化器
 * @author aiolia
 * @version 1.0
 * @create 2023/3/13
 */
@Slf4j
public class HessianSerializer implements CommonSerializer
{
    @Override
    public byte[] serialize(Object obj)
    {
        HessianOutput hessianOutput = null;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream())
        {
            hessianOutput = new HessianOutput(byteArrayOutputStream);
            hessianOutput.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e)
        {
            log.error("序列化时有错误发生:", e);
            throw new SerializeException("序列化时有错误发生");
        } finally
        {
            if (hessianOutput != null)
            {
                try
                {
                    hessianOutput.close();
                } catch (IOException e)
                {
                    log.error("关闭流时有错误发生:", e);
                }
            }
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz)
    {
        HessianInput hessianInput = null;
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes))
        {
            hessianInput = new HessianInput(byteArrayInputStream);
            return hessianInput.readObject();
        } catch (IOException e)
        {
            log.error("序列化时有错误发生:", e);
            throw new SerializeException("序列化时有错误发生");
        } finally
        {
            if (hessianInput != null) hessianInput.close();
        }
    }

    @Override
    public int getCode()
    {
        return SerializerCode.valueOf("HESSIAN").getCode();
    }
}

package aiolia.rpc.transport.transport.netty.client;

import aiolia.rpc.entity.RpcRequest;
import aiolia.rpc.entity.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author aiolia
 * @version 1.0
 * @create 2023/3/12
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse>
{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception
    {
        try
        {
            log.info(String.format("客户端接收到消息: %s", msg));
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
            ctx.channel().attr(key).set(msg);
//            ctx.channel().close();
        } finally
        {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        log.error("过程调用时有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 心跳检测超时处理
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception
    {
        if (evt instanceof IdleStateEvent)
        {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE)
            {
                log.info("长时间未发送消息，发送心跳包...");
                ctx.writeAndFlush(RpcRequest.builder().heartBeat(true).build());
            }
        } else
        {
            super.userEventTriggered(ctx, evt);
        }
    }

}

package cn.edu.rpc.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by HeYong on 2016/3/22.
 */
public class RpcEncoder  extends MessageToByteEncoder{
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        byte[] data = JDKSerializa.encode(o);
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
    }
}

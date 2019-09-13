package netty.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

@Deprecated
public class ProtobufDecoder_1 extends ByteToMessageDecoder {
    private static Log log = LogFactory.getLog(ProtobufDecoder_1.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //...

        // 读取传送过来的消息的长度。
        int length = byteBuf.readUnsignedShort();

        //...
        if (length > byteBuf.readableBytes()) {
            // 读到的半包
            // ...
            log.error("告警：读到的消息体长度小于传送过来的消息长度");
            return;
        }
        //...  省略了正常包的处理
//        short version = byteBuf.readShort();
//        short bodyLength = byteBuf.readShort();
//        byte[] body = new byte[bodyLength];
//        byteBuf.readBytes(body);
//        RequestDTO requestDTO = RequestDTO.toObject(body);

    }
}

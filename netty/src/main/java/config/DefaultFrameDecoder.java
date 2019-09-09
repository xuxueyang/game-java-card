package config;

import constants.Constants;
import core.core.RequestDTO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DefaultFrameDecoder<T extends RequestDTO> extends ByteToMessageDecoder {
//    private static int versionL = 2;
//    private static int lengthL = 4;
    private Logger logger = LoggerFactory.getLogger(DefaultFrameDecoder.class);
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //...

        // 读取传送过来的消息的长度。
        short version = byteBuf.readShort();
        if(version != Constants.PROTOCOL_VERSION){
            return;
        }
        int length = byteBuf.readInt();
        if (length > byteBuf.readableBytes()) {
            // 读到的半包
            // ...
            logger.error("告警：读到的消息体长度小于传送过来的消息长度");
            byteBuf.writeByte(length);//?
            return;
        }
        //...  省略了正常包的处理
        byte[] body = new byte[length];
        byteBuf.readBytes(body);
        RequestDTO requestDTO = RequestDTO.toObject(body);
        list.add(requestDTO);
        logger.info("success decode");
    }
}

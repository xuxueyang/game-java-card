package netty.config;

import netty.constants.Constants;
import core.core.RequestDTO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class DefaultFrameEncoder extends MessageToByteEncoder<RequestDTO> {
    private Logger logger = LoggerFactory.getLogger(DefaultFrameEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RequestDTO requestDTO, ByteBuf byteBuf) throws Exception {
        logger.info("转换DTOing");
        byte[] bytes = RequestDTO.toByteArray(requestDTO);// 将对象转换为byte
        int length = bytes.length;// 读取 ProtoMsg 消息的长度
        ByteBuf buf = Unpooled.buffer(Constants.PROTOCOL_HEADLENGTH + length);
        // 先将消息协议的版本写入，也就是消息头
        buf.writeShort(Constants.PROTOCOL_VERSION);
        // 再将 ProtoMsg 消息的长度写入
        buf.writeInt(length);
        // 写入 ProtoMsg 消息的消息体
        buf.writeBytes(bytes);
        //发送
        byteBuf.writeBytes(buf);
//        ---------------------
//                作者：疯狂创客圈
//        来源：CSDN
//        原文：https://blog.csdn.net/crazymakercircle/article/details/83957259
//        版权声明：本文为博主原创文章，转载请附上博文链接！

    }
//    public static byte[] intToByte(byte[] data){
//        byte[] b = new byte[6 + data.length];
//        b[0] = (byte)(data.length & 0xff);
//        b[1] = (byte)((data.length >> 8) & 0xff);
//        b[2] = (byte)((data.length >> 16) & 0xff);
//        b[3] = (byte)((data.length >> 24) & 0xff);
//        b[4] = 0x0;
//        b[5] = 0x1;
//        System.arraycopy(data, 0, b, 6, data.length);
//        return b;
//    }
}

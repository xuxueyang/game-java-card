package code;

import constants.Constants;
import core.core.RequestDTO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtobufEncoder_1 extends MessageToByteEncoder<RequestDTO> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RequestDTO requestDTO, ByteBuf byteBuf) throws Exception {

        byte[] bytes = RequestDTO.toByteArray(requestDTO);// 将对象转换为byte
        int length = bytes.length;// 读取 ProtoMsg 消息的长度
        ByteBuf buf = Unpooled.buffer(2 + length);
        // 先将消息协议的版本写入，也就是消息头
        buf.writeShort(Constants.PROTOCOL_VERSION);
        // 再将 ProtoMsg 消息的长度写入
        buf.writeShort(length);
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
}

package code;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class PackageDecoder extends FrameDecoder {

    @Override
    protected Object decode(ChannelHandlerContext channelHandlerContext, Channel channel, ChannelBuffer channelBuffer) throws Exception {
        if (channelBuffer.readableBytes() > 4) {
            //标记读取位置
            channelBuffer.markReaderIndex();
            //读取数据长度
            int n = channelBuffer.readInt();
            if (channelBuffer.readableBytes() < n) {
                //如果数据长度小于设定的数据，则处于缓存状态
                channelBuffer.resetReaderIndex();
                //缓存当前数据，等待数据接入
                return null;
            }
            byte[] bytes = new byte[n];
            channelBuffer.readBytes(bytes);
            return new String(bytes);
        }
        //缓存当前数据，等待数据接入
        return null;
    }
}

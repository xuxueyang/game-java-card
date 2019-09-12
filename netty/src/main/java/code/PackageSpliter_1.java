package code;

import constants.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

@Deprecated
public class PackageSpliter_1 extends LengthFieldBasedFrameDecoder {
    public PackageSpliter_1() {
        super(Integer.MAX_VALUE, Constants.LENGTH_OFFSET,Constants.LENGTH_BYTES_COUNT);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        return super.decode(ctx, in);
    }
}

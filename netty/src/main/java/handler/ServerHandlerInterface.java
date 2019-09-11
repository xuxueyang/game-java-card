package handler;

import io.netty.channel.ChannelHandlerContext;

public interface ServerHandlerInterface<T,A> {
    void channelRead(ChannelHandlerContext ctx, T dto) throws Exception;
    String encode(A object);
    A decode(String json);
}

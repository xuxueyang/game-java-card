package config;

import code.PackageDecoder;
import handler.ServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

public class DefaultChannelInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new DefaultFrameDecoder());
        socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
        socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
        socketChannel.pipeline().addLast(new ServerHandler());
        socketChannel.pipeline().addLast(new DefaultFrameEncoder());


    }
}

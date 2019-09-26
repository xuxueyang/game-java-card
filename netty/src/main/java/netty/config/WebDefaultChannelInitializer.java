package netty.config;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import netty.handler.ServerHandler;
import netty.proto.dto.RequestDTO;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class WebDefaultChannelInitializer extends ChannelInitializer<SocketChannel>{
    public static String test(ByteBuf byteBuf){
        byte[] body = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(body);
        try {
            return new String(body,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "error";
        }
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.println("chhhh"+socketChannel.id());
        socketChannel.pipeline().addLast("http-codec", new HttpServerCodec());
        socketChannel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
        socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        socketChannel.pipeline().addLast(new ServerHandler());
    }
}

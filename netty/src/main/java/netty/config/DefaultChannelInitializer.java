package netty.config;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import netty.handler.ServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import netty.proto.dto.RequestDTO;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class DefaultChannelInitializer extends ChannelInitializer<SocketChannel>{
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
    public static final boolean useProtobuf =  true;
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        if(useProtobuf){
            //protobuf 处理
            socketChannel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
            socketChannel.pipeline().addLast(new ProtobufDecoder(RequestDTO.RequestDTOProto.getDefaultInstance()));
            socketChannel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
            socketChannel.pipeline().addLast(new ProtobufEncoder());
            socketChannel.pipeline().addLast(new ServerHandler());

        }else{
            //不需要自己写转换处理的代码了
            socketChannel.pipeline().addLast(new DefaultFrameDecoder());
            socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
            socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
            socketChannel.pipeline().addLast(new ServerHandler());
            socketChannel.pipeline().addLast(new DefaultFrameEncoder());
        }
        if(false){
            socketChannel.pipeline().addLast("http-codec", new HttpServerCodec());
            socketChannel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
            socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        }
    }
}

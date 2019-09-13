package netty.config;

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

import java.nio.charset.Charset;

public class DefaultChannelInitializer extends ChannelInitializer<SocketChannel>{

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

    }
}

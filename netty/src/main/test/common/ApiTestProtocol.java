package common;

import com.alibaba.fastjson.JSON;
import com.googlecode.protobuf.format.JsonFormat;
import config.DefaultFrameDecoder;
import config.DefaultFrameEncoder;
import core.core.RequestDTO;
import core.protocol.Protocol;
import core.util.MD5Util;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import proto.MsgUtil;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ApiTestProtocol {

    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                    channel.pipeline().addLast(new ProtobufDecoder(proto.dto.RequestDTO.RequestDTOProto.getDefaultInstance()));
                    channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                    channel.pipeline().addLast(new ProtobufEncoder());
                    // 在管道中添加我们自己的接收数据实现方法
                    channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            //接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
//                            RequestDTO dto = (RequestDTO)msg;
                            String string = JsonFormat.printToString((proto.dto.RequestDTO.RequestDTOProto) msg);

                            System.out.println(
                                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 客户端接收到消息：" + string );
                        }
                    });
                }
            });
            ChannelFuture f = b.connect("127.0.0.1", 7397).sync();
            System.out.println("itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}");

            //向服务端发送信息
            RequestDTO dto = new RequestDTO();
            HashMap<Object, Object> data = new HashMap<>();
            data.put("message1","Hello world");
            dto.setUserId(1L);
            dto.setTimestamp(new Date().getTime());
            dto.setArea(Protocol.Area.Netty);
            dto.setAreaL(1L);
            dto.setType(Protocol.Type.LOGIN);
            dto.setMd5(MD5Util.MD5("8254a4f9aa08420092f3c6f8f01b2370" + dto.getTimestamp() ));
            dto.setData(data);

            f.channel().writeAndFlush(MsgUtil.buildMsg(JSON.toJSONString(dto)));

            RequestDTO dto2 = new RequestDTO();
            HashMap<Object, Object> data2 = new HashMap<>();
            data2.put("message2","Hello world");
            dto2.setUserId(1L);
            dto2.setTimestamp(new Date().getTime());
            dto2.setArea(Protocol.Area.Netty);
            dto2.setAreaL(1L);
            dto2.setMd5(MD5Util.MD5("8254a4f9aa08420092f3c6f8f01b2370" + dto.getTimestamp() ));
            dto2.setData(data2);

            dto2.setType(Protocol.Type.ROOM);

            f.channel().writeAndFlush(MsgUtil.buildMsg(JSON.toJSONString(dto2)));

            f.channel().closeFuture().syncUninterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}

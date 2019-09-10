package common;

import com.alibaba.fastjson.JSON;
import config.DefaultFrameDecoder;
import config.DefaultFrameEncoder;
import core.core.RequestDTO;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static core.core.RequestDTO.toByteArray;

public class ApiTest {
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
                    channel.pipeline().addLast(new DefaultFrameDecoder());
                    channel.pipeline().addLast(new DefaultFrameEncoder());
                    // 解码转String，注意调整自己的编码格式GBK、UTF-8
                    channel.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
                    // 解码转String，注意调整自己的编码格式GBK、UTF-8
                    channel.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
                    // 在管道中添加我们自己的接收数据实现方法
                    channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            //接收msg消息{与上一章节相比，此处已经不需要自己进行解码}
                            RequestDTO dto = (RequestDTO)msg;
                            System.out.println(
                                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 客户端接收到消息：" + JSON.toJSON(dto) );
                        }
                    });
                }
            });
            ChannelFuture f = b.connect("127.0.0.1", 7397).sync();
            System.out.println("itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈，获取源码}");

            //向服务端发送信息
            RequestDTO dto = new RequestDTO();
            HashMap<Object, Object> data = new HashMap<>();
            data.put("message","Hello world");
            dto.setUserId(0L);
            dto.setData(data);
            byte[] bytes = toByteArray(dto);
            f.channel().writeAndFlush(dto);

            f.channel().closeFuture().syncUninterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}

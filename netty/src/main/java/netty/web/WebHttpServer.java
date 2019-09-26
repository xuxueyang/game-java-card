package netty.web;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.config.WebDefaultChannelInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;


@Component("web-netty.web.HttpServer")
public class WebHttpServer {
    private Logger logger = LoggerFactory.getLogger(WebHttpServer.class);

    private final EventLoopGroup parentGroup = new NioEventLoopGroup();
    private final EventLoopGroup childGroup = new NioEventLoopGroup();

    private Channel channel;

    private ChannelFuture future;

    public void bind(InetSocketAddress address){
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup,childGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new WebDefaultChannelInitializer());
            future = b.bind(address).syncUninterruptibly();
            channel = future.channel();
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            if(null!=future&&future.isSuccess()){
                logger.info("WebHttpServer启动成功 start success");
            }else{
                logger.info("WebHttpServer启动失败 start error");
            }
        }
    }
    public void destroy(){
        if(null==channel)
            return;
        channel.close();
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }

    public ChannelFuture getFuture() {
        return future;
    }
}

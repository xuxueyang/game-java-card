
import code.PackageDecoder;
import handler.EchoServerHandler;
import handler.ServerHandler;
import io.netty.channel.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//---------------------
//        作者：杨PPP
//        来源：CSDN
//        原文：https://blog.csdn.net/qq_24256123/article/details/78379562
//        版权声明：本文为博主原创文章，转载请附上博文链接！
public class HttpServer {
    private static Log log = LogFactory.getLog(HttpServer.class);

    public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServer();
        log.info("服务已启动...");
        server.start1(7397);
    }

    public void start0(int port) throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            // 启动引导器
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, work);
            // 设置nio类型的channel
            b.channel(NioServerSocketChannel.class);
            // 设置监听端口
            b.localAddress(new InetSocketAddress(port));
            // 设置通道初始化
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                //有连接到达时会创建一个channel
                protected void initChannel(SocketChannel ch) throws Exception {
                    // pipeline管理channel中的Handler
                    // 在channel队列中添加一个handler来处理业务
                    ch.pipeline().addLast("echoServerHandler",  new EchoServerHandler());
                }
            });
            // 配置完成，开始绑定server
            // 通过调用sync同步方法阻塞直到绑定成功 ​
            ChannelFuture f = b.bind().sync();
            System.out.println(EchoServer.class.getName()
                    + " started and listen on " + f.channel().localAddress());
            // 监听服务器关闭事件
            // 应用程序会一直等待，直到channel关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //  关闭EventLoopGroup，释放掉所有资源包括创建的线程
            work.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
    public void start2(int port) throws Exception {

        //声明服务类
        org.jboss.netty.bootstrap.ServerBootstrap serverBootstrap = new org.jboss.netty.bootstrap.ServerBootstrap();

        //设定线程池
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService work = Executors.newCachedThreadPool();

        //设置工厂
        serverBootstrap.setFactory(new NioServerSocketChannelFactory(boss,work));

        //设置管道流
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public org.jboss.netty.channel.ChannelPipeline getPipeline() throws Exception {
                org.jboss.netty.channel.ChannelPipeline channelPipeline = Channels.pipeline();
                //添加处理方式
                channelPipeline.addLast("decode",new PackageDecoder());
//                channelPipeline.addLast("hello",new HelloHandler());
                return channelPipeline;
            }
        });
        //设置端口
        serverBootstrap.bind(new InetSocketAddress(9000));
    }
    public void start1(int port) throws Exception {
        // 配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {

                    ch.pipeline().addLast(new ServerHandler());
//                    ch.pipeline().addLast("decode", (ChannelHandler) new PackageDecoder());
                }
            }).option(ChannelOption.SO_BACKLOG, 128) // 最大客户端连接数为128
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // 绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();
            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放线程池资源
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}

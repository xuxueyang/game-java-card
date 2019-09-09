package handler;

import com.alibaba.fastjson.JSON;
import core.core.RequestDTO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import rabbitmq.MQResource;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static Log log = LogFactory.getLog(ServerHandler.class);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        log.info(ctx.channel().id() + "进来了");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        //通知客户端链接建立成功
        SocketChannel channel = (SocketChannel) ctx.channel();
        String str = "通知客户端链接建立成功" + " " + new Date() + " " + channel.localAddress().getHostString() + "\r\n";
        RequestDTO o = new RequestDTO();
        o.setMd5(str);
        byte[] bytes = RequestDTO.toByteArray(o);
        ctx.writeAndFlush(bytes);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        log.info(ctx.channel().id() + "离开了");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("ServerHandler ========================= ");
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        RequestDTO dto = RequestDTO.toObject(req);
//        System.out.println(dto);
        String json = JSON.toJSONString(dto);
        log.debug(json);
        MQResource.getMQResource().getRabbitMQProducer().produce(json);
//        ReferenceCountUtil.release(msg);
//        UserLoginDoData.doData(req);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // TODO Auto-generated method stub
        log.info("异常信息：\r\n" + cause.getMessage());

//        cause.printStackTrace();
        ctx.close();
    }

}

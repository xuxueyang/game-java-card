package netty.handler;

import core.core.RequestDTO;
import core.protocol.Protocol;
import core.rpc.RoomRPCConstant;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import netty.handler.inter.AbstactSelfServerHandler;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import netty.rabbitmq.RabbitMQConsumer;
import netty.rabbitmq.RabbitMQProducer;


import javax.annotation.Resource;

@Service
public class RoomServerHandler extends AbstactSelfServerHandler<RequestDTO,RequestDTO> {
    private static Log log = LogFactory.getLog(RoomServerHandler.class);

    @Resource(name = RoomRPCConstant.MQ_NAME_PRODUCER)
    private RabbitMQConsumer consumer;
    @Resource(name = RoomRPCConstant.MQ_NAME_CONSUMER)
    private RabbitMQProducer producer;
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    public void channelRead(ChannelHandlerContext ctx, RequestDTO dto) throws Exception {
        String json = encode(dto);
        log.info(json);
        try {
            producer.produce(json);
        }catch (Exception e){
            log.error("消息队列出错" + e.getMessage());
        }
    }

    @Override
    public byte getType() {
        return Protocol.Type.ROOM;
    }


}

package handler;

import core.core.RequestDTO;
import core.rpc.RoomRPCConstant;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import rabbitmq.mq.consumer.RabbitMQConsumer;
import rabbitmq.mq.producer.RabbitMQProducer;

import javax.annotation.Resource;

@Service
public class RoomServerHandler extends AbstactSelfServerHandler<RequestDTO,RequestDTO> {
    private static Log log = LogFactory.getLog(RoomServerHandler.class);

    @Resource(name = RoomRPCConstant.MQ_NAME_PRODUCER)
    private RabbitMQConsumer consumer;
    @Resource(name = RoomRPCConstant.MQ_NAME_CONSUMER)
    private RabbitMQProducer producer;


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




}

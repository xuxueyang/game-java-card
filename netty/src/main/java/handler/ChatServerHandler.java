package handler;

import com.alibaba.fastjson.JSON;
import core.core.RequestDTO;
import core.rpc.ChatRPCConstant;;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import rabbitmq.MQResource;
import rabbitmq.mq.consumer.RabbitMQConsumer;
import rabbitmq.mq.producer.RabbitMQProducer;

import javax.annotation.Resource;

@Service
public class ChatServerHandler implements ServerHandlerInterface<RequestDTO, RequestDTO> {
    private static Log log = LogFactory.getLog(ChatServerHandler.class);

    @Resource(name = ChatRPCConstant.MQ_NAME_PRODUCER)
    private RabbitMQConsumer consumer;
    @Resource(name = ChatRPCConstant.MQ_NAME_CONSUMER)
    private RabbitMQProducer producer;


    @Override
    public void channelRead(ChannelHandlerContext ctx, RequestDTO dto) throws Exception {
        String json = encode(dto);
        log.info(json);
        try {
            MQResource.getMQResource().getChatRabbitMQProducer().produce(json);
        }catch (Exception e){
            log.error("消息队列出错" + e.getMessage());
        }
    }

    @Override
    public String encode(RequestDTO object) {
        return JSON.toJSONString(object);
    }

    @Override
    public RequestDTO decode(String json) {
        return JSON.parseObject(json,RequestDTO.class);
    }
    //TODO 线程不断读取数据，并且推送自己以外的人（自己encode，自己decode)

    @Data
    private class Message{
        private long areaL;
        private int type;//群发还是单独发还是组发，单独就是userId,组发就是groupId，群发就是按照频道来
        private long toId;//单独发送到哪里
        private String message;
    }
//    private enum Type{
//        GROUP,
//        ONE,
//        WORLD,
//        CURRENT,
//        ROOM
//    }
}

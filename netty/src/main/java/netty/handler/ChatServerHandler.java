package netty.handler;

import core.core.RequestDTO;
import core.protocol.Protocol;
import core.rpc.ChatRPCConstant;;
import netty.handler.inter.AbstactSelfServerHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import netty.rabbitmq.MQResource;
import netty.rabbitmq.RabbitMQConsumer;
import netty.rabbitmq.RabbitMQProducer;


import javax.annotation.Resource;

@Service
public class ChatServerHandler extends AbstactSelfServerHandler<RequestDTO, RequestDTO> {
    private static Log log = LogFactory.getLog(ChatServerHandler.class);

    @Resource(name = ChatRPCConstant.MQ_NAME_PRODUCER)
    private RabbitMQConsumer consumer;
    @Resource(name = ChatRPCConstant.MQ_NAME_CONSUMER)
    private RabbitMQProducer producer;

//    TODO 会卡死
//    @PostConstruct
    private void initManager() {
        Runnable runnableConsumer = new Runnable() {
            @Override
            public void run() {
                //循环读取读出消息
                while (true){
                    if(consumer!=null){
                        try {
                            String consume = consumer.consume();
                            log.info(consume);
                            RequestDTO dto = decode(consume,RequestDTO.class);
                            //TODO 转发 receiveMessage(dto);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        };
        Thread threadConsumer = new Thread(runnableConsumer);
        threadConsumer.run();
    }
    @Override
    public void channelRead(Channel ctx, RequestDTO dto) throws Exception {
        String json = encode(dto);
        log.info(json);
        try {
            MQResource.getMQResource().getChatRabbitMQProducer().produce(json);
        }catch (Exception e){
            log.error("消息队列出错" + e.getMessage());
        }
    }

    @Override
    public byte getType() {
        return Protocol.Type.CHAT;
    }

    @Override
    public void init() {

    }


    @Data
    private class Message{
        private long areaL;
        private int type;//群发还是单独发还是组发，单独就是userId,组发就是groupId，群发就是按照频道来
        private long toId;//单独发送到哪里
        private String message;
    }
}

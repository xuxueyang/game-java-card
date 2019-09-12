package rabbitmq;

import core.rpc.ChatRPCConstant;
import core.rpc.RoomRPCConstant;
import rabbitmq.mq.consumer.RabbitMQConsumer;
import rabbitmq.mq.producer.RabbitMQProducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

@Deprecated
public class MQResource {
    private MQResource(){}
    private static MQResource mqResource = null;
    public static MQResource getMQResource(){return mqResource;}
    private HashMap<String,RabbitMQProducer> producerHashMap = new HashMap<>();
    private HashMap<String,RabbitMQConsumer> consumerHashMap = new HashMap<>();
    static {
//        MQResource.mqResource = new MQResource();
//        getMQResource().initRoomMQ();
//        getMQResource().initChatMQ();
    }

    public RabbitMQProducer getRoomRabbitMQProducer(){
        return getRabbitMQProducer(RoomRPCConstant.MQ_NAME_CONSUMER);
    }

    public RabbitMQProducer getChatRabbitMQProducer(){
        return getRabbitMQProducer(ChatRPCConstant.MQ_NAME_CONSUMER);
    }
    public void initChatMQ(){
        try {
            RabbitMQProducer rabbitMQProducer = new RabbitMQProducer(ChatRPCConstant.MQ_NAME_CONSUMER);
            producerHashMap.put(ChatRPCConstant.MQ_NAME_CONSUMER,rabbitMQProducer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        try {
            RabbitMQConsumer rabbitMQConsumer = new RabbitMQConsumer(ChatRPCConstant.MQ_NAME_PRODUCER);
            consumerHashMap.put(ChatRPCConstant.MQ_NAME_PRODUCER,rabbitMQConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
    public void initRoomMQ(){
        try {
            RabbitMQProducer rabbitMQProducer = new RabbitMQProducer(RoomRPCConstant.MQ_NAME_CONSUMER);
            producerHashMap.put(RoomRPCConstant.MQ_NAME_CONSUMER,rabbitMQProducer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        try {
            RabbitMQConsumer rabbitMQConsumer = new RabbitMQConsumer(RoomRPCConstant.MQ_NAME_PRODUCER);
            consumerHashMap.put(RoomRPCConstant.MQ_NAME_PRODUCER,rabbitMQConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
    private RabbitMQProducer getRabbitMQProducer(String name){
        return producerHashMap.get(name);
    }
}

package rabbitmq;

//import rabbitmq.mq.consumer.RabbitMQConsumer;
import core.rpc.RoomRPCConstant;
import rabbitmq.mq.consumer.RabbitMQConsumer;
import rabbitmq.mq.producer.RabbitMQProducer;
//import rabbitmq.mq.producer.RabbitMQProducer;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class MQResource {
    private MQResource(){}
    private static MQResource mqResource = null;
    public static MQResource getMQResource(){return mqResource;}
    private HashMap<String,RabbitMQProducer> producerHashMap = new HashMap<>();
    private HashMap<String,RabbitMQConsumer> consumerHashMap = new HashMap<>();
    static {
        MQResource.mqResource = new MQResource();
        getMQResource().initRoomMQ();
    }
    public static void main(String[] args){
        MQResource.getMQResource();
    }


    public void initRoomMQ(){
        try {
            RabbitMQProducer rabbitMQProducer = new RabbitMQProducer(RoomRPCConstant.MQ_NAME_PRODUCER);
            producerHashMap.put(RoomRPCConstant.MQ_NAME_PRODUCER,rabbitMQProducer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        try {
            RabbitMQConsumer rabbitMQConsumer = new RabbitMQConsumer(RoomRPCConstant.MQ_NAME_CONSUMER);
            consumerHashMap.put(RoomRPCConstant.MQ_NAME_CONSUMER,rabbitMQConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}

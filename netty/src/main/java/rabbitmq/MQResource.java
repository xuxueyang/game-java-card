package rabbitmq;

import core.rpc.RoomRPCConstant;
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

    public RabbitMQProducer getRabbitMQProducer(){
        return producerHashMap.get(RoomRPCConstant.MQ_NAME_CONSUMER);
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
}

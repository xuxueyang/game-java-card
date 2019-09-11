package config;

import core.rpc.ChatRPCConstant;
import core.rpc.RoomRPCConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rabbitmq.mq.consumer.RabbitMQConsumer;
import rabbitmq.mq.producer.RabbitMQProducer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class RabbitMQConfiguration {
//    该方法名默认就是Bean名
    @Bean(name = RoomRPCConstant.MQ_NAME_CONSUMER)
    public RabbitMQProducer RoomRabbitMQProducer (){
        RabbitMQProducer Producer = null;
        try {
            Producer = new RabbitMQProducer(RoomRPCConstant.MQ_NAME_CONSUMER);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return Producer;
    }
    @Bean(name = RoomRPCConstant.MQ_NAME_PRODUCER)
    public RabbitMQConsumer RoomRabbitMQConsumer(){
        RabbitMQConsumer Consumer = null;
        try {
            Consumer = new RabbitMQConsumer(RoomRPCConstant.MQ_NAME_PRODUCER);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return Consumer;
    }

    @Bean(name = ChatRPCConstant.MQ_NAME_CONSUMER)
    public RabbitMQProducer ChatRabbitMQProducer (){
        RabbitMQProducer Producer = null;
        try {
            Producer = new RabbitMQProducer(ChatRPCConstant.MQ_NAME_CONSUMER);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return Producer;
    }
    @Bean(name = ChatRPCConstant.MQ_NAME_PRODUCER)
    public RabbitMQConsumer ChatRabbitMQConsumer(){
        RabbitMQConsumer Consumer = null;
        try {
            Consumer = new RabbitMQConsumer(ChatRPCConstant.MQ_NAME_PRODUCER);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return Consumer;
    }
}

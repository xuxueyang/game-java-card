package roommanager.config;

import core.rpc.RoomRPCConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import netty.rabbitmq.RabbitMQConsumer;
import netty.rabbitmq.RabbitMQProducer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class MyRabbitMQConfiguration {
//    该方法名默认就是Bean名
    @Bean
    public RabbitMQProducer RabbitMQClient(){
        RabbitMQProducer Producer = null;
        try {
            Producer = new RabbitMQProducer(RoomRPCConstant.MQ_NAME_PRODUCER);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return Producer;
    }
    @Bean
    public RabbitMQConsumer RabbitMQConsumer(){
        RabbitMQConsumer Consumer = null;
        try {
            Consumer = new RabbitMQConsumer(RoomRPCConstant.MQ_NAME_CONSUMER);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return Consumer;
    }

}

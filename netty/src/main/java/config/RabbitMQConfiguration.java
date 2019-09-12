package config;

import core.rpc.ChatRPCConstant;
import core.rpc.RoomRPCConstant;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rabbitmq.mq.consumer.RabbitMQConsumer;
import rabbitmq.mq.producer.RabbitMQProducer;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class RabbitMQConfiguration {
    @Value("${rabbitmq.mq.host}")
    private String host;

    @Value("${rabbitmq.mq.username}")
    private String username;

    @Value("${rabbitmq.mq.password}")
    private String password;
    @Value("${rabbitmq.mq.port}")
    private String port;

//    @Value("${rabbitmq.mq.vhost}")
//    private String vhost;



    /** * 因为使用了自定义的ConnectionFactory,所以需要定义RabbitAdmin * */
//    @Bean(value = "nettyRabbitAdmin")
//    public RabbitAdmin pmsRabbitAdmin(){
//        RabbitAdmin rabbitAdmin=new RabbitAdmin(pmsMqConnectionFactory());
//        return rabbitAdmin;
//    }
//    @Bean(value = "nettyConnectionFactory")
//    public ConnectionFactory pmsMqConnectionFactory(){
//        CachingConnectionFactory connectionFactory=new CachingConnectionFactory();
//        connectionFactory.setAddresses(host);
//        connectionFactory.setVirtualHost(host + ":" + port);
//        connectionFactory.setUsername(username);
//        connectionFactory.setPassword(password);
//        return connectionFactory;
//    }

//    该方法名默认就是Bean名
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

}

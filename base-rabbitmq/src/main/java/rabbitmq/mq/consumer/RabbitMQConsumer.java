package rabbitmq.mq.consumer;

import com.rabbitmq.client.*;
import core.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

//import org.springframework.stereotype.Component;

//@Component
public class RabbitMQConsumer {


    private Connection connection;
    private Channel channel;
    private String queueName;
//    private String replyQueueName;
    final BlockingQueue<String> response = new ArrayBlockingQueue<String>(100);

    //指定消费后，写入哪个队列（反过来说，要监听replyTo）
    public RabbitMQConsumer(String queueName ) throws IOException, TimeoutException {
        connection = ConnectionUtil.getConnection();

        channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
//        replyQueueName = channel.queueDeclare().getQueue();
        this.queueName = queueName;
//        channel.basicQos(1);
//        Consumer rabbitmq.mq.consumer = new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                super.handleDelivery(consumerTag, envelope, properties, body);
//                AMQP.BasicProperties properties1 = new AMQP.BasicProperties.Builder().correlationId(properties.getCorrelationId()).build();
//                String mes = new String(body, "UTF-8");
//                int num = Integer.valueOf(mes);
//                System.out.println("接收数据：" + num);
//                channel.basicPublish("", properties.getReplyTo(), properties1, String.valueOf(num).getBytes());
//                channel.basicAck(envelope.getDeliveryTag(), false);
//            }
//        };
//        channel.basicConsume(queueName, false, rabbitmq.mq.consumer);

        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                response.offer(new String(body, "UTF-8"));
            }
        });
    }
    public String consume() throws InterruptedException {
        return response.take();
    }
    public void close() throws IOException {
        connection.close();
    }
}

package rabbitmq;

import com.rabbitmq.client.*;
import core.ConnectionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

//import org.springframework.stereotype.Component;

//@Component
public class RabbitMQProducer {


    private Connection connection;
    private Channel channel;
    private String queueName;
    private String replyQueueName;
    private Map<String,Response> responseMap = new HashMap<String, Response>();
    private class Response{
        public String corrId;
        public boolean check = false;
        public String message;
    }
    public Map<String,Response> getResponseMap(){
        return responseMap;
    }
    //指定消费后，写入哪个队列（反过来说，要监听replyTo）
    public RabbitMQProducer(String queueName ) throws IOException, TimeoutException {
        connection = ConnectionUtil.getConnection();
        channel = connection.createChannel();
        replyQueueName = channel.queueDeclare().getQueue();
        this.queueName = queueName;
        channel.queueDeclare(queueName, false, false, false, null);
        channel.basicQos(1);//指该消费者在接收到队列里的消息但没有返回确认结果之前,它不会将新的消息分发给它。
        channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if(responseMap.containsKey(properties.getCorrelationId())){
                    Response response = new Response();
                    responseMap.get(properties.getCorrelationId()).check = true;
                    responseMap.get(properties.getCorrelationId()).message = new String(body, "UTF-8");
                }
            }
        });
    }
    public void close() throws IOException {
        connection.close();
    }

    public void produce(String message) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();

        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
//                .replyTo(replyQueueName)
                .build();
        channel.basicPublish("", queueName, props, message.getBytes("UTF-8"));
    }
}

package netty.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.Test;

public class test1 {
    private final static String QUEUE_NAME = "消息队列名字";

    @Test
    public void testConnect(){
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置rabbotMQ相关信息
        factory.setHost("127.0.0.1");
        factory.setUsername("root");
        factory.setPassword("123456");
        factory.setPort(5762);
        try{
            //创建新的连接
            Connection connection = factory.newConnection();

            //创建新的通道
            Channel channel = connection.createChannel();

            //声明关注的队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("Customer Waiting  Reciver message");

            // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
            Consumer consumer = new DefaultConsumer(channel) {
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) {
                    String message = new String(body);//获取到的消息


                    //开启 保存数据线程
                    //ThreadPool.genInsertDataThread(message);



                }
            };

            //自动回复队列应答 -- RabbitMQ中的消息确认机制
            channel.basicConsume(QUEUE_NAME, true, consumer);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

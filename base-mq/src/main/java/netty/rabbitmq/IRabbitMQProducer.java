package netty.rabbitmq;

import java.io.IOException;

public interface IRabbitMQProducer {
    public void produce(String message) throws IOException, InterruptedException;
    public void close() throws IOException;
}
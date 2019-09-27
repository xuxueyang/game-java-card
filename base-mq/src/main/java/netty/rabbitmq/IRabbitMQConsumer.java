package netty.rabbitmq;

import java.io.IOException;

public interface IRabbitMQConsumer {
    public String consume() throws InterruptedException;
    public void close() throws IOException;
}

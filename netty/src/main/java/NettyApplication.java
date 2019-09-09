import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.net.InetSocketAddress;

@SpringBootApplication
@ComponentScan
public class NettyApplication implements CommandLineRunner
{

    @Value("${netty.host}")
    private String host;

    @Value("${netty.port}")
    private int port;

    @Autowired
    private HttpServer httpServer;

    public static void main(String[] args){
        SpringApplication.run(NettyApplication.class,args);
    }

    @Override
    public void run(String... strings) throws Exception {
        InetSocketAddress address = new InetSocketAddress(host,port);
        httpServer.bind(address);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> httpServer.destroy()));
        httpServer.getFuture().channel().closeFuture().syncUninterruptibly();
    }
}

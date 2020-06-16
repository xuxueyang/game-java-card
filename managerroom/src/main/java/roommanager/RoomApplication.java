package roommanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
//@ComponentScan
public class RoomApplication {
    public static void main(String[] args){
        ConfigurableApplicationContext run = SpringApplication.run(RoomApplication.class, args);
    }
}

package itemmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;


@ComponentScans(value = {@ComponentScan(value = "itemmanager")})
@SpringBootApplication
@EnableEurekaClient
public class ItemApplication {
    public static void main(String[] args){
        SpringApplication.run(ItemApplication.class,args);
    }


}

package itemmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.ComponentScans;


@SpringBootApplication(scanBasePackageClasses = ItemApplication.class)
@EnableDiscoveryClient
@EnableEurekaClient
@EnableFeignClients
//@ComponentScan(basePackageClasses = ItemApplication.class)
public class ItemApplication {
    public static void main(String[] args){
        SpringApplication.run(ItemApplication.class,args);
    }


}

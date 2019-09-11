package core;

import core.spring.world.WorldManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.support.WebApplicationContextUtils;


@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
//@ComponentScans(value = {@ComponentScan(value = "main")})
@ComponentScan
@EnableDiscoveryClient
@EnableEurekaClient
@EnableFeignClients
public class GatewayApplication {
    public static void main(String[] args){
        ConfigurableApplicationContext run = SpringApplication.run(GatewayApplication.class, args);
//        WorldManager.getWorld().runWorld();
        WorldManager worldManager = (WorldManager)run.getBean("WorldManager");
//        worldManager.runWorld();

    }
}

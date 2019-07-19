package core;

import core.world.ConfigManager;
import core.world.WorldManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
//@ComponentScans(value = {@ComponentScan(value = "main")})
@ComponentScan
public class GatewayApplication {
    public static void main(String[] args){
        SpringApplication.run(GatewayApplication.class,args);
        ConfigManager.getGameConfig().init();
        WorldManager.getWorld().runWorld();

    }
}

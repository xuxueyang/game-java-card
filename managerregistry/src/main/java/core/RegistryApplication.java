package core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

//@ComponentScans(value = {@ComponentScan(value = "core")})
@SpringBootApplication
@EnableEurekaServer
public class RegistryApplication {
    public static void main(String[] args){
        SpringApplication.run(RegistryApplication.class,args);
    }
}

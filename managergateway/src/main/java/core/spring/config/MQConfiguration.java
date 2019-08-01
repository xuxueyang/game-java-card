package core.spring.config;

import core.inter.MQFirstInterface;
import core.spring.MQ.SpringMQFirstService;
import core.spring.config.Condition.MQSpringCondition;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

@Configuration
public class MQConfiguration {

    @Conditional({MQSpringCondition.class})
    @Bean(name = "MQFirstInterface")
    public MQFirstInterface getMQFirstInterface(){
        return new SpringMQFirstService();
    }


}

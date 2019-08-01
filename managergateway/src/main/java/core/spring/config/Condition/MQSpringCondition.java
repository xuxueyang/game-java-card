package core.spring.config.Condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class MQSpringCondition implements Condition {
    private static final String CHARSET = "UTF-8";

    /**
     * @param conditionContext:判断条件能使用的上下文环境
     * @param annotatedTypeMetadata:注解所在位置的注释信息
     * */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Properties props = new Properties();
        try {
            props.load(new InputStreamReader(MQSpringCondition.class.getClassLoader().getResourceAsStream("game-config.properties"),
                    CHARSET));
            //初始化消息队列
            String property = props.getProperty("message.queue.type");
            if(!"spring".equals(property)){
                //还有从redis获取，还有从其他的
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
package core.world;

import core.spring.MQResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Properties;

public  class ConfigManager {
    private static final String CHARSET = "UTF-8";
    public static String MQType = "spring";
    private ConfigManager(){


    }
    private final static  ConfigManager _instance = new ConfigManager();
    public static ConfigManager getGameConfig(){
        return _instance;
    }
    public void init(){
        Properties props = new Properties();
        try {
            props.load(new InputStreamReader(ConfigManager.class.getClassLoader().getResourceAsStream("game-config.properties"),
                    CHARSET));
            //初始化消息队列
            String property = props.getProperty("message.queue.type");
            if("spring".equals(property)){
                MQType = "spring";
                MQManager mqManager = MQManager.getMQManager();
                Field queue = null;
                try {
                    queue = MQManager.class.getField("queue");
                    queue.setAccessible(true);
                    try {
                        queue.set(mqManager, MQResource.getQueue());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

            }
            //还有从redis获取，还有从其他的
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

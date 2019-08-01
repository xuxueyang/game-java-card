package core.spring.world;

import core.inter.MQFirstInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 管理世界的
 */
@Component("WorldManager")
public  class WorldManager
{
    public static final Logger log = LoggerFactory.getLogger(WorldManager.class);

    private static AtomicInteger atomicInteger = new AtomicInteger(1);
    @Resource(name = "MQFirstInterface")
    private MQFirstInterface mqFirstInterface;

    private void initWorld(){
        log.info("——————————————————————start开始初始化游戏世界————————————————————");
        //TODO 加载世界配置
        //TODO 加载野外怪物列表
        //TODO 加载功能点
        log.info("——————————————————————end开初始化游戏世界————————————————————");

    }
    public WorldManager(){
        initWorld();
    }
    public void runWorld(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(_world, 0,20);
    }

    private final  world _world = new world();


    private final  class world  extends TimerTask{
        public world(){

        }

        @Override
        public void run() {
//            System.out.println("世界开始运行");
            atomicInteger.getAndIncrement();
            log.info("世界开始运行"+ atomicInteger.get());
            //先监听端口
            Object test1 = mqFirstInterface.pollObjectByKey(new Byte("1"));
            if(test1!=null)
                System.out.println(test1);
        }
    }
}

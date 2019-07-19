package core.world;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 管理世界的
 */
public  class WorldManager
{
    private void initWorld(){
        //TODO 加载世界配置
        //TODO 加载野外怪物列表
        //TODO 加载功能点

    }
    private WorldManager(){
        initWorld();
    }
    public void runWorld(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(_world, 0,20);
    }
    private  static WorldManager _instance = null;
    public static WorldManager getWorld(){
        if(_instance==null){
            _instance= new WorldManager();
        }
        return _instance;
    }
    private final static world _world = new world();


    private final static class world  extends TimerTask{
        public world(){

        }

        @Override
        public void run() {
//            System.out.println("世界开始运行");
            //先监听端口
            Object test1 = MQManager.getMQManager().queue.getObjectByKey("test");
            if(test1!=null)
                System.out.println(test1);
        }
    }
}

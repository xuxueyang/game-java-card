package core.world;

import core.inter.MQFirstInterface;

public  class MQManager {
    public MQFirstInterface queue = null;
    private MQManager(){

    }
    private final static  MQManager _instance = new MQManager();
    public static MQManager getMQManager() {
//        if(_instance.queue==null){
//            throw new Exception("MQManager的queue未设置");
//        }
        return _instance;
    }
    //可以从mqresource获取消息，也可以从缓存中获取

}

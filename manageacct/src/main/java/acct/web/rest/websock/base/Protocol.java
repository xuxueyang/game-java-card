package acct.web.rest.websock.base;

import java.util.ArrayList;
import java.util.List;

public class Protocol {
    public final static String SPLIT = "_";
    // 一级协议就单例，具体剩下的以2级方式处理，这样架构会简单点吧
    //3开头的是群发消息
    public enum messageType{
        All,//群发
        One //单发
    }

    // 1开头的是系统消息
    public final static String FirstProtocol_SYS = "1";

    public final static String OnCreated = "1_1";//创建完毕后服务端发送
    
    public final static String OnCreatedSys = "1_1_1";//创建完毕后服务端发送
    public final static String CloseSession = "1_1_2";//关闭连接

    public final static String OnClose = "1_2";
    public final static String ERROR = "-1";
    public final static String SUCCESS = "0";


    //2开头的是在线聊天
    public final static String FirstProtocol_CHAT = "2";
    public final static String sendChatMessage = "2_1";// 发送聊天信息




    public final static List<String> protocols = new ArrayList<>();
    static {
        protocols.add(OnCreated);
        protocols.add(OnCreatedSys);
        protocols.add(OnClose);
        protocols.add(ERROR);
        protocols.add(sendChatMessage);
        protocols.add(CloseSession);


    }
}

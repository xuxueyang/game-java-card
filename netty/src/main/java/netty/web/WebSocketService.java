package netty.web;


import com.alibaba.fastjson.JSON;
import netty.config.GetHttpSessionConfigurator;
import netty.handler.ServerHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint(value = "/websocket",configurator = GetHttpSessionConfigurator.class)
//@ServerEndpoint(value = "/websocket/{token}")
@Component
public class WebSocketService {

    ServerHandler serverHandler = ServerHandler.serverHandler;


    private HttpSession httpSession;
    public static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);




    static {
        // 注册系统消息处理器
//        SysProtocolHandle sysProtocolHandle = new SysProtocolHandle();
//        initAddHandle(sysProtocolHandle);
        // 聊天消息处理器

    }


    //连接
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        //这里作为websocket的接口，不做修改
//        if(this.serverHandler==null){
//            this.serverHandler
//        }

//        原文：https://blog.csdn.net/qq_38162143/article/details/78275018
//        Map<String, List<String>> requestParameterMap = session.getRequestParameterMap();
        try {
            this.serverHandler.handlerAdded(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if(!requestParameterMap.containsKey("token")||!requestParameterMap.containsKey("userId")){
//            // 参数不够
////            _sendMsg(session,DataProtocol.getCheckData("参数错误",Protocol.CloseSession));
//            return;
//        }

    }

    //关闭
    @OnClose
    public void onClose(Session session) {
        try {
            this.serverHandler.handlerRemoved(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //接收消息   客户端发送过来的消息
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            this.serverHandler.channelRead(session,message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //异常
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生异常!");
        throwable.printStackTrace();
    }
    //统计在线人数
    private static int onlineCount = 0;
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        onlineCount--;
    }

}

package handler;

import org.springframework.stereotype.Component;
import rpc.AcctRpcClient;
import com.alibaba.fastjson.JSON;
import core.core.RequestDTO;
import core.core.ReturnResultDTO;
import core.protocol.Protocol;
import core.util.MD5Util;
import factory.ResDTOFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import rabbitmq.MQResource;

import javax.annotation.PostConstruct;
import java.lang.ref.SoftReference;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class ServerHandler extends ChannelInboundHandlerAdapter {
    public static ServerHandler serverHandler;
    public ServerHandler(){}

    private static Log log = LogFactory.getLog(ServerHandler.class);

    private static final boolean TEST = false;

    @Autowired
    private AcctRpcClient acctRpcClient;
    @PostConstruct
    public void init(){
        serverHandler = this;
        serverHandler.acctRpcClient = this.acctRpcClient;
    }

    private static ConcurrentHashMap<String,Long> channelIdUserId = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Long,String> userIdChannelId = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Long,ChannelHandlerContext> userIdChannels = new ConcurrentHashMap<>();

    static {
        initManager();
    }
    private static void initManager() {

        if(TEST){
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if(!userIdChannelId.isEmpty()){
                        try {
                            Enumeration<Long> keys = userIdChannels.keys();
                            while (keys.hasMoreElements()){
                                Long aLong = keys.nextElement();
                                ChannelHandlerContext ctx = userIdChannels.get(aLong);
                                RequestDTO o = new RequestDTO<>();
                                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                                objectObjectHashMap.put("message","hello world");
                                o.setData(objectObjectHashMap);
                                ctx.channel().writeAndFlush(o);
                            }
                        } catch (Exception e) {
                            log.error("推送消息 error" + e.getMessage());
                        }
                    }
                }
            },0,5L  * 1000);
        }

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        log.info(ctx.channel().id() + "进来了");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        //通知客户端链接建立成功
        SocketChannel channel = (SocketChannel) ctx.channel();
        String str = "通知客户端链接建立成功" + " " + new Date() + " " + channel.localAddress().getHostString() + "\r\n";
        RequestDTO o = new RequestDTO();
        o.setMd5(str);
        ctx.channel().writeAndFlush(o);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        log.info(ctx.channel().id() + "离开了");
        if(channelIdUserId.contains(ctx.channel().id().asLongText())){
            Long aLong = channelIdUserId.get(ctx.channel().id().asLongText());
            userIdChannelId.remove(aLong);
            userIdChannels.remove(aLong);
            channelIdUserId.remove(ctx.channel().id().asLongText());
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("ServerHandler ========================= ");
        RequestDTO dto = (RequestDTO) msg;
        if(!channelIdUserId.contains(ctx.channel().id().asLongText())){
            // 説明第一次接入，需要驗證token
            if(!TEST){
                if(!Protocol.Area.Netty.equals(dto.getArea())
                        ||!Protocol.Type.LOGIN.equals(dto.getType())
                        ||dto.getTimestamp()==null
                        ||dto.getAreaL()==null
                        ||dto.getUserId()==null
                        ||dto.getMd5() == null){
                    return;
                }
                //校驗token
                SoftReference<RequestDTO> newDto = new SoftReference<>(new RequestDTO());
                newDto.get().setData(dto.getUserId());
                newDto.get().setAreaL(dto.getAreaL());
                ReturnResultDTO returnResultDTO = null;
                try {
                    returnResultDTO = serverHandler.acctRpcClient.getTokenById(newDto.get());
                }catch (Exception e){
                    log.error("rpc acct失败" + e.getMessage());
                }
                if(returnResultDTO==null||returnResultDTO.getData()==null){
                    log.info("未登录");
                    ctx.close();
                    return;
                }
                if(dto.getMd5()
                        .equals(MD5Util.MD5(returnResultDTO.getData().toString() + dto.getTimestamp()))){
                    channelIdUserId.put(ctx.channel().id().asLongText(),dto.getUserId());
                    userIdChannelId.put(dto.getUserId(),ctx.channel().id().asLongText());
                    userIdChannels.put(dto.getUserId(),ctx);
                    ctx.channel().writeAndFlush(ResDTOFactory.getSuccessConnected());
                    return;

                }
            }else{
                channelIdUserId.put(ctx.channel().id().asLongText(),dto.getUserId());
                userIdChannelId.put(dto.getUserId(),ctx.channel().id().asLongText());
                userIdChannels.put(dto.getUserId(),ctx);
            }

        }

        //这样就内部以UserId维系连接
        dto.setUserId(channelIdUserId.get(ctx.channel().id().asLongText()));
        String json = JSON.toJSONString(dto);
        log.info(json);
        try {
            MQResource.getMQResource().getRabbitMQProducer().produce(json);
        }catch (Exception e){
            log.error("消息队列出错" + e.getMessage());
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // TODO Auto-generated method stub
        log.info("异常信息：\r\n" + cause.getMessage());

//        cause.printStackTrace();
        ctx.close();
    }

}

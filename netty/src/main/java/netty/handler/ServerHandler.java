package netty.handler;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import netty.config.DefaultChannelInitializer;
import core.manager.UserObjectManager;
import netty.handler.inter.AbstactSelfServerHandler;
import netty.handler.inter.WebServerHandlerAdapter;
import org.springframework.stereotype.Component;
import netty.rpc.AcctRpcClient;
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

import javax.annotation.PostConstruct;
import javax.websocket.Session;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class ServerHandler extends ChannelInboundHandlerAdapter implements WebServerHandlerAdapter {
    public static ServerHandler serverHandler;

    public ServerHandler(){}

    private static Log log = LogFactory.getLog(ServerHandler.class);

    private static final boolean TEST = false;

//    public ConcurrentHashMap<Byte,AbstactSelfServerHandler> handlers = new ConcurrentHashMap<>();
    public ArrayList<AbstactSelfServerHandler> handlers = new ArrayList<>();

    @Autowired
    private AcctRpcClient acctRpcClient;
    @Autowired
    private ChatServerHandler chatServerHandler;
    @Autowired
    private RoomServerHandler roomServerHandler;
    @Autowired
    private FileServerHandler fileServerHandler;

    @PostConstruct
    public void init(){
        serverHandler = this;
//        serverHandler.acctRpcClient = this.acctRpcClient;

        Field[] fields = serverHandler.getClass().getFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if(field.getClass().isAssignableFrom(AbstactSelfServerHandler.class) ){
                try {
//                    AbstactSelfServerHandler handler = (AbstactSelfServerHandler)field.get(serverHandler);
//                    serverHandler.handlers.put(handler.getType(),handler);
                    AbstactSelfServerHandler handler = (AbstactSelfServerHandler)field.get(serverHandler);
                    serverHandler.handlers.add(handler);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

//        serverHandler.chatServerHandler = this.chatServerHandler;
//        serverHandler.fileServerHandler = this.fileServerHandler;
//        serverHandler.roomServerHandler = this.roomServerHandler;

//        serverHandler.handlers.add(this.chatServerHandler);
//        serverHandler.handlers.add(this.roomServerHandler);
//        serverHandler.handlers.add(this.fileServerHandler);
    }

    private  UserObjectManager<AbstactSelfServerHandler.Channel> manager = new UserObjectManager<AbstactSelfServerHandler.Channel>(1);


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        log.info(ctx.channel().id() + "进来了");
    }
    @Override
    public void handlerAdded(Session session) throws Exception {
        log.info(session.getId() + "进来了");
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
    public void channelActive(Session session) throws Exception {

    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        log.info(ctx.channel().id() + "离开了");
        if(manager.containsValue(ctx.channel().id().asLongText())){
            manager.removeByValue(ctx.channel().id().asLongText());

        }
    }
    @Override
    public void handlerRemoved(Session session) throws Exception {
        log.info(session.getId() + "离开了");
        if(manager.containsValue(session.getId())){
            manager.removeByValue(session.getId());

        }
    }
//    @PostConstruct
//    private void initManager() {
//        Timer timer = new Timer();
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                List<Long> keys = manager.keys();
//                if(keys!=null){
//                    keys.forEach(item -> {
//                        ChannelHandlerContext object = manager.getObject(item);
//                        if(object!=null){
//                            object.channel().writeAndFlush(MsgUtil.buildObj("XXXXXXXXXXXXXXXXXXXX"));
//                        }
//                    });
//                }
//            }
//        };
//        timer.scheduleAtFixedRate(timerTask, 0,5);
//    }
    @Override
    public void channelRead(Session session, Object msg) throws Exception {
        _channelRead(new AbstactSelfServerHandler.Channel(session),msg);
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        _channelRead(new AbstactSelfServerHandler.Channel(ctx),msg);
    }
    public void _channelRead(AbstactSelfServerHandler.Channel channel, Object msg) throws Exception {
        log.info("ServerHandler ========================= ");
        RequestDTO dto = null;
        if(msg instanceof FullHttpRequest){
            String string = ((FullHttpRequest) msg).content().toString(CharsetUtil.UTF_8);
            if("".equals(string)){
                return;
            }else{
                dto = (RequestDTO)JSON.parseObject(string,RequestDTO.class);
            }
        }else  if(DefaultChannelInitializer.useProtobuf){
            try {
                if(msg instanceof String){
                    //TODO 转不成，bug
                    dto = JSON.parseObject(
                            JSON.parseObject(
                                    (String) msg
                                    , netty.proto.dto.RequestDTO.RequestDTOProto.class).getMessage()
                            ,RequestDTO.class);
                }else{
                    dto = JSON.parseObject(((netty.proto.dto.RequestDTO.RequestDTOProto) msg).getMessage(),RequestDTO.class);
                }
            }catch (Exception e){
                return;
            }
        }else{
            dto = (RequestDTO) msg;
        }
        log.info(dto);
        if(dto==null)
            return;
        if(!manager.containsValue(channel.getId())){
            // 説明第一次接入，需要驗證token
            if(!TEST){
                if(Protocol.Area.Netty - dto.getArea() != 0
                        ||Protocol.Type.LOGIN  - dto.getType() != 0
                        ||dto.getTimestamp()==null
                        ||dto.getAreaL()== 0
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
                    log.error("netty.rpc acct失败" + e.getMessage());
                }
                if(returnResultDTO==null||returnResultDTO.getData()==null){
                    log.info("未登录");
                    _close(channel);
                    return;
                }
                if(dto.getMd5()
                        .equals(MD5Util.MD5(returnResultDTO.getData().toString() + dto.getTimestamp()))){
                    manager.put(dto.getUserId(),channel.getId(),channel);
                    channel.sendMsg(ResDTOFactory.getSuccessConnected());
                    final Long userId = dto.getUserId();
                    serverHandler.handlers.forEach(item -> {
                        try {
                            item.channelActive(channel, userId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    return;

                }
            }else{
                manager.put(dto.getUserId(),channel.getId(),channel);
            }

        }

        //这样就内部以UserId维系连接
        dto.setUserId(manager.getKeyByValue(channel.getId()));
        switch (dto.getType()){
            case Protocol.Type.CHAT:{
                serverHandler.chatServerHandler.channelRead(channel,dto);
            }break;
            case Protocol.Type.ROOM:{
                serverHandler.roomServerHandler.channelRead(channel,dto);
            }break;
            case Protocol.Type.FILE:{
                serverHandler.fileServerHandler.channelRead(channel,dto);
            }break;
            default:{
                log.error("未知协议");
            }
        }
//        ReferenceCountUtil.release(msg);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // TODO Auto-generated method stub
        log.info("异常信息：\r\n" + cause.getMessage());
        cause.printStackTrace();
        _close(new AbstactSelfServerHandler.Channel(ctx));
    }
    private void _close(AbstactSelfServerHandler.Channel ctx) throws Exception{
        serverHandler.handlers.forEach(item -> {
            try {
                item.cloes(ctx);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ctx.close();

    }

}

package handler;

import com.alibaba.fastjson.JSONObject;
import com.googlecode.protobuf.format.JsonFormat;
import config.DefaultChannelInitializer;
import core.manager.UserObjectManager;
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
    @Autowired
    private ChatServerHandler chatServerHandler;
    @Autowired
    private RoomServerHandler roomServerHandler;

    @PostConstruct
    public void init(){
        serverHandler = this;
        serverHandler.acctRpcClient = this.acctRpcClient;
        serverHandler.chatServerHandler = this.chatServerHandler;
        serverHandler.roomServerHandler = this.roomServerHandler;
    }

    private  UserObjectManager<ChannelHandlerContext> manager = new UserObjectManager<ChannelHandlerContext>(1);


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
        if(manager.containsValue(ctx.channel().id().asLongText())){
            manager.removeByValue(ctx.channel().id().asLongText());

        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("ServerHandler ========================= ");
        RequestDTO dto = null;
        if(DefaultChannelInitializer.useProtobuf){
            dto = (RequestDTO)JSON.parseObject(((proto.dto.RequestDTO.RequestDTOProto) msg).getMessage(),RequestDTO.class);
        }else{
            dto = (RequestDTO) msg;
        }
        log.info(dto);
        if(!manager.containsValue(ctx.channel().id().asLongText())){
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
                    log.error("rpc acct失败" + e.getMessage());
                }
                if(returnResultDTO==null||returnResultDTO.getData()==null){
                    log.info("未登录");
                    close(ctx);
                    return;
                }
                if(dto.getMd5()
                        .equals(MD5Util.MD5(returnResultDTO.getData().toString() + dto.getTimestamp()))){
                    manager.put(dto.getUserId(),ctx.channel().id().asLongText(),ctx);
                    ctx.channel().writeAndFlush(ResDTOFactory.getSuccessConnected());
                    serverHandler.chatServerHandler.channelActive(ctx,dto.getUserId());
                    serverHandler.roomServerHandler.channelActive(ctx,dto.getUserId());
                    return;

                }
            }else{
                manager.put(dto.getUserId(),ctx.channel().id().asLongText(),ctx);
            }

        }

        //这样就内部以UserId维系连接
        dto.setUserId(manager.getKeyByValue(ctx.channel().id().asLongText()));
        switch (dto.getType()){
            case Protocol.Type.CHAT:{
                serverHandler.chatServerHandler.channelRead(ctx,dto);
            }break;
            case Protocol.Type.ROOM:{
                serverHandler.roomServerHandler.channelRead(ctx,dto);
            }break;
            default:{
                log.error("未知协议");
            }
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
        serverHandler.chatServerHandler.cloes(ctx);
        serverHandler.roomServerHandler.cloes(ctx);
        ctx.close();
    }
    private void close(ChannelHandlerContext ctx){
        serverHandler.chatServerHandler.cloes(ctx);
        serverHandler.roomServerHandler.cloes(ctx);

    }

}

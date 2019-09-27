package netty.handler.inter;

import com.alibaba.fastjson.JSON;
import core.core.RequestDTO;
import core.manager.UserObjectManager;
import io.netty.channel.ChannelHandlerContext;

import javax.websocket.Session;

public abstract class AbstactSelfServerHandler<T,A> {
    public static class Channel{
        private Object object;
        private String id;
        public  Channel(Object channel) throws Exception{
            this.object = channel;
            if(object instanceof ChannelHandlerContext){
                this.id =  ((ChannelHandlerContext)object).channel().id().asLongText();
            }else if(object instanceof Session){
                this.id = ((Session)object).getId();
            }else{
                throw new Exception("未知的channel");
            }
        }
        public String getId(){
            return id;
        }
        public void sendMsg(Object dto) throws Exception{
            if(object instanceof ChannelHandlerContext){
                ((ChannelHandlerContext)object).channel().writeAndFlush(dto);
            }else if(object instanceof Session){
//                ((Session)object).getBasicRemote().sendText(JSON.toJSONString(dto));
                if(dto instanceof netty.proto.dto.RequestDTO.RequestDTOProto){
                    ((Session)object).getBasicRemote().sendText(((netty.proto.dto.RequestDTO.RequestDTOProto)dto).getMessage());
                }else{
                    ((Session)object).getBasicRemote().sendText(JSON.toJSONString(dto));
                }

            }
        }
        public void  close() throws Exception{
            if(object instanceof ChannelHandlerContext){
                ((ChannelHandlerContext)object).close();
            }else if(object instanceof Session){
                ((Session)object).close();
            }
        }
    }
    protected   UserObjectManager<Channel> userObjectManager = new UserObjectManager<Channel>(1);

    public  void channelRead(Channel ctx, T dto) throws Exception{

    }
    public void channelActive(Channel ctx,Long userId) throws Exception{
        userObjectManager.put(userId,ctx.getId(),ctx);
    }
//    void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception;
    public void cloes(Channel ctx){
        userObjectManager.removeByValue(ctx.getId());
    }


    // 线程不断读取数据，并且推送自己以外的人（自己encode，自己decode)
    protected String encode(A object){
        return JSON.toJSONString(object);
    }
    protected A decode(String json,Class<A> aClass){
        return JSON.parseObject(json, aClass);
    }

    public abstract byte getType();
    public void init(){

    }
}

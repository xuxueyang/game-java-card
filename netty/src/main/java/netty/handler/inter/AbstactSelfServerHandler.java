package netty.handler.inter;

import com.alibaba.fastjson.JSON;
import core.manager.UserObjectManager;
import io.netty.channel.ChannelHandlerContext;

public abstract class AbstactSelfServerHandler<T,A> {
    protected   UserObjectManager userObjectManager = new UserObjectManager(1);

    public  void channelRead(ChannelHandlerContext ctx, T dto) throws Exception{

    }
    public void channelActive(ChannelHandlerContext ctx,Long userId) throws Exception{
        userObjectManager.put(userId,ctx.channel().id().asLongText(),ctx);
    }
//    void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception;
    public void cloes(ChannelHandlerContext ctx){
        userObjectManager.removeByValue(ctx.channel().id().asLongText());
    }


    // 线程不断读取数据，并且推送自己以外的人（自己encode，自己decode)
    protected String encode(A object){
        return JSON.toJSONString(object);
    }
    protected A decode(String json,Class<A> aClass){
        return JSON.parseObject(json, aClass);
    }
}

package netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import core.core.RequestDTO;
import core.protocol.CommonProtocol;
import core.protocol.Protocol;
import core.rpc.CommonRPCConstant;
import factory.ResDTOFactory;
import netty.handler.inter.AbstactSelfServerHandler;
import netty.rabbitmq.RabbitMQConsumer;
import netty.rabbitmq.RabbitMQProducer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import rx.internal.util.LinkedArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class MatchServerHandler extends AbstactSelfServerHandler<RequestDTO,RequestDTO> {
    private static Log log = LogFactory.getLog(MatchServerHandler.class);

    @Override
    public byte getType() {
        return Protocol.Type.MATCH;
    }
    private LinkedBlockingQueue<RequestDTO> storageProducer = new LinkedBlockingQueue<RequestDTO>();



    public void SERVER_MATCH_ADD(RequestDTO dto) throws Exception{
        String key = dto.getData().toString();
        if(matchMap.containsKey(key)){
            LinkedBlockingQueue<Long> longs = matchMap.get(key);
            if(!longs.contains(dto.getUserId())){
                longs.put(dto.getUserId());
            }
            //返回数据，正在匹配中
            userObjectManager.getObject(dto.getUserId()).sendMsg(
                    ResDTOFactory.getDTO(Protocol.Type.MATCH,CommonProtocol.SERVER_MATCH_ADD));
        }
        //TODO 触发一下匹配删选方法
    }
    public void SERVER_MATCH_CANCEL(RequestDTO dto) throws Exception{
        String key = dto.getData().toString();
        if(matchMap.containsKey(key)){
            LinkedBlockingQueue<Long> longs = matchMap.get(key);
            if(longs.contains(dto.getUserId())){
                matchMap.get(key).remove(dto.getUserId());
            }
            //返回数据，正在匹配中
            userObjectManager.getObject(dto.getUserId()).sendMsg(
                    ResDTOFactory.getDTO(Protocol.Type.MATCH,CommonProtocol.SERVER_MATCH_CANCEL));
        }
    }
    public void receiveMessage(RequestDTO dto) throws Exception{
        //取消重连，直接调用请求吧，不走netty--
        switch (dto.getProtocol()){
            case CommonProtocol.SERVER_MATCH_ADD:
            {
                SERVER_MATCH_ADD(dto);
            }break;
            case CommonProtocol.SERVER_MATCH_CANCEL:
            {
                SERVER_MATCH_CANCEL(dto);
            }break;
        }
    }
    public  void channelRead(Channel ctx, RequestDTO dto) throws Exception{
        try {
            storageProducer.put(dto);
        }catch (Exception e){
            log.error("消息队列出错" + e.getMessage());
        }
    }
//    @PostConstruct
    @Override
    public void init() {
        Runnable _runnableConsumer = new Runnable() {
            @Override
            public void run() {
                //循环读取读出消息
                while (true){
                    if(storageProducer!=null){
                        try {
                            receiveMessage(storageProducer.take());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        };
        Thread _threadConsumer  = new Thread(_runnableConsumer);
        _threadConsumer.run();
        //生成一些匹配队列
        matchMap.put("auto-chess",new LinkedBlockingQueue<Long>(200));
    }
    private HashMap<String,LinkedBlockingQueue<Long>> matchMap = new HashMap<>();
}

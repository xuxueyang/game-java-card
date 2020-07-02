package netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import core.core.RequestDTO;
import core.core.ReturnResultDTO;
import core.protocol.AutoChessRoomProtocol;
import core.protocol.CommonProtocol;
import core.protocol.Protocol;
import core.rpc.CommonRPCConstant;
import core.rpc.RoomRPCConstant;
import core.rpc.client.RoomFeignClient;
import factory.ResDTOFactory;
import netty.handler.inter.AbstactSelfServerHandler;
import netty.rabbitmq.RabbitMQConsumer;
import netty.rabbitmq.RabbitMQProducer;
//import netty.rpc.RoomRpcClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.internal.util.LinkedArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class MatchServerHandler extends AbstactSelfServerHandler<RequestDTO,RequestDTO> {
    private static Log log = LogFactory.getLog(MatchServerHandler.class);

    @Autowired
//    private RoomRpcClient roomRpcClient;
    private RoomFeignClient roomRpcClient;
    @Override
    public byte getType() {
        return Protocol.Type.MATCH;
    }
    private LinkedBlockingQueue<RequestDTO> storageProducer = new LinkedBlockingQueue<RequestDTO>();
    private LinkedBlockingQueue<Long> checkedQueue = new LinkedBlockingQueue<>();
    private Runnable _runnableConsumer = new Runnable() {
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

    public void SERVER_MATCH_ADD(RequestDTO dto) throws Exception{
        String key = dto.getData().toString();
        if(matchMap.containsKey(key)){
            matchMap.get(key).pushUser(dto.getUserId());
            //返回数据，正在匹配中
            userObjectManager.getObject(dto.getUserId()).sendMsg(
                    ResDTOFactory.getDTO(Protocol.Type.MATCH,CommonProtocol.SERVER_MATCH_ADD));
        }
        //TODO 触发一下匹配删选方法
    }
    public void SERVER_MATCH_CANCEL(RequestDTO dto) throws Exception{
        String key = dto.getData().toString();
        if(checkedQueue.contains(dto.getUserId())){
            userObjectManager.getObject(dto.getUserId()).sendMsg(
                    ResDTOFactory.getDTO(Protocol.Type.MATCH,CommonProtocol.SERVER_MATCH_CANCEL_ERROR_IS_IN_INIT_ROOM));
            return;
        }
        if(matchMap.containsKey(key)){
            matchMap.get(key).removeUser(dto.getUserId());
            //返回数据，正在匹配中
        }
        userObjectManager.getObject(dto.getUserId()).sendMsg(
                ResDTOFactory.getDTO(Protocol.Type.MATCH,CommonProtocol.SERVER_MATCH_CANCEL));
    }
    public void receiveMessage(RequestDTO dto) throws Exception{
        //取消重连，直接调用请求吧，不走netty--
        log.info("处理match消息");
        switch (dto.getProtocol()){
            case CommonProtocol.CLIENT_MATCH_ADD:
            {
                SERVER_MATCH_ADD(dto);
            }break;
            case CommonProtocol.CLIENT_MATCH_CANCEL:
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
//    public MatchServerHandler(){
//        this.init();
//    }
    private boolean _hasInit = false;
    @Override
    public void init() {
      if(!_hasInit){
          log.debug("init match");
          new Thread(_runnableConsumer).start();
          //生成一些匹配队列
          matchMap.put(RoomRPCConstant.RoomKey.autochess.getStatus(), new _MatchQueue(RoomRPCConstant.RoomKey.autochess.getStatus(),2));
      }else{
          _hasInit = true;
      }
    }
    private HashMap<String,_MatchQueue> matchMap = new HashMap<>();
    private class _MatchQueue{
        private int needPeopleNum;
        public boolean pushUser(Long userId){
            if(!queue.contains(userId)&&!checkedQueue.contains(userId)){
               queue.add(userId);
               match();
            }
            return true;
        }
        public boolean removeUser(Long userId){
            if(queue.contains(userId)){
                queue.remove(userId);
            }
            return true;
        }
        private String key;
        public _MatchQueue(String key,int needPeopleNum){
            this.key = key;
            this.needPeopleNum = needPeopleNum;
        }
        public void match(){
            try {
                if(queue.size()>=this.needPeopleNum){
                    Long[] longs = new Long[needPeopleNum];
                    synchronized (queue){
                        for(int i=0;i<needPeopleNum;i++){
                            longs[i] = queue.take();
                            checkedQueue.put(longs[i]);
                        }
                    }
                    //发送匹配成功
                    try {
                        RequestDTO dto = new RequestDTO();
//                        dto.setAreaL(userObjectManager.getAreaL());
                        HashMap<String, Object> data = new HashMap<String,Object>();
                        data.put(RoomRPCConstant.Key.roomKey.name(),key);
                        data.put(RoomRPCConstant.Key.userIds.name(),longs);
                        data.put(RoomRPCConstant.Key.areaL.name(),userObjectManager.getAreaL());
                        dto.setData(data);
                        ReturnResultDTO returnResultDTO = roomRpcClient.CREATE_ROOM(dto);
                        if(returnResultDTO.getReturnCode().startsWith("200")){
                            //通知
                            //移除check隊列
                            String roomId = returnResultDTO.getData().toString();
                            for (int i = 0; i < longs.length; i++) {
                                RequestDTO tmp = ResDTOFactory.getDTO(Protocol.Type.MATCH, CommonProtocol.SERVER_ROOM_SUCCESS_CREATED);
                                tmp.setData(roomId);
                                userObjectManager.getObject(longs[i])
                                        .sendMsg(tmp);
                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //TODO 处理创建失败的情况
                    for (Long aLong : longs) {
                        checkedQueue.remove(aLong);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        private LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<Long>(200);
    }
}

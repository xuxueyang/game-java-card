package core.spring.service.match;

import core.core.RequestDTO;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import core.dto.acct.dto.UserInfo;
import core.rpc.MatchRPCConstant;
import core.spring.aop.MatchTwoRoomRpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import util.logger.LogUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class MatchTwoRoomService {
    public static final Logger log = LoggerFactory.getLogger(LogUtil.class);

    @Autowired
    private MatchTwoRoomRpcClient matchTwoRoomRpcClient;
    private static ConcurrentHashMap<Long,MatchMeta> _userMetaMap = new ConcurrentHashMap();
    private static LinkedBlockingQueue<Long> _userIdsQueue = new LinkedBlockingQueue(100);

    public boolean join(UserInfo userInfo) {
        if(_userIdsQueue.contains(userInfo.getId())){
            return false;
        }
        //如果已經在隊列中，那麽不能加入，如果已經在隊列中需要
        MatchMeta matchMeta = new MatchMeta(userInfo.getAccountId(), userInfo.getId(),userInfo.getArea());
        boolean offer = _userIdsQueue.offer(matchMeta.userId);
        if(offer){
            _userMetaMap.put(matchMeta.userId,matchMeta);
        }else{
            return false;
        }
//        match();
        return true;
    }
    @Scheduled(cron ="0/20 * * * * ? ")
    public void match(){
        //按照算法匹配，現在就優先前2個吧
        if(_userIdsQueue.size()>=2){
//            Long one = _userIdsQueue.peek();
//            if(one!=null){
//                MatchMeta matchMeta = _userMetaMap.get(one);
//
//            }
//            Long two = _userIdsQueue.poll();
            //先不考慮同步
            try {
                Long oneUserId = _userIdsQueue.take();
                Long twoUserId = _userIdsQueue.take();
                MatchMeta one = _userMetaMap.get(oneUserId);
                MatchMeta two = _userMetaMap.get(twoUserId);
                RequestDTO dto = new RequestDTO();
                Map<String,Long> map = new HashMap<>();
                map.put(MatchRPCConstant.Key.oneUser.name(),one.userId);
                map.put(MatchRPCConstant.Key.twoUser.name(),two.userId);
                map.put(MatchRPCConstant.Key.area.name(),Long.parseLong(one.area.toString()));

                dto.setData(map);
                LogUtil.debug(""+oneUserId,""+twoUserId,MatchRPCConstant.SERVICE_NAME,"match-two-room",dto);
                ReturnResultDTO returnResultDTO = matchTwoRoomRpcClient.CREATE_TWO_ROOM(dto);
                if(!returnResultDTO.getReturnCode().startsWith(ReturnCode.SUCCESS)){
                    //TODO 説明匹配失敗
                    log.error(returnResultDTO.getData().toString());
                    //通知一下最好
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public void quit(UserInfo userInfo) {
        if(_userIdsQueue.contains(userInfo.getId())){
            MatchMeta matchMeta = _userMetaMap.get(userInfo.getId());
            if(matchMeta.lock==0||!matchMeta.remove){
                //todo remove会锁全部，o(n)到時候可以用metaMap做標志位，然後定時器清除
                _userMetaMap.remove(userInfo.getId());
                _userIdsQueue.remove(userInfo.getId());

                RequestDTO dto = new RequestDTO();
                Map<String,Long> map = new HashMap<>();
                map.put(MatchRPCConstant.Key.area.name(),Long.parseLong(matchMeta.area.toString()));
                map.put(MatchRPCConstant.Key.accountId.name(),matchMeta.accountId);
                map.put(MatchRPCConstant.Key.userId.name(),matchMeta.userId);
                dto.setData(map);
                matchTwoRoomRpcClient.SURRENDER(dto);
            }else {
                //TODO 加入移除隊列
            }

        }
    }
    private class MatchMeta{
        public Long accountId;
        public Long userId;
        public Byte area;
        public String roomId;
        public boolean remove = false;
        public int lock = 0;

        public MatchMeta(Long accountId,Long userId, Byte area) {
            this.accountId = accountId;
            this.area = area;
        }
    }
}

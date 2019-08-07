package core.spring.service.match;

import core.dto.acct.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class MatchService {
    @Autowired
    private MatchTwoRoomService matchTwoRoomService;

    private HashSet<Long> userIdQueue = new HashSet<>();
    public boolean checkHasInQueue(Long userId){
        return userIdQueue.contains(userId);
    }
    public boolean joinTwoRoom(UserInfo userInfo) {
        //如果已經在隊列中，那麽不能加入，如果已經在隊列中需要
        if(checkHasInQueue(userInfo.getId())){
            return false;
        }
        return matchTwoRoomService.join(userInfo);

    }

    public boolean quitTwoRoom(UserInfo userInfo) {
        if(userIdQueue.contains(userInfo.getId())){
            userIdQueue.remove(userInfo.getId());
        }
        matchTwoRoomService.quit(userInfo);
        return true;

    }
}

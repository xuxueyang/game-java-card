package core.spring.service.room;

import core.core.RequestDTO;
import core.spring.world.WorldManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PvpTwoRoom implements Runnable,RoomInterface {
    public static final Logger log = LoggerFactory.getLogger(WorldManager.class);

    private String _roomId;
    public PvpTwoRoom(String roomId,Long oneUserId,Long twoUserId){
        log.debug("init:戰鬥房間"+ oneUserId + "  " + twoUserId);
        init();
    }
    private void init(){

    }
    @Override
    public void run() {

    }

    @Override
    public void receiveMessage(RequestDTO dto) {

    }
}

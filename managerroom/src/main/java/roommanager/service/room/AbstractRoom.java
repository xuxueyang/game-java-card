package roommanager.service.room;

import core.core.RequestDTO;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class AbstractRoom<T> implements Runnable{
    protected LinkedBlockingQueue<RequestDTO> blockingQueue = new LinkedBlockingQueue(20);
    protected RoomEventOverInterface<RoomEventOverInterface.DefaultOverDTO> _RoomEventOverInterface = null;
    protected RoomEventSendInterface<T> _RoomEventSendInterface = null;
    protected boolean canReceiveMessage = true;
    protected String _roomId;
    protected Byte area;

    public abstract List<T> sendStartMsg();

    public void receiveMessage(RequestDTO dto) {
        if(canReceiveMessage){
            try {
                blockingQueue.put(dto);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void sendMessage(List<T> msgList) {
        if(msgList!=null){
            this._RoomEventSendInterface.sendMsg(msgList);
        }
    }

    protected abstract void overTime(Long winnerUserId,Long failureUserId);

    @Override
    public void run() {

    }
}

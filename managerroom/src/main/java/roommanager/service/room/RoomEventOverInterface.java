package roommanager.service.room;

public interface RoomEventOverInterface<T> {
    //結束戰鬥
    void over(T t);
    public class DefaultOverDTO{
        public String roomId;
        public Long winnerUserId;
        public Long failureUserId;
        public Long battleTime;
    }
}

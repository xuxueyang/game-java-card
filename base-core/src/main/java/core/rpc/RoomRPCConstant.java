package core.rpc;

public interface RoomRPCConstant {
    String port = "20011";

    String SERVICE_NAME = "room";
    String MQ_NAME_PRODUCER = "room_producer";
    String MQ_NAME_CONSUMER = "room_consumer";

    String _prefix = "/netty.rpc/room";
    String CREATE_TWO_ROOM = _prefix + "/two/CREATE_TWO_ROOM";
    String SURRENDER = _prefix + "/two/SURRENDER";

    enum Key{
        area,
        accountId,
        userId,
        oneUser,
        twoUser,
        userIds
    }
}

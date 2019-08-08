package core.rpc;

public interface RoomRPCConstant {
    String port = "20011";

    String SERVICE_NAME = "room";
    String _prefix = "/rpc/room";
    String CREATE_TWO_ROOM = _prefix + "/two/CREATE_TWO_ROOM";
    String SURRENDER = _prefix + "/two/SURRENDER";

    enum Key{
        area,
        accountId,
        userId,
        oneUser,
        twoUser
    }
}

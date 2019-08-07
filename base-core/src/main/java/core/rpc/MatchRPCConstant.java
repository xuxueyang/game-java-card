package core.rpc;

public interface MatchRPCConstant {
    String port = "20002";

    String SERVICE_NAME = "gateway";
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

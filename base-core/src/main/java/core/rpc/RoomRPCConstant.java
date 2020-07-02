package core.rpc;

public interface RoomRPCConstant {
    String port = "20011";

    String SERVICE_NAME = "room";
    String MQ_NAME_PRODUCER = "room_producer";
    String MQ_NAME_CONSUMER = "room_consumer";

    String _prefix = "/api";
    String CREATE_TWO_ROOM = _prefix + "/two/CREATE_TWO_ROOM";
    String SURRENDER = _prefix + "/two/SURRENDER";

    String CREATE_ROOM = _prefix +"/CREATE_ROOM";

    enum Key{
//        area,
        areaL,
        accountId,
        userId,
        oneUser,
        twoUser,
        userIds,
        roomKey,
    }
    enum RoomKey{
        autochess("auto-chess");

        private String status;

        private RoomKey(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        // 此方法是关键
        public static RoomKey getByStatus(String status) {
            for (RoomKey aparameterStatus : values()) {
                if (aparameterStatus.getStatus().equals(status)) {
                    return aparameterStatus;
                }
            }
            return null;
        }
    }
}

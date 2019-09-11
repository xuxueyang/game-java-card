package core.protocol;

public  interface Protocol {
    public static final  String TOKEN = "TOKEN";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String USERINFO = "userinfo";

    //token超时时间
    public static final Long TOKEN_VALID_TIME = 30*30*60L;
    public static final Long Head_TIME = 60000L;//60s

    //token超时时间
    public static final Long REDIS_VAILD_TIME = TOKEN_VALID_TIME;

    final class AreaL {
        private static final Long TEXT = 1L;
        public static Long[] getAllArea(){
            return new Long[]{TEXT};
        }
        public static final String split = "-";
    }
    final class Area{
//        public static Byte TEXT = 0x1;
        public final static Byte Sys = 0x1;
        public final static Byte Netty = 0x2;
    }
    final  class Type{
        public static Byte SYS = 0x1;
        public static Byte LOGIN = 0x2;
        public static Byte CHAT = 0x3;
        public static Byte ITEM = 0x4;
        public static Byte ROOM = 0x5;
    }
    public final static class Status{
        public static int YES =1;
        public static int NOT =0;
    }
    interface HeadType
    {
        Byte  Login_Request = 1; //登陆请求
        Byte  Login_Response = 2; //登录响应
        Byte  Logout_Request =3 ;//退出请求
        Byte  Logout_Response =3 ;
        Byte  Keepalive_Request =3 ;//心跳请求ping;
        Byte  Keepalive_Response =3 ;
        Byte  Message_Request =3 ;//消息请求;
        Byte  Message_Response =3 ;//消息回执;
        Byte  Message_Notification =3 ;//通知消息
    }
    interface _Index {
        int ConstatnProtocolIndex = 100;
        int PvpTwoRoomIndex_CLIENT = 10000;
        int PvpTwoRoomIndex_SERVER = 12000;
    }
    interface ConstatnProtocol{
        int Head = _Index.ConstatnProtocolIndex + 1;
        int SUCCESS = _Index.ConstatnProtocolIndex + 2;

    }
    interface PvpTwoRoomProtocol{
        //玩家能做的事情：使用卡牌、棋子移動、棋子攻擊、棋子使用技能，結束回合、逃跑投降
        int CLINET_CARD_USE = _Index.PvpTwoRoomIndex_CLIENT + 1;
        int CLINET_ENVOY_MOVE= _Index.PvpTwoRoomIndex_CLIENT + 2;
        int CLINET_ENVOY_ATTACK= _Index.PvpTwoRoomIndex_CLIENT + 3;
        int CLINET_ENVOY_USE_SKILL= _Index.PvpTwoRoomIndex_CLIENT + 4;
        int CLINET_PLAYER_OVER= _Index.PvpTwoRoomIndex_CLIENT + 5;
        int CLINET_PLAYER_SURRENDER= _Index.PvpTwoRoomIndex_CLIENT + 6;


        //服務器能做的：棋子移動、棋子攻擊、棋子使用技能、棋子死亡、卡牌觸發、卡使用、玩家抽卡、玩家回合結束、增長水晶
        //初始化
        int SERVER_ERROR= _Index.PvpTwoRoomIndex_SERVER + 0;
        int SERVER_ROOM_INIT = _Index.PvpTwoRoomIndex_SERVER + 1;
        int SERVER_ENVOY_MOVE= _Index.PvpTwoRoomIndex_SERVER + 2;
        int SERVER_ENVOY_ATTACK = _Index.PvpTwoRoomIndex_SERVER + 3;
        int SERVER_ENVOY_USE_SKILL = _Index.PvpTwoRoomIndex_SERVER + 4;
        int SERVER_ENVOY_DEAD = _Index.PvpTwoRoomIndex_SERVER + 5;
        int SERVER_CARD_TYPE_YINMOU_EFFECT = _Index.PvpTwoRoomIndex_SERVER + 6;
        int SERVER_CARD_USE= _Index.PvpTwoRoomIndex_SERVER + 7;
        int SERVER_CARD_GET_SUCCESS= _Index.PvpTwoRoomIndex_SERVER + 8;
        int SERVER_PLAYER_OVER= _Index.PvpTwoRoomIndex_SERVER + 9;
        int SERVER_STAR_STAR_FORCE_INCR= _Index.PvpTwoRoomIndex_SERVER + 10;
        int SERVER_PLAYER_SURRENDER= _Index.PvpTwoRoomIndex_SERVER + 11;

        int SERVER_CARD_GET_DESTROY_CARD= _Index.PvpTwoRoomIndex_SERVER + 12;
        int SERVER_CARD_GET_NO_CARD= _Index.PvpTwoRoomIndex_SERVER + 13;

        int SERVER_PLAYER_START= _Index.PvpTwoRoomIndex_SERVER + 14;



    }
    /*登录信息*/
    // LoginRequest对应的HeadType为Login_Request
    // 消息名称去掉下划线，更加符合Java 的类名规范
//    class LoginRequest{
//        required String uid = 1;        // 用户唯一id
//        required string deviceId = 2;     // 设备ID
//        required string token = 3;       // 用户token
//        optional uint32 platform = 4;      //客户端平台 windows、mac、android、ios、web
//        optional string app_version = 5;    // APP版本号
//    }

}

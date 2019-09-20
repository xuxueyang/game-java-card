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
        public final static byte Sys = 0x1;
        public final static byte Netty = 0x2;
    }
    final  class Type{
        public static final byte SYS = 0x1;
        public static final byte LOGIN = 0x2;
        public static final byte CHAT = 0x3;
        public static final byte ITEM = 0x4;
        public static final byte ROOM = 0x5;
        public static final byte FILE = 0x6;

    }

    public final static class Status{
        public static int YES =1;
        public static int NOT =0;
    }
    interface HeadType
    {
        byte  Login_Request = 1; //登陆请求
        byte  Login_Response = 2; //登录响应
        byte  Logout_Request =3 ;//退出请求
        byte  Logout_Response =3 ;
        byte  Keepalive_Request =3 ;//心跳请求ping;
        byte  Keepalive_Response =3 ;
        byte  Message_Request =3 ;//消息请求;
        byte  Message_Response =3 ;//消息回执;
        byte  Message_Notification =3 ;//通知消息
    }
    interface _Index {
        int ConstatnProtocolIndex = 100;
        int PvpTwoRoomIndex_CLIENT = 10000;
        int PvpTwoRoomIndex_SERVER = 12000;

        int AutoChessRoomIndex_CLIENT = 14000;
        int AutoChessRoomIndex_SERVER = 16000;

        int ChatIndex = 20000;
    }
    interface ConstatnProtocol{
        int Head = _Index.ConstatnProtocolIndex + 1;
        int SUCCESS = _Index.ConstatnProtocolIndex + 2;

    }

    interface ChatIndex{
        int WORLD = _Index.ChatIndex + 1;
        int ONE = _Index.ChatIndex + 1;
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

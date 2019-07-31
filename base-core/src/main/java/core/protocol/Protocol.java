package core.protocol;

public  interface Protocol {
    public static final  String TOKEN = "TOKEN";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String USERINFO = "userinfo";

    //token超时时间
    public static final Long TOKEN_VALID_TIME = 30*30*60L;
    //token超时时间
    public static final Long REDIS_VAILD_TIME = TOKEN_VALID_TIME;

    public final static class Type{
        public static Byte SYS = 10;
        public static Byte LOGIN = 20;
        public static Byte CHAT = 30;
        public static Byte ITEM = 40;
    }
    public final static class Status{
        public static int YES =1;
        public static int NOT =0;
    }
    interface HeadType
    {
        int  Login_Request = 1; //登陆请求
        int  Login_Response = 2; //登录响应
        int  Logout_Request =3 ;//退出请求
        int  Logout_Response =3 ;
        int  Keepalive_Request =3 ;//心跳请求ping;
        int  Keepalive_Response =3 ;
        int  Message_Request =3 ;//消息请求;
        int  Message_Response =3 ;//消息回执;
        int  Message_Notification =3 ;//通知消息
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

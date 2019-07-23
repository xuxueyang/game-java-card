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
        public static Byte LOGIN = 0;
        public static Byte CHAT = 1;
        public static Byte ITEM = 2;
    }
    public final static class Status{
        public static int YES =1;
        public static int NOT =0;
    }
}

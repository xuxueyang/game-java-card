package core.protocol;

public class AutoChessRoomProtocol {
    private static final int client_index =  Protocol._Index.AutoChessRoomIndex_CLIENT;
    private static final int server_index =  Protocol._Index.AutoChessRoomIndex_SERVER;

    //奇数代表错误
    //客户端+2000代表正确返回

    //玩家能做的事情：使用卡牌、棋子移動、棋子攻擊、棋子使用技能，結束回合、逃跑投降
//    public static final int CLINET_CARD_USE = client_index + 1;
//    public static final int CLINET_ENVOY_MOVE= client_index + 2;
//    public static final int CLINET_ENVOY_ATTACK= client_index + 3;
//    public static final int CLINET_ENVOY_USE_SKILL= client_index + 4;
//    public static final int CLINET_PLAYER_OVER= client_index + 5;
//    public static final int CLINET_PLAYER_SURRENDER= client_index + 6;


    //服務器能做的：棋子移動、棋子攻擊、棋子使用技能、棋子死亡、卡牌觸發、卡使用、玩家抽卡、玩家回合結束、增長水晶
    //初始化
//    public static final int SERVER_ERROR= server_index + 0;
//    public static final int SERVER_ROOM_INIT = server_index + 1;
//    public static final int SERVER_ENVOY_MOVE= server_index + 2;
//    public static final int SERVER_ENVOY_ATTACK = server_index + 3;
//    public static final int SERVER_ENVOY_USE_SKILL = server_index + 4;
//    public static final int SERVER_ENVOY_DEAD = server_index + 5;
//    public static final int SERVER_CARD_TYPE_YINMOU_EFFECT = server_index + 6;
//    public static final int SERVER_CARD_USE= server_index + 7;
//    public static final int SERVER_CARD_GET_SUCCESS= server_index + 8;
//    public static final int SERVER_PLAYER_OVER= server_index + 9;
//    public static final int SERVER_STAR_STAR_FORCE_INCR= server_index + 10;
//    public static final int SERVER_PLAYER_SURRENDER= server_index + 11;
//
//    public static final int SERVER_CARD_GET_DESTROY_CARD= server_index + 12;
//    public static final int SERVER_CARD_GET_NO_CARD= server_index + 13;
//
    public static final int SERVER_PLAYER_SELLER_CHESS= server_index + 0;
    public static final int SERVER_PLAYER_SELLER_CHESS_ERRPR= server_index + 1;
    public static final int SERVER_PLAYER_REFRESH_SELF= server_index + 2;
    public static final int SERVER_PLAYER_REFRESH_SELF_ERRPR= server_index + 3;
    public static final int SERVER_PLAYER_REFRESH_AUTO= SERVER_PLAYER_REFRESH_SELF;


    public enum SkillType { //技能状态
        BEI_DONG(0,"被动"),
        DAN_TI(1,"单体"),
        WU_MU_BIAO(2,"无目标"),
        DIAN_MU_BIAO(3,"点目标"),
        ZI_JI(4,"自己"),
        JIN_SHEN_DAN_WEI(5,"近身单位"),
        YI_ZI_JI_WEI_DIAN_MU_BIAO(6,"以自己为中心的点目标"),
        SUI_JI_YOU_JUN(7,"随机友军"),
        SUI_JI_ZHOU_WEI_KONG_DI(8,"随机周围空地"),
        XUE_LIANG_ZUI_DI_DUI_YOU(9,"血量最低队友");

        SkillType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        private int code;

        private String desc;

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }

}
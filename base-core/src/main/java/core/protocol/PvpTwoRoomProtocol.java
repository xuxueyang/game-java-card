package core.protocol;

public class PvpTwoRoomProtocol{
    private static final int client_index =  Protocol._Index.PvpTwoRoomIndex_CLIENT;
    private static final int server_index =  Protocol._Index.PvpTwoRoomIndex_SERVER;


    //玩家能做的事情：使用卡牌、棋子移動、棋子攻擊、棋子使用技能，結束回合、逃跑投降
    public static final int CLINET_CARD_USE = client_index + 1;
    public static final int CLINET_ENVOY_MOVE= client_index + 2;
    public static final int CLINET_ENVOY_ATTACK= client_index + 3;
    public static final int CLINET_ENVOY_USE_SKILL= client_index + 4;
    public static final int CLINET_PLAYER_OVER= client_index + 5;
    public static final int CLINET_PLAYER_SURRENDER= client_index + 6;


    //服務器能做的：棋子移動、棋子攻擊、棋子使用技能、棋子死亡、卡牌觸發、卡使用、玩家抽卡、玩家回合結束、增長水晶
    //初始化
    public static final int SERVER_ERROR= server_index + 0;
    public static final int SERVER_ROOM_INIT = server_index + 1;
    public static final int SERVER_ENVOY_MOVE= server_index + 2;
    public static final int SERVER_ENVOY_ATTACK = server_index + 3;
    public static final int SERVER_ENVOY_USE_SKILL = server_index + 4;
    public static final int SERVER_ENVOY_DEAD = server_index + 5;
    public static final int SERVER_CARD_TYPE_YINMOU_EFFECT = server_index + 6;
    public static final int SERVER_CARD_USE= server_index + 7;
    public static final int SERVER_CARD_GET_SUCCESS= server_index + 8;
    public static final int SERVER_PLAYER_OVER= server_index + 9;
    public static final int SERVER_STAR_STAR_FORCE_INCR= server_index + 10;
    public static final int SERVER_PLAYER_SURRENDER= server_index + 11;

    public static final int SERVER_CARD_GET_DESTROY_CARD= server_index + 12;
    public static final int SERVER_CARD_GET_NO_CARD= server_index + 13;

    public static final int SERVER_PLAYER_START= server_index + 14;



}
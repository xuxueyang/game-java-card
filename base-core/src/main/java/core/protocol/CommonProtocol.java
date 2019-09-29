package core.protocol;

public class CommonProtocol {
    private static final int client_index =  Protocol._Index.CommonProtocolIndex_CLIENT;//40000
    private static final int server_index =  Protocol._Index.CommonProtocolIndex_SERVER;//50000
    private static final int error_index =  Protocol._Index.CommonProtocolIndex_ERROR;//60000
//————————————————————客户端
    public static final int CLINET_AUTH_WEBSOCKET = client_index + 1;
    public static final int CLINET_RELINK = client_index + 2;//想要重连
    public static final int CLIENT_MATCH_ADD = client_index + 3;//加入匹配
    public static final int CLIENT_MATCH_CANCEL = client_index + 4;//取消


    //————————————————————服务器
    public static final int SERVER_AUTH_WEBSOCKET_CHECK_SUCCESS = server_index + 1;
    public static final int SERVER_AUTH_WEBSOCKET_CHECK_ERROR = server_index + 2;
    public static final int SERVER_MATCH_ADD = server_index + 3;//加入匹配
    public static final int SERVER_MATCH_CANCEL = server_index + 4;//取消
    public static final int SERVER_MATCH_SUCCESS = server_index + 5;//取消
    public static final int SERVER_MATCH_ERROR = server_index + 6;//取消
    public static final int SERVER_ROOM_SUCCESS_CREATED = server_index + 7;//房間成功創建



//————————————————————ERROR
    public static final int ERROR_DATA_FORMAT = error_index + 1;

}

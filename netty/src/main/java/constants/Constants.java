package constants;

public class Constants {
    //协议版本号
    public static final short PROTOCOL_VERSION = 1;
    //头部的长度： 版本号 + 报文长度
    public static final short PROTOCOL_HEADLENGTH = 4;
    //长度的偏移
    public static final short LENGTH_OFFSET = 2;
    //长度的字节数
    public static final short LENGTH_BYTES_COUNT = 2;
}

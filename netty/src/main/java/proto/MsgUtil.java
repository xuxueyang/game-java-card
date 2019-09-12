package proto;

import proto.dto.RequestDTO;

public class MsgUtil {
    /**
     * 构建protobuf消息体
     */
    public static RequestDTO.RequestDTOProto buildMsg(String message) {
        RequestDTO.RequestDTOProto.Builder builder = RequestDTO.RequestDTOProto.newBuilder();
        builder.setMessage(message);
        return builder.build();
    }
}

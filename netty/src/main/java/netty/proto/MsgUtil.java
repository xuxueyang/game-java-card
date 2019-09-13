package netty.proto;

import com.alibaba.fastjson.JSON;
import netty.config.DefaultChannelInitializer;
import netty.proto.dto.RequestDTO;

public class MsgUtil {
    /**
     * 构建protobuf消息体
     */
    public static Object buildMsg(String message) {
        if(DefaultChannelInitializer.useProtobuf){
            RequestDTO.RequestDTOProto.Builder builder = RequestDTO.RequestDTOProto.newBuilder();
            builder.setMessage(message);
            return builder.build();
        }else{
            core.core.RequestDTO dto = new core.core.RequestDTO<>();
            dto.setData(message);
            return dto;
        }

    }
    public static Object sendObj(Object object) {
        if(DefaultChannelInitializer.useProtobuf){
            RequestDTO.RequestDTOProto.Builder builder = RequestDTO.RequestDTOProto.newBuilder();
            builder.setMessage(JSON.toJSONString(object));
            return builder.build();
        }else{
            return object;
        }

    }
    public static Object buildObj(Object fileDTO,byte type){
        if(DefaultChannelInitializer.useProtobuf) {
            RequestDTO.RequestDTOProto.Builder builder = RequestDTO.RequestDTOProto.newBuilder();
            core.core.RequestDTO requestDTO = new core.core.RequestDTO();
            requestDTO.setData(fileDTO);
            requestDTO.setType(type);
            builder.setMessage(JSON.toJSONString(requestDTO));
            return builder.build();
        }else{
            core.core.RequestDTO dto = new core.core.RequestDTO<>();
            dto.setData(fileDTO);
            return dto;
        }
    }
    public static Object parseObject(Object jsonObject,Class aClass){
        return JSON.parseObject(JSON.toJSONString(jsonObject), aClass);
    }

}

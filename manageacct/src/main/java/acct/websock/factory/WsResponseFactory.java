package acct.websock.factory;

import core.core.RequestDTO;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import core.protocol.Protocol;
import core.util.MD5Util;

public class WsResponseFactory {
    public static RequestDTO<ReturnResultDTO> getClose(String token){
        if(token==null)
            return null;
        return getToOneDefaultTemplate(token,ReturnCode.ERROR_QUERY,"参数错误");
    }

    public static RequestDTO<ReturnResultDTO> getOnOpen(String token) {
        if(token==null)
            return null;
        return getToOneDefaultTemplate(token,ReturnCode.DEFAULT_SUCCESS,"连接成功");
    }
    public static RequestDTO<ReturnResultDTO> getToOneDefaultTemplate(String token,String code,Object data){
        if(token==null)
            return null;
        RequestDTO<ReturnResultDTO> requestDTO = new RequestDTO<ReturnResultDTO>();
        long timestamp = System.currentTimeMillis();
        requestDTO.setTimestamp(timestamp);
        ReturnResultDTO dataDTO  = new ReturnResultDTO<>(code,data);
        requestDTO.setData(dataDTO);
        requestDTO.setMd5(MD5Util.MD5(token + timestamp));
        requestDTO.setType(Protocol.Type.SYS);
        return requestDTO;
    }
    public static RequestDTO<ReturnResultDTO> getToAllDefaultTemplate(String code,Object data){
        RequestDTO<ReturnResultDTO> requestDTO = new RequestDTO<ReturnResultDTO>();
        long timestamp = System.currentTimeMillis();
        requestDTO.setTimestamp(timestamp);
        ReturnResultDTO dataDTO  = new ReturnResultDTO<>(code,data);
        requestDTO.setData(dataDTO);
        requestDTO.setType(Protocol.Type.SYS);
        return requestDTO;
    }
}

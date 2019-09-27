package factory;

import core.core.RequestDTO;
import core.protocol.CommonProtocol;
import core.protocol.Protocol;

public final class ResDTOFactory
{
    public static RequestDTO getSuccessCheckedConnected(){
        RequestDTO dto = new RequestDTO();
        dto.setArea(Protocol.Area.Netty);
        dto.setType(Protocol.Type.LOGIN);
        dto.setProtocol(CommonProtocol.SERVER_AUTH_WEBSOCKET_CHECK_SUCCESS);
        return dto;
    }
    public static RequestDTO getErrorCheckedConnected(){
        RequestDTO dto = new RequestDTO();
        dto.setArea(Protocol.Area.Netty);
        dto.setType(Protocol.Type.LOGIN);
        dto.setProtocol(CommonProtocol.SERVER_AUTH_WEBSOCKET_CHECK_ERROR);
        return dto;
    }
    public static RequestDTO getDTO(byte type,int protocol){
        RequestDTO dto = new RequestDTO();
        dto.setArea(Protocol.Area.Netty);
        dto.setType(type);
        dto.setProtocol(protocol);
        return dto;
    }
}

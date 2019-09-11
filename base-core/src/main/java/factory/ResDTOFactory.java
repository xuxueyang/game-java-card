package factory;

import core.core.RequestDTO;
import core.protocol.Protocol;

public final class ResDTOFactory
{
    public static RequestDTO getSuccessConnected(){
        RequestDTO dto = new RequestDTO();
        dto.setArea(Protocol.Area.Netty);
        dto.setType(Protocol.Type.LOGIN);
        dto.setProtocol(Protocol.ConstatnProtocol.SUCCESS);
        return dto;
    }
}

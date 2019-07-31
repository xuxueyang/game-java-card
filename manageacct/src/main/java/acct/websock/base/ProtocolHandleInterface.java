package acct.websock.base;

import core.core.RequestDTO;

import java.util.List;

public interface ProtocolHandleInterface {
//    void regeistrySelf();
//    DataProtocol onOpen(Session session,DataProtocol protocol);
    List<RequestDTO> onMessaeg(String sessionId, String message, RequestDTO protocol);
    String getType();
}

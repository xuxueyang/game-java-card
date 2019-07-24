package acct.web.rest.websock.base;

import java.util.List;

public interface ProtocolHandleInterface {
//    void regeistrySelf();
//    DataProtocol onOpen(Session session,DataProtocol protocol);
    List<DataProtocol> onMessaeg(String sessionId, String message, DataProtocol protocol);
    String getType();
}

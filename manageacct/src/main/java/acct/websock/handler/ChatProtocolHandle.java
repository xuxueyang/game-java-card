package acct.websock.handler;

import acct.websock.base.Protocol;
import acct.websock.base.ProtocolHandleInterface;
import org.apache.commons.lang3.StringUtils;


import java.util.ArrayList;
import java.util.List;



// 全部单例
public final class ChatProtocolHandle
//        implements ProtocolHandleInterface
{

//    @Override
//    public List<DataProtocol> onMessaeg(String sessionId, String message, DataProtocol protocol) {
//        switch (protocol.getProtocol()){
//            case Protocol.sendChatMessage:
//                return OnSendChatMessage(sessionId,message,protocol);
//        }
//        return null;
//    }
//
//    private List<DataProtocol> OnSendChatMessage(String sessionId,String message, DataProtocol protocol) {
//        //从userId获取到会话ID
//        List<DataProtocol> list = new ArrayList<>();
//        if(StringUtils.isBlank(protocol.getToUserId())){
//            // 构造2个消息
//            DataProtocol myProtocol = getDataByLast(protocol);
//            myProtocol.setMessage(protocol.getMessage());
//            myProtocol.setMessageType(Protocol.messageType.One.name());
//            myProtocol.setToUserId(protocol.getUserId());
//            DataProtocol toProtocol = getDataByLast(protocol);
//            toProtocol.setMessage(protocol.getMessage());
//            toProtocol.setMessageType(Protocol.messageType.One.name());
//            toProtocol.setToUserId(protocol.getToUserId());
//            list.add(myProtocol);
//            list.add(toProtocol);
//        }else {
//            //广播全部
//            DataProtocol myProtocol = getDataByLast(protocol);
//            myProtocol.setMessageType(Protocol.messageType.All.name());
//            myProtocol.setMessage(protocol.getMessage());
////            myProtocol.set
//            list.add(myProtocol);
//        }
//
//        return list;
//    }
//
//    @Override
//    public String getType() {
//        return Protocol.FirstProtocol_CHAT;
//    }
}

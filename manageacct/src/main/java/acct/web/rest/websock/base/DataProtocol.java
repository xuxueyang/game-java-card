package acct.web.rest.websock.base;


import core.util.UUIDGenerator;

import java.io.Serializable;

public class DataProtocol implements Serializable {


    private String message;
    private String token;
    private String sessionId;
//    private String fromSessionId;
    private String messageType;
    private String userId;
    private String fromUserId;
    private String toUserId;
    private String protocol;
//    private String nextDataProtocolStr;
    private String id;
    private String prvId;


    /**
     * 根据上一个消息构造下一个(最基本的信息)
     * @param current
     * @return
     */
    public static DataProtocol getDataByLast(DataProtocol current){
        DataProtocol protocol = new DataProtocol();
        protocol.setId(UUIDGenerator.getUUID());
        protocol.setPrvId(current.getPrvId());
        protocol.setFromUserId(current.getUserId());
        return protocol;
    }
    public static DataProtocol getErrorDataProtocol(String msg,DataProtocol current){
        DataProtocol protocol = getDataByLast(current);
        protocol.setId(UUIDGenerator.getUUID());
        protocol.setMessage(msg);
        protocol.setProtocol(Protocol.ERROR);
        protocol.setSessionId(current.getSessionId());
        protocol.setMessageType(Protocol.messageType.One.name());
        protocol.setToUserId(current.getUserId());
        return protocol;
    }
    public static DataProtocol getSuccessDataProtocol(String msg,DataProtocol current){
        DataProtocol protocol = getDataByLast(current);
        protocol.setId(UUIDGenerator.getUUID());
        protocol.setMessage(msg);
        protocol.setProtocol(Protocol.SUCCESS);
        protocol.setSessionId(current.getSessionId());
        protocol.setToUserId(current.getUserId());
        protocol.setMessageType(Protocol.messageType.One.name());
        return protocol;
    }
    // 一种check
    public static DataProtocol getCheckData(String msg,String protocolCode){
        DataProtocol protocol = new DataProtocol();
        protocol.setId(UUIDGenerator.getUUID());
        protocol.setProtocol(protocolCode);
        protocol.setMessage("");
        protocol.setMessageType(Protocol.messageType.One.name());

        return protocol;
    }
    private DataProtocol(){

    }
    //对于传入的数据
    public DataProtocol(String message, String token, String fromUserId, String toUserId, String protocol, String id, String prvId) {
        this.message = message;
        this.token = token;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.protocol = protocol;
        this.id = id;
        this.prvId = prvId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

//    public String getNextDataProtocolStr() {
//        return nextDataProtocolStr;
//    }
//
//    public void setNextDataProtocolStr(String nextDataProtocolStr) {
//        this.nextDataProtocolStr = nextDataProtocolStr;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrvId() {
        return prvId;
    }

    public void setPrvId(String prvId) {
        this.prvId = prvId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

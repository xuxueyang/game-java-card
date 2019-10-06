package netty.handler.inter;

import javax.websocket.Session;

public interface WebServerHandlerAdapter {
    public void handlerAdded(Session session) throws Exception;
    public void channelActive(Session session) throws Exception;
    public void handlerRemoved(Session session) throws Exception;
//    public void handlerRemoved(Session session) throws Exception;
    public void channelRead(Session session, Object msg) throws Exception;
    public void exceptionCaught(Session session, Throwable cause) throws Exception;
}

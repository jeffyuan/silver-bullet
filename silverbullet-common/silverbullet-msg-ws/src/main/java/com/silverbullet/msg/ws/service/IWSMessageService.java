package com.silverbullet.msg.ws.service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jeffyuan on 2018/11/21.
 */
public interface IWSMessageService {

    /**
     * 连接msg
     */
    public void connectMsg(Session session);

    /**
     * 断开msg
     * @param session
     */
    public void unConnectMsg(Session session);

    /**
     * 返回连接的session个数
     * @return
     */
    public int getSessionSize();
    /**
     * 发送消息
     * @param session
     * @param message
     */
    public void sendMessage(Session session, String message);

    /**
     * 广播消息
     * @param message
     * @throws IOException
     */
    public void broadCastInfo(String message) throws IOException;

    /**
     * 按照sessionid发布消息
     * @param sessionId
     * @param message
     * @throws IOException
     */
    public void sendMessage(String sessionId, String message) throws IOException;
}

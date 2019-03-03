package com.silverbullet.msg.ws.service.impl;

import com.silverbullet.msg.ws.service.IWSMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jeffyuan on 2018/11/21.
 */
@Service
public class WSMessageService implements IWSMessageService {

    private static final Logger logger = LoggerFactory.getLogger(WSMessageService.class);

    // 记录当前session数量
    private static final AtomicInteger onlineCount = new AtomicInteger(0);
    // 记录当前session
    private static CopyOnWriteArraySet<Session> sessionSet = new CopyOnWriteArraySet<Session>();

    /**
     * 连接msg
     *
     * @param session
     */
    @Override
    public void connectMsg(Session session) {
        sessionSet.add(session);
        int cnt = onlineCount.incrementAndGet(); // 在线数加1
    }

    /**
     * 断开msg
     *
     * @param session
     */
    @Override
    public void unConnectMsg(Session session) {
        sessionSet.remove(session);
        int cnt = onlineCount.decrementAndGet();
    }

    /**
     * 返回连接的session个数
     *
     * @return
     */
    @Override
    public int getSessionSize() {
        return onlineCount.get();
    }


    /**
     * 发送消息
     *
     * @param session
     * @param message
     */
    @Override
    public void sendMessage(Session session, String message) {

        try {
            session.getBasicRemote().sendText(String.format("%s (From Server，Session ID=%s)", message, session.getId()));
        } catch (IOException e) {
            logger.error("发送消息出错：{}", e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * 广播消息
     *
     * @param message
     * @throws IOException
     */
    @Override
    public void broadCastInfo(String message) throws IOException {
        for (Session session : sessionSet) {
            if(session.isOpen()){
                sendMessage(session, message);
            }
        }
    }

    /**
     * 按照sessionid发布消息
     *
     * @param sessionId
     * @param message
     * @throws IOException
     */
    @Override
    public void sendMessage(String sessionId, String message) throws IOException {
        Session session = null;
        for (Session s : sessionSet) {
            if(s.getId().equals(sessionId)){
                session = s;
                break;
            }
        }

        if(session!=null){
            sendMessage(session, message);
        } else{

            logger.warn("没有找到你指定ID的会话：{}",sessionId);
        }
    }
}

package com.silverbullet.msg.ws.server;

import com.silverbullet.msg.ws.service.IWSMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.websocket.*;

import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * websocket 服务
 * Created by jeffyuan on 2018/11/21.
 */
@ServerEndpoint(value = "/ws/wsurl")
@Component
public class WebSocketServer {
    private static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    @Autowired
    private IWSMessageService iwsMessageService;

    private static WebSocketServer webSocketServer;

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        webSocketServer = this;
        webSocketServer.iwsMessageService = this.iwsMessageService;
    }

    /**
     * 连接成功调用
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        webSocketServer.iwsMessageService.connectMsg(session);
        logger.info("有连接加入，当前连接数为：{}", webSocketServer.iwsMessageService.getSessionSize());

        webSocketServer.iwsMessageService.sendMessage(session, "连接成功");
    }

    /**
     * 关闭成功调用
     * @param session
     */
    @OnClose
    public void onClose(Session session) {

        webSocketServer.iwsMessageService.unConnectMsg(session);
        logger.info("有连接关闭，当前连接数为：{}", webSocketServer.iwsMessageService.getSessionSize());
    }

    /**
     * 收到客户端的消息内容
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("来自客户端的消息：{}", message);

        webSocketServer.iwsMessageService.sendMessage(session, "收到你的消息，消息内容：" + message);
    }

    /**
     * 消息异常
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生异常错误：{}，Session ID： {}",error.getMessage(),session.getId());
        error.printStackTrace();
    }
}

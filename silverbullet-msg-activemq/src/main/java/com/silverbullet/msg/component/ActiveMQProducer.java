package com.silverbullet.msg.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.silverbullet.msg.service.IProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.ObjectMessage;

/**
 * ActiveMQ 消息生产者
 * Created by jeffyuan on 2018/3/20.
 */
@Component
public class ActiveMQProducer implements IProducerService{

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * 发送消息
     * @param destination 目标
     * @param message 文本消息
     */
    @Override
    public void sendMessage(Destination destination, String message) {
        jmsMessagingTemplate.convertAndSend(destination, message);
    }

    /**
     * 发送序列化对象
     * @param destination 目标
     * @param objectMessage 对象
     */
    @Override
    public void sendMessage(Destination destination, ObjectMessage objectMessage) {
        jmsMessagingTemplate.convertAndSend(destination, objectMessage);
    }

}

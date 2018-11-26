package com.silverbullet.msg.component;


import com.silverbullet.msg.service.IProducerService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.ObjectMessage;

/**
 * activemq消息消费者
 * Created by jeffyuan on 2018/3/20.
 */
@Component
public class ActiveMQConsumer {

    @JmsListener(destination = "task.queue", containerFactory = "jmsListenerContainerQueue")
    public void receiveQueue(String text) {
        System.out.println("Consumer收到的报文为:"+text);
    }

    @JmsListener(destination = "sysmsg.topic", selector="msg='127.0.0.1' or msg='all'", containerFactory = "jmsListenerContainerTopic")
    public void receiveTopic(ObjectMessage text) {
        System.out.println("Consumer收到的报文为:"+text);
    }
}

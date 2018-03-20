package com.silverbullet.msg.demo;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by jeffyuan on 2018/3/20.
 */
public class ActiveMQUserListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
        try {
            System.out.println("收到的消息："+((TextMessage)message).getText());
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


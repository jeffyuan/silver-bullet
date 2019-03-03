package com.silverbullet.msg.demo;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * ActiveMQ消息消费者
 * Created by jeffyuan on 2018/3/20.
 */
public class ActiveMQConsumer {

    private static final String USERNAME= ActiveMQConnection.DEFAULT_USER; // 默认的连接用户名
    private static final String PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD; // 默认的连接密码
    private static final String BROKEURL=ActiveMQConnection.DEFAULT_BROKER_URL; // 默认的连接地址

    public static void main(String[] args) {
        ConnectionFactory connectionFactory; // 连接工厂
        Connection connection = null; // 连接
        Session session; // 会话 接受或者发送消息的线程
        Destination destination; // 消息的目的地
        MessageConsumer messageConsumer; // 消息的消费者

        // 实例化连接工厂
        connectionFactory=new ActiveMQConnectionFactory(ActiveMQConsumer.USERNAME, ActiveMQConsumer.PASSWORD, ActiveMQConsumer.BROKEURL);

        try {
            connection=connectionFactory.createConnection();  // 通过连接工厂获取连接
            connection.start(); // 启动连接
            session=connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE); // 创建Session
            destination=session.createQueue("TASKS-localhost");  // 创建连接的消息队列
            messageConsumer=session.createConsumer(destination); // 创建消息消费者
            //采用监听的方式 ，注册监听，自动的去监听里触发消息
            messageConsumer.setMessageListener(new ActiveMQUserListener()); // 注册消息监听
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

package com.silverbullet.msg.service;

import javax.jms.Destination;
import javax.jms.ObjectMessage;

/**
 * Created by jeffyuan on 2018/3/20.
 */
public interface IProducerService {
    public void sendMessage(Destination destination, String message);
    public void sendMessage(Destination destination, ObjectMessage objectMessage) ;
}

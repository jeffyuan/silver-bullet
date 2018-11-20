package com.silverbullet.msg.controller;

//import com.google.common.eventbus.Subscribe;
import com.silverbullet.msg.service.IProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jms.Queue;
import javax.jms.Topic;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息controller
 * Created by jeffyuan on 2018/3/20.
 */
@RequestMapping(value = "/sysmessage")
@Controller
public class SysMessageController {

    @Autowired
    private Queue queue;
    @Autowired
    private Topic topic;

    @Autowired
    private IProducerService iProducerService;

    @GetMapping("/queue/{msg}")
    public void sendQueue(@PathVariable("msg") String msg){
        iProducerService.sendMessage(this.queue, msg);
    }

    @GetMapping("/topic/{msg}")
    public void sendTopic(@PathVariable("msg")String msg){
        iProducerService.sendMessage(this.topic, msg);
    }
}

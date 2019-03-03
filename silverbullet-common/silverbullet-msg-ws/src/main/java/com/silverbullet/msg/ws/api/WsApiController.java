package com.silverbullet.msg.ws.api;

import com.silverbullet.msg.ws.service.IWSMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * websocket的controller类
 * Created by jeffyuan on 2018/11/21.
 */
@RestController
@RequestMapping(value = "/api/ws/msg")
public class WsApiController {

    @Autowired
    private IWSMessageService iwsMessageService;

    /**
     * 广播
     * @param message
     * @return
     */
    @RequestMapping(value = "/brocast", method = RequestMethod.GET)
    public String sendAllMessage(@RequestParam(required=true) String message) {
        try {
            iwsMessageService.broadCastInfo(message);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "ok";
    }

    /**
     * 给一个端发消息
     * @param message
     * @param id
     * @return
     */
    @RequestMapping(value = "/sendone", method = RequestMethod.GET)
    public String sendOneMessage(@RequestParam(required=true) String message, @RequestParam(required=true) String id){
        try {
            iwsMessageService.sendMessage(id,message);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "ok";
    }
}

package com.silverbullet.msg.ws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jeffyuan on 2018/11/26.
 */
@Controller
@RequestMapping(value = "/msg/ws")
public class WsMsgController {

    @RequestMapping(value = "/index")
    public String index(Model model) {

        return "/msg/wsindex";
    }
}


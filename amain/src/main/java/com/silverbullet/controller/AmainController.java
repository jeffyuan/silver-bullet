package com.silverbullet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by jeffyuan on 2018/2/11.
 */
@Controller
public class AmainController {
    @GetMapping(value = "/")
    public String index(Model model) {
        //ModelAndView modelAndView = new ModelAndView("index.html");
        //return modelAndView;

        return "index";
    }
}

package com.silverbullet.controller;

import com.silverbullet.auth.domain.SysAuthUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jeffyuan on 2018/2/11.
 */
@Controller
public class AmainController {
    @RequiresRoles("SYSSSO")
    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(Model model){
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("username", userName);
        return "index";
    }

    @RequestMapping(value="",method= RequestMethod.GET)
    public String defaultIndex(Model model){
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("username", userName);
        return "index";
    }
}

package com.silverbullet.controller;

import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.service.UserActionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jeffyuan on 2018/2/11.
 */
@Controller
public class AmainController {

    private static final Logger logger = LoggerFactory.getLogger(AmainController.class);

    @Autowired
    protected UserActionService userActionService;

    //@RequiresRoles("SYSSSO")
    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(Model model){
        //logger.info("[APP]/index");

        SysAuthUser userInfo = (SysAuthUser) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("username", userInfo.getUsername());

        return "index";
    }

    @RequestMapping(value="",method= RequestMethod.GET)
    public String defaultIndex(Model model){
        //logger.info("[APP]/index");
        return "index";
    }

    @RequestMapping(value = "/getmenus.do", method = RequestMethod.POST)
    public String getMenus(Model model) {

        SysAuthUser userInfo = (SysAuthUser) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("menus", userActionService.getUserLeftMenu(userInfo.getUsername()));
        model.addAttribute("username", userInfo.getName());

        return "/fragments/bk-leftmenu-tmp";
    }
}

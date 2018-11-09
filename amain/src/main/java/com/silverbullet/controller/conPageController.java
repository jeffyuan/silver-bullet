package com.silverbullet.controller;


import com.silverbullet.auth.domain.SysIcon;
import com.silverbullet.auth.service.ISysIcon;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/page")
public class conPageController {

    @Autowired
    ISysIcon sysIconService;

    @RequestMapping(value = "/icon.html", method = RequestMethod.POST)
    public String icon(Model model){

        BaseDataResult<SysIcon> sysIcon = sysIconService.list();
        model.addAttribute("results", sysIcon);
        return "/conPage/icon";
    }
}

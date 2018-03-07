package com.silverbullet.auth.controller;

import com.silverbullet.auth.service.ISysAuthUserService;
import com.silverbullet.utils.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by jeffyuan on 2018/2/11.
 */
@Controller
@RequestMapping(value = "/auth/sysauthuser")
public class SysAuthUserController {

    @Autowired
    private ISysAuthUserService sysAuthUserService;

    @RequestMapping(value = "/list/{curpage}.html", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("curpage") String curpage){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/SysAuthUser/list");

        DataResult results = sysAuthUserService.list(nCurPage, 5);

        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }
}

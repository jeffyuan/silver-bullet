package com.silverbullet.auth.controller;

import com.github.pagehelper.PageHelper;
import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.auth.service.ISysUserService;
import com.silverbullet.utils.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Created by jeffyuan on 2018/2/11.
 */
@Controller
@RequestMapping(value = "/auth")
public class SysAuthController {

    @Autowired
    private ISysUserService sysUserService;

    @GetMapping(value = "/list/{curpage}.html")
    public ModelAndView index(@PathVariable("curpage") String curpage){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/sys-auth/list");

        DataResult listUser = sysUserService.list(nCurPage, 5);
        listUser.runDictionary("state", "0:禁用,1:可用,2:删除");
        listUser.runDictionary("type", "1:系统用户,2:其他,3:超级用户");
        listUser.runformatTime("logintime", "yyyy-MM-dd HH:mm:ss");


        modelAndView.addObject("users", listUser);
        //modelAndView.addObject("totalNum", sysUserService.countNum());
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }
}

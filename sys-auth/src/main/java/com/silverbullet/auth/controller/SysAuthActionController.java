package com.silverbullet.auth.controller;

import com.silverbullet.auth.domain.SysAuthAction;
import com.silverbullet.auth.service.ISysAuthActionService;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能权限 控制器
 * Created by jeffyuan on 2018/2/11.
 */
@Controller
@RequestMapping(value = "/auth/sysauthaction")
public class    SysAuthActionController {

    @Autowired
    private ISysAuthActionService sysAuthActionService;

    @RequestMapping(value = "list/pub.html", method = RequestMethod.GET)
    public ModelAndView index(){
        int nCurPage = 1;

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/SysAuthAction/list");

        BaseDataResult<SysAuthAction> results = sysAuthActionService.list(nCurPage, 5 );

        modelAndView.addObject("list","list");
        modelAndView.addObject("method", "AuthAction.loadDataCommon");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }

    @RequestMapping(value = "/list.html", method = RequestMethod.POST)
    public ModelAndView listData(String curpage){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/SysAuthAction/listContent");

        BaseDataResult<SysAuthAction> results = sysAuthActionService.list(nCurPage, 5);

        modelAndView.addObject("list","list");
        modelAndView.addObject("method", "AuthAction.loadDataCommon");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }

    @RequestMapping(value = "/list.do", method = RequestMethod.POST)
    @ResponseBody
    public Map list(){
        HashMap map = new HashMap();
        BaseDataResult<SysAuthAction> results = sysAuthActionService.list();
        map.put("list",results);
        return map;
    }

    @RequestMapping(value = "/add.html", method = RequestMethod.POST)
    public String add(Model model) {
        model.addAttribute("obj", new SysAuthAction());
        return "/SysAuthAction/model";
    }

    @RequestMapping(value = "/edit.html", method = RequestMethod.POST)
    public String edit(Model model, String id) {
        SysAuthAction sysAuthUser = sysAuthActionService.getOneById(id);
        model.addAttribute("obj", sysAuthUser);
        return "/SysAuthAction/model";
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (ids == null || ids.isEmpty()) {
            mapRet.put("result", false);
            return mapRet;
        }
        boolean bRet = sysAuthActionService.delete(ids);
        mapRet.put("result", bRet);
        return mapRet;
    }

    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Validated({AddValidate.class}) SysAuthAction sysAuthAction,
                                    BindingResult result) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (result.hasErrors()) {
            mapRet.put("result", false);
            mapRet.put("errors", result.getFieldErrors());
            return mapRet;
        }

        boolean bTrue = false;
        String message = "";
        if (sysAuthAction.getId().isEmpty()) {
            bTrue = sysAuthActionService.Insert(sysAuthAction);
            message = bTrue ? "添加成功！" : "添加失败！";
        } else {
            bTrue = sysAuthActionService.Update(sysAuthAction);
            message = bTrue ? "修改成功！" : "修改失败！";
        }

        mapRet.put("result", bTrue);
        mapRet.put("message", message);

        return mapRet;
    }
}

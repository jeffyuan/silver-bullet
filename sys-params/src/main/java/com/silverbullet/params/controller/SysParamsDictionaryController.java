package com.silverbullet.params.controller;

import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.params.domain.SysParamsDictionary;
import com.silverbullet.params.service.ISysParamsDictionaryService;
import com.silverbullet.utils.BaseDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典类 控制器
 * Created by jeffyuan on 2018/2/11.
 */
@Controller
@RequestMapping(value = "/params/sysparamsdictionary")
public class SysParamsDictionaryController {

    @Autowired
    private ISysParamsDictionaryService sysParamsDictionaryService;

    @RequestMapping(value = "/list/{curpage}.html", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("curpage") String curpage){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/SysParamsDictionary/list");

        BaseDataResult<SysParamsDictionary> results = sysParamsDictionaryService.list(nCurPage, 5);

        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }

    @RequestMapping(value = "/add.html", method = RequestMethod.POST)
    public String add(Model model) {
        return "/SysParamsDictionary/model";
    }

    @RequestMapping(value = "/edit.html", method = RequestMethod.POST)
    public String edit(Model model, String id) {
        SysParamsDictionary sysParamsDictionary = sysParamsDictionaryService.getOneById(id);
        model.addAttribute("obj", sysParamsDictionary);

        return "/SysParamsDictionary/model";
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (ids == null || ids.isEmpty()) {
            mapRet.put("result", false);
            return mapRet;
        }

        boolean bRet = sysParamsDictionaryService.delete(ids);

        mapRet.put("result", bRet);
        return mapRet;
    }

    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Validated({AddValidate.class}) SysParamsDictionary sysParamsDictionary,
                                    BindingResult result) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (result.hasErrors()) {
            mapRet.put("result", false);
            mapRet.put("errors", result.getFieldErrors());
            return mapRet;
        }

        boolean bTrue = false;
        String message = "";
        if (sysParamsDictionary.getId().isEmpty()) {
            bTrue = sysParamsDictionaryService.Insert(sysParamsDictionary);
            message = bTrue ? "添加成功！" : "添加失败！";
        } else {
            bTrue = sysParamsDictionaryService.Update(sysParamsDictionary);
            message = bTrue ? "修改成功！" : "修改失败！";
        }

        mapRet.put("result", bTrue);
        mapRet.put("message", message);

        return mapRet;
    }
}

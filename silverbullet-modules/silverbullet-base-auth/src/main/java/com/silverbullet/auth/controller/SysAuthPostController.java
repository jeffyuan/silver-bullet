package com.silverbullet.auth.controller;

import com.silverbullet.auth.domain.SysAuthOrganization;
import com.silverbullet.auth.domain.SysAuthPost;
import com.silverbullet.auth.domain.SysAuthPostAction;
import com.silverbullet.auth.service.ISysAuthOrganizationService;
import com.silverbullet.auth.service.ISysAuthPostService;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.utils.BaseDataResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构管理角色 控制器
 * Created by jeffyuan on 2018/2/11.
 */
@Controller
@RequestMapping(value = "/auth/sysauthpost")
public class SysAuthPostController {

    @Autowired
    private ISysAuthPostService sysAuthPostService;

    @Autowired
    private ISysAuthOrganizationService sysAuthOrganizationService;

    @RequestMapping(value = "/list/{curpage}.html", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("curpage") String curpage){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/SysAuthPost/list");

        BaseDataResult<SysAuthPost> results = sysAuthPostService.list(nCurPage, 5);

        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }

    @RequestMapping(value = "/dictitem/list/{organizationId}.html", method = RequestMethod.POST)
    public ModelAndView loadDictItem(@PathVariable("organizationId") String organizationId, String curpage, String Id) {
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/SysAuthPost/list");

        BaseDataResult<SysAuthPost> results = sysAuthPostService.getPostByOrgId(organizationId, nCurPage, 5);

        modelAndView.addObject("OrganizationId", organizationId);
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);
        modelAndView.addObject("Id", Id);

        return modelAndView;
    }

    @RequestMapping(value = "/list.html", method = RequestMethod.POST)
    public ModelAndView list(String curpage, String orgId){

        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/SysAuthPost/list");

        BaseDataResult<SysAuthPost> results = sysAuthPostService.getPostByOrgId(orgId, nCurPage, 5);

        modelAndView.addObject("list","list");
        modelAndView.addObject("OrganizationId", orgId);
        modelAndView.addObject("method", "AuthAction.loadData");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);


        return modelAndView;
    }


    @RequestMapping(value = "/add.html", method = RequestMethod.POST)
    public String add(Model model, String orgId) {
        SysAuthOrganization sysAuthOrganization = sysAuthOrganizationService.getOneById(orgId);

        model.addAttribute("obj", new SysAuthPost());
        model.addAttribute("orgObj", sysAuthOrganization);
        return "/SysAuthPost/model";
    }


    @RequestMapping(value = "/edit.html", method = RequestMethod.POST)
    public String edit(Model model, String id, String orgId) {
        SysAuthPost sysAuthUser = sysAuthPostService.getOneById(id);
        SysAuthOrganization sysAuthOrganization = sysAuthOrganizationService.getOneById(orgId);
        model.addAttribute("obj", sysAuthUser);
        model.addAttribute("orgObj", sysAuthOrganization);

        return "/SysAuthPost/model";
    }


    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Validated({AddValidate.class}) SysAuthPost sysAuthUser,
                                    BindingResult result) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (result.hasErrors()) {
            mapRet.put("result", false);
            mapRet.put("errors", result.getFieldErrors());
            return mapRet;
        }

        boolean bTrue = false;
        String message = "";
        if (sysAuthUser.getId().isEmpty()) {
            bTrue = sysAuthPostService.Insert(sysAuthUser);
            message = bTrue ? "添加成功！" : "添加失败！";
        } else {
            bTrue = sysAuthPostService.Update(sysAuthUser);
            message = bTrue ? "修改成功！" : "修改失败！";
        }

        mapRet.put("result", bTrue);
        mapRet.put("message", message);

        return mapRet;
    }

    @RequestMapping(value = "/deletes.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (ids == null || ids.isEmpty()) {
            mapRet.put("result", false);
            return mapRet;
        }
        boolean bRet = sysAuthPostService.delete(ids);
        mapRet.put("result", bRet);
        return mapRet;
    }

    @RequestMapping(value = "/setPostAction.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setPermission(String postIds, @RequestParam(value = "actionId[]") String[] actionId){

        Map<String, Object>mapRet = new HashMap<String, Object>();

        boolean obj = sysAuthPostService.Handle(postIds, actionId);

        mapRet.put("result", obj);

        return mapRet;
    }


    @RequestMapping(value = "/findCheck.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, List> findCheck(String postId) {

        Map<String, List>mapRet = new HashMap<>();

        List<SysAuthPostAction> postActionContent = sysAuthPostService.findCheck(postId);
        mapRet.put("result", postActionContent);

        return mapRet;
    }

    @RequestMapping(value = "modelTree.html", method = RequestMethod.POST)
    public String modelTree(String uid, Model model){
        model.addAttribute("uid", uid);
        return "/SysAuthPost/modelTree";
    }


}

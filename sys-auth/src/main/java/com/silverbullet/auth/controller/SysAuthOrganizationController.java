package com.silverbullet.auth.controller;

import com.silverbullet.auth.domain.SysAuthAction;
import com.silverbullet.auth.domain.SysAuthOrganization;
import com.silverbullet.auth.service.ISysAuthOrganizationService;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
import com.silverbullet.utils.TreeNode1;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.silverbullet.utils.TreeNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织机构管理 控制器
 * Created by jeffyuan on 2018/2/11.
 */
@Controller
@RequestMapping(value = "/auth/sysauthorganization")
public class SysAuthOrganizationController {

    @Autowired
    private ISysAuthOrganizationService sysAuthOrganizationService;

    @RequestMapping(value = "/list/{curpage}.html", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable("curpage") String curpage, String parentId){

        int nCurPage = 1;
        String parentid = sysAuthOrganizationService.getOneByParentId("NONE").getId();

        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }if (parentId != null){
            parentid = parentId;
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/SysAuthOrganization/list");

        BaseDataResult<SysAuthOrganization> results = sysAuthOrganizationService.list(parentid, nCurPage, 5);

        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }




    @RequestMapping(value = "/add.html", method = RequestMethod.POST)
    public String add(Model model, String parentId) {

        SysAuthOrganization sysAuthOrganization = new SysAuthOrganization();

        if(parentId == null){
            model.addAttribute("obj", new SysAuthOrganization());
            return "/SysAuthOrganization/model";
        }

        String parentName = sysAuthOrganizationService.getOneById(parentId).getName();
        sysAuthOrganization.setParentId(parentId);

        model.addAttribute("obj", sysAuthOrganization);
        model.addAttribute("parentName", parentName);
        return "/SysAuthOrganization/model";
    }





    @RequestMapping(value = "/edit.html", method = RequestMethod.POST)
    public String edit(Model model, String id) {
        SysAuthOrganization sysAuthUser = sysAuthOrganizationService.getOneById(id);
        model.addAttribute("obj", sysAuthUser);
        return "/SysAuthOrganization/model";
    }




    @RequestMapping(value = "checkPermission/{curpage}.html", method = RequestMethod.POST)
    public ModelAndView check(@PathVariable("curpage") String curpage, String parentId ){

        int nCurPage = 1;
        parentId = "402888ac547fe1050154800171f30000";
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/SysAuthPost/list");

        BaseDataResult<SysAuthOrganization> results = sysAuthOrganizationService.list(parentId, nCurPage, 5);

        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }



    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (ids == null || ids.isEmpty()) {
            mapRet.put("result", false);
            return mapRet;
        }
        boolean bRet = sysAuthOrganizationService.delete(ids);
        mapRet.put("result", bRet);
        return mapRet;
    }





    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Validated({AddValidate.class}) SysAuthOrganization sysAuthUser,
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
            bTrue = sysAuthOrganizationService.Insert(sysAuthUser);
            message = bTrue ? "添加成功！" : "添加失败！";
        } else {
            bTrue = sysAuthOrganizationService.Update(sysAuthUser);
            message = bTrue ? "修改成功！" : "修改失败！";
        }

        mapRet.put("result", bTrue);
        mapRet.put("message", message);

        return mapRet;
    }





    @RequestMapping(value = "/list/{id}.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getOrgSelect(@PathVariable("id") String Id){
        Map<String, Object> model = new HashMap<String, Object>();
        SysAuthOrganization results= sysAuthOrganizationService.getOneById(Id);
        model.put("results", results);
        return model;
    }



    @RequestMapping(value = "/local/{curpage}.html", method = RequestMethod.GET)
    public ModelAndView local(@PathVariable("curpage") String curpage, String parentId){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/SysAuthOrganization/listContent");

        BaseDataResult<SysAuthOrganization> results = sysAuthOrganizationService.localList(parentId, nCurPage, 5);

        if(results.getResultList().isEmpty()){
            modelAndView.addObject("noData", ToolUtil.noData());
            modelAndView.addObject("results", results);
        }else{
            modelAndView.addObject("results", results);
        }

        modelAndView.addObject("parentId", parentId);
        modelAndView.addObject("curPage", nCurPage);
            return modelAndView;
    }

    @RequestMapping(value = "/tree.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> treeNode(String parentId){
        if(parentId == null){
            parentId = "NONE";
        }

        Map<String, Object> map = new HashMap<>();

        BaseDataResult<SysAuthOrganization> result = sysAuthOrganizationService.findTreeNode(parentId);

        if(result.getResultList().isEmpty()){
            map.put("noData", ToolUtil.noData());
        }else{
            map.put("result", result);
        }

        return map;

//        return sysAuthOrganizationService.findTreeNode();
    }

}

package com.silverbullet.auth.controller;

import com.silverbullet.auth.domain.SysAuthAction;
import com.silverbullet.auth.domain.SysAuthActionTree;
import com.silverbullet.auth.domain.SysAuthPostAction;
import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.auth.service.ISysAuthActionTreeService;
import com.silverbullet.auth.service.ISysAuthPostActionService;
import com.silverbullet.auth.service.ISysAuthPostService;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
import com.silverbullet.utils.TreeNode;
import com.silverbullet.utils.TreeNode1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限定义 控制器
 * Created by jeffyuan on 2018/2/11.
 */
@Controller
@RequestMapping(value = "/auth/sysauthactiontree")
public class SysAuthActionTreeController {

    @Autowired
    private ISysAuthActionTreeService sysAuthActionTreeService;
    @Autowired
    private ISysAuthPostActionService sysAuthPostActionService;

    @RequestMapping(value = "/list/pub.html", method = RequestMethod.GET)
    public ModelAndView index(){
        int nCurPage = 1;

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/SysAuthActionTree/list");

        BaseDataResult<SysAuthActionTree> results = sysAuthActionTreeService.list("NONE", nCurPage, 5);
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }

    @RequestMapping(value = "/list.html", method = RequestMethod.POST)
    public ModelAndView listData(String curpage, String parentId) {
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        if (parentId == null) {
            parentId = "NONE";
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/SysAuthActionTree/listContent");

        BaseDataResult<SysAuthActionTree> results = sysAuthActionTreeService.list(parentId, nCurPage, 5);

        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);
        modelAndView.addObject("list","list");
        modelAndView.addObject("method", "AuthActionTree.loadDataCommon");

        return modelAndView;
    }


    @RequestMapping(value = "/list.do", method = RequestMethod.POST)
    @ResponseBody
    public Map list(){
        HashMap map = new HashMap();
        BaseDataResult<SysAuthActionTree> results = sysAuthActionTreeService.list();
        map.put("list",results);
        return map;
    }


    @RequestMapping(value = "/add.html", method = RequestMethod.POST)
    public String add(Model model) {
        model.addAttribute("obj", new SysAuthActionTree());
        return "/SysAuthActionTree/model";
    }

    @RequestMapping(value = "/edit.html", method = RequestMethod.POST)
    public String edit(Model model, String id) {
        SysAuthActionTree sysAuthActionTree = sysAuthActionTreeService.getOneById(id);
        model.addAttribute("obj", sysAuthActionTree);
        return "/SysAuthActionTree/model";
    }

    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Validated({AddValidate.class}) SysAuthActionTree sysAuthActionTree,
                                    BindingResult result) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (result.hasErrors()) {
            mapRet.put("result", false);
            mapRet.put("errors", result.getFieldErrors());
            return mapRet;
        }

        boolean bTrue = false;
        String message = "";
        if (sysAuthActionTree.getId().isEmpty()) {
            bTrue = sysAuthActionTreeService.Insert(sysAuthActionTree);
            message = bTrue ? "添加成功！" : "添加失败！";
        } else {
            bTrue = sysAuthActionTreeService.Update(sysAuthActionTree);
            message = bTrue ? "修改成功！" : "修改失败！";
        }

        mapRet.put("result", bTrue);
        mapRet.put("message", message);

        return mapRet;
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (ids == null || ids.isEmpty()) {
            mapRet.put("result", false);
            return mapRet;
        }
        boolean bRet = sysAuthActionTreeService.delete(ids);
        mapRet.put("result", bRet);
        return mapRet;
    }

    @RequestMapping(value = "/tree.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> treeNode(String parentId, String postId){

        parentId = parentId == null ? "NONE": parentId;

        Map<String, Object> map = new HashMap<>();

        BaseDataResult<SysAuthActionTree> result = sysAuthActionTreeService.findTreeNode(parentId);

        List<SysAuthPostAction> list = postId == null ?
                new ArrayList<>():
                sysAuthPostActionService.getParamsByPostId(postId);

        if(result.getResultList().isEmpty()){
            map.put("noData", ToolUtil.noData());
        }else{
            map.put("result", result);
            map.put("list", list);
        }

        return map;
    }


    @RequestMapping(value = "/check.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> check(String check){
        Map<String, Object> mapRet = new HashMap<String, Object>();

        return mapRet;
    }

    @RequestMapping(value = "/treeNodeMove.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> treeNodeMove(String currentId, String brotherId) {
        Map<String, Object> mapt = new HashMap<>();
        if(currentId == null && brotherId == null){
            mapt.put("status", false);
            mapt.put("message", "修改失败");
            return mapt;
        }

        Boolean status = sysAuthActionTreeService.exchangeActionTreeSort(currentId, brotherId);
        if(status == true) {
            mapt.put("status", true);
        } else {
            mapt.put("status", false);
            mapt.put("message", "修改失败");
        }

        return mapt;
    }

}

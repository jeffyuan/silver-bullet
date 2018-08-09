package com.silverbullet.auth.controller;

import com.silverbullet.auth.domain.SysAuthActionTree;
import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.auth.domain.SysAuthUserOrg;
import com.silverbullet.auth.domain.SysAuthUserPost;
import com.silverbullet.auth.service.ISysAuthOrganizationService;
import com.silverbullet.auth.service.ISysAuthUserService;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.utils.BaseDataResult;
import org.beetl.sql.core.annotatoin.Param;
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
import java.util.List;
import java.util.Map;

/**
 * 用户管理 控制器
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

        BaseDataResult<SysAuthUser> results = sysAuthUserService.list(nCurPage, 5);

        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }

    @RequestMapping(value = "/add.html", method = RequestMethod.POST)
    public String add(Model model) {
        model.addAttribute("obj", new SysAuthUser());
        return "/SysAuthUser/add";
    }

    @RequestMapping(value = "/edit.html", method = RequestMethod.POST)
    public String edit(Model model, String id) {
        SysAuthUser sysAuthUser = sysAuthUserService.getOneById(id);
        List<Map<String, String>> sysAuthUserOrg = sysAuthUserService.getOneByUserId(id);
        List<Map<String, String>> sysAuthUserPost = sysAuthUserService.getPostByUserId(id);
        model.addAttribute("obj", sysAuthUser);
        model.addAttribute("org", sysAuthUserOrg);
        model.addAttribute("post", sysAuthUserPost);
        return "/SysAuthUser/add";
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (ids == null || ids.isEmpty()) {
            mapRet.put("result", false);
            return mapRet;
        }
        boolean bRet = sysAuthUserService.delete(ids);
        mapRet.put("result", bRet);
        return mapRet;
    }

    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Validated({AddValidate.class}) SysAuthUser sysAuthUser,String postId, String OrganizationId,
                                    String UorgId, String UpostId, BindingResult result) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (result.hasErrors()) {
            mapRet.put("result", false);
            mapRet.put("errors", result.getFieldErrors());
            return mapRet;
        }
        if("".equals(sysAuthUser.getName()) || sysAuthUser.getName()==null){
            mapRet.put("result", false);
            mapRet.put("message", "名称不能为空！");
            return mapRet;
        }
        if("".equals(sysAuthUser.getUsername()) || sysAuthUser.getUsername()==null){
            mapRet.put("result", false);
            mapRet.put("message", "用户名不能为空！");
            return mapRet;
        }
        boolean bTrue = false;
        String message = "";
        if (sysAuthUser.getId().isEmpty()) {
            bTrue = sysAuthUserService.Insert(sysAuthUser, postId, OrganizationId);
            message = bTrue ? "添加成功！" : "添加失败！";
        } else {
            bTrue = sysAuthUserService.Update(sysAuthUser, postId, OrganizationId, UorgId, UpostId);
            message = bTrue ? "修改成功！" : "修改失败！";
        }

        mapRet.put("result", bTrue);
        mapRet.put("message", message);

        return mapRet;
    }



    @RequestMapping(value = "/findActionTreeName", method = RequestMethod.POST)
    @ResponseBody
    public List<SysAuthActionTree> findActionTreeName(SysAuthActionTree sysAuthActionTree){
        return sysAuthUserService.findList(sysAuthActionTree);

    }
    @RequestMapping(value= "/findPostName", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> findPostName(String id){
        return sysAuthUserService.findPostNameByActionTreeId(id);
    }

}

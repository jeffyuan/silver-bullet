package com.silverbullet.auth.controller;

import com.silverbullet.auth.domain.SysAuthActionTree;
import com.silverbullet.auth.domain.SysAuthOrganization;
import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.auth.service.ISysAuthOrganizationService;
import com.silverbullet.auth.service.ISysAuthUserService;
import com.silverbullet.core.validate.AddValidate;
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

    @Autowired
    private ISysAuthOrganizationService sysAuthOrganizationService;


    @RequestMapping(value = "list/pub.html", method = RequestMethod.GET)
    public ModelAndView index() {
        int nCurPage = 1;

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/SysAuthUser/list");

        BaseDataResult<SysAuthUser> results = sysAuthUserService.list(nCurPage, 5);

        modelAndView.addObject("list", "list");
        modelAndView.addObject("method", "AuthUser.loadData");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }

    @RequestMapping(value = "/list.html", method = RequestMethod.POST)
    public ModelAndView listData(String curpage) {
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/SysAuthUser/listContent");

        BaseDataResult<SysAuthUser> results = sysAuthUserService.list(nCurPage, 5);


        modelAndView.addObject("list", "list");
        modelAndView.addObject("method", "AuthUser.loadData");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }

    @RequestMapping(value = "/dictitem/list/{userId}.html", method = RequestMethod.POST)
    public ModelAndView loadOrganizationItem(@PathVariable("userId") String userId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/SysAuthUser/actionModel");
        BaseDataResult<SysAuthOrganization> org = sysAuthOrganizationService.getOrgSelect();
        modelAndView.addObject("org", org.getResultList());
        modelAndView.addObject("userId", userId);
        String orgName;
        String orgId;
        String postName;
        String postId;
        if (sysAuthUserService.getOneByUserId(userId).size() == 0) {
            orgName = "";
            orgId = "";
        } else {
            orgName = sysAuthUserService.getOneByUserId(userId).get(0).get("organizationName");
            orgId = sysAuthUserService.getOneByUserId(userId).get(0).get("organizationId");
        }

        if (sysAuthUserService.getPostByUserId(userId).size() == 0) {
            postName = "";
            postId = "";
        } else {
            postName = sysAuthUserService.getPostByUserId(userId).get(0).get("postName");
            postId = sysAuthUserService.getPostByUserId(userId).get(0).get("postId");
        }

        modelAndView.addObject("OrganizationId", orgId);
        modelAndView.addObject("OrganizationName", orgName);
        modelAndView.addObject("postId", postId);
        modelAndView.addObject("postName", postName);

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
        BaseDataResult<SysAuthOrganization> org = sysAuthOrganizationService.getOrgSelect();
        List<Map<String, String>> sysAuthUserOrg = sysAuthUserService.getOneByUserId(id);
        List<Map<String, String>> sysAuthUserPost = sysAuthUserService.getPostByUserId(id);
        model.addAttribute("obj", sysAuthUser);
        model.addAttribute("userId", id);
        model.addAttribute("org", org.getResultList());

        String orgName;
        String orgId;
        String postName;
        String postId;
        if (sysAuthUserService.getOneByUserId(id).size() == 0) {
            orgName = "";
            orgId = "";
        } else {
            orgName = sysAuthUserService.getOneByUserId(id).get(0).get("organizationName");
            orgId = sysAuthUserService.getOneByUserId(id).get(0).get("organizationId");
        }

        if (sysAuthUserService.getPostByUserId(id).size() == 0) {
            postName = "";
            postId = "";
        } else {
            postName = sysAuthUserService.getPostByUserId(id).get(0).get("postName");
            postId = sysAuthUserService.getPostByUserId(id).get(0).get("postId");
        }

        model.addAttribute("OrganizationId", orgId);
        model.addAttribute("OrganizationName", orgName);
        model.addAttribute("UorgId",sysAuthUserOrg.get(0).get("id"));
        model.addAttribute("UpostId",sysAuthUserPost.get(0).get("id"));
        model.addAttribute("postId", postId);
        model.addAttribute("postName", postName);
        return "/SysAuthUser/add";
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        Map<String, Object> mapRet = new HashMap<String, Object>();
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
    public Map<String, Object> save(@Validated({AddValidate.class}) SysAuthUser sysAuthUser, String postId, String OrganizationId,
                                    String UorgId, String UpostId, BindingResult result) {
        Map<String, Object> mapRet = new HashMap<String, Object>();
        if (result.hasErrors()) {
            mapRet.put("result", false);
            mapRet.put("errors", result.getFieldErrors());
            return mapRet;
        }
        if ("".equals(sysAuthUser.getName()) || sysAuthUser.getName() == null) {
            mapRet.put("result", false);
            mapRet.put("message", "名称不能为空！");
            return mapRet;
        }
        if ("".equals(sysAuthUser.getUsername()) || sysAuthUser.getUsername() == null) {
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

    @RequestMapping(value = "/setPost.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> change(String UserId, String OrganizationId, String postId) {
        System.out.println(UserId);
        Map<String, Object> mapRet = new HashMap<String, Object>();

        String org_id = sysAuthUserService.getOneByUserId(UserId).get(0).get("id");
        String post_id = sysAuthUserService.getPostByUserId(UserId).get(0).get("id");

        boolean bTrue;
        String message;
        if (sysAuthUserService.getOneByUserId(UserId) == null) {
            bTrue = sysAuthUserService.insertUserOrgPost(UserId, OrganizationId, postId);
            message = bTrue ? "添加成功！" : "添加失败！";
        } else {
            bTrue = sysAuthUserService.updatetUserOrgPost(org_id, post_id, UserId, OrganizationId, postId);
            message = bTrue ? "修改成功！" : "修改失败！";
        }

        mapRet.put("result", bTrue);
        mapRet.put("message", message);
        if (bTrue != true) {
            mapRet.put("error", "修改失败");
        }
        return mapRet;
    }


    @RequestMapping(value = "/findActionTreeName", method = RequestMethod.POST)
    @ResponseBody
    public List<SysAuthActionTree> findActionTreeName(SysAuthActionTree sysAuthActionTree) {
        return sysAuthUserService.findList(sysAuthActionTree);

    }

    @RequestMapping(value = "/findPostName", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> findPostName(String id) {
        return sysAuthUserService.findPostNameByActionTreeId(id);
    }

}

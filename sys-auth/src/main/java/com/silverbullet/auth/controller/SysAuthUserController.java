package com.silverbullet.auth.controller;

import com.silverbullet.auth.domain.SysAuthActionTree;
import com.silverbullet.auth.domain.SysAuthOrganization;
import com.silverbullet.auth.domain.SysAuthUser;
import com.silverbullet.auth.service.ISysAuthOrganizationService;
import com.silverbullet.auth.service.ISysAuthUserService;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
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

    @RequestMapping(value = "/loadOrganizationItem.html", method = RequestMethod.POST)
    public ModelAndView loadOrganizationItem(String userId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/SysAuthUser/actionModel");

        BaseDataResult<SysAuthOrganization> org = sysAuthOrganizationService.getOrgSelect();
        Map<String, Object> user = sysAuthUserService.changeOrgPost(userId);

        modelAndView.addObject("org", org.getResultList());
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("OrganizationId", user.get("orgId"));
        modelAndView.addObject("OrganizationName", user.get("orgName"));
        modelAndView.addObject("postId", user.get("postId"));
        modelAndView.addObject("postName", user.get("postName"));
        return modelAndView;
    }

    @RequestMapping(value = "/add.html", method = RequestMethod.POST)
    public String add(Model model) {
        BaseDataResult<SysAuthOrganization> org = sysAuthOrganizationService.getOrgSelect();
        model.addAttribute("obj", new SysAuthUser());
        model.addAttribute("org", org.getResultList());
        model.addAttribute("OrganizationName", "请选择部门");
        model.addAttribute("postName", "请选择岗位");
        return "/SysAuthUser/add";
    }

    @RequestMapping(value = "/edit.html", method = RequestMethod.POST)
    public String edit(Model model, String id) {
        SysAuthUser sysAuthUser = sysAuthUserService.getOneById(id);
        BaseDataResult<SysAuthOrganization> org = sysAuthOrganizationService.getOrgSelect();
        List<Map<String, String>> sysAuthUserOrg = sysAuthUserService.getOneByUserId(id);
        List<Map<String, String>> sysAuthUserPost = sysAuthUserService.getPostByUserId(id);
        Map<String, Object> user = sysAuthUserService.changeOrgPost(id);
        if (sysAuthUserOrg.size() != 0) {
            model.addAttribute("UorgId", sysAuthUserOrg.get(0).get("id"));
        }
        if (sysAuthUserPost.size() != 0) {
            model.addAttribute("UpostId", sysAuthUserPost.get(0).get("id"));
        }

        model.addAttribute("obj", sysAuthUser);
        model.addAttribute("userId", id);
        model.addAttribute("org", org.getResultList());
        model.addAttribute("OrganizationId", user.get("orgId"));
        model.addAttribute("OrganizationName", user.get("orgName"));
        model.addAttribute("postId", user.get("postId"));
        model.addAttribute("postName", user.get("postName"));
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
    public Map<String, Object> save(@Validated({AddValidate.class}) SysAuthUser sysAuthUser, BindingResult result, String postId, String OrganizationId,
                                    String UorgId, String UpostId) {
        Map<String, Object> mapRet = new HashMap<String, Object>();
        if (result.hasErrors()) {
            mapRet.put("result", false);
            mapRet.put("errors", result.getFieldErrors());
            return mapRet;
        }
        if (postId.equals("disabled selected") && OrganizationId != null) {
            mapRet.put("result", false);
            mapRet.put("message", "请选择正确的岗位！");
            mapRet.put("address","postId");
            return mapRet;
        }

        boolean bTrue = false;
        boolean aTrue = false;
        String message = "";

        if (sysAuthUser.getId().isEmpty()) {
            bTrue = sysAuthUserService.Insert(sysAuthUser, postId, OrganizationId);
            message = bTrue ? "添加成功！" : "添加失败！";
        } else if (sysAuthUserService.getOneByUserId(sysAuthUser.getId()).isEmpty()) {
            Map<String, String> userOrgPost = sysAuthUserService.getUserOrgPost(sysAuthUser.getId());
            aTrue = sysAuthUserService.updatetUserOrgPost(userOrgPost.get("org_id"), userOrgPost.get("post_id"), sysAuthUser.getId(), OrganizationId, postId);
            bTrue = sysAuthUserService.Update(sysAuthUser, postId, OrganizationId, UorgId, UpostId);
            message = (bTrue && aTrue) ? "修改成功！" : "修改失败！";

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
        Map<String, Object> mapRet = new HashMap<String, Object>();
        boolean bTrue;
        String message;
        if (postId.equals("disabled selected") || postId.isEmpty()) {
            mapRet.put("result", false);
            mapRet.put("message", "请选择正确的岗位！");
            return mapRet;
        }
        if (sysAuthUserService.getOneByUserId(UserId).isEmpty()) {
            bTrue = sysAuthUserService.insertUserOrgPost(UserId, OrganizationId, postId);
            message = bTrue ? "添加成功！" : "添加失败！";
        } else {
            String org_id = sysAuthUserService.getOneByUserId(UserId).get(0).get("id");
            String post_id = sysAuthUserService.getPostByUserId(UserId).get(0).get("id");
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

    @RequestMapping(value = "/changePassword.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> changePassword(String id,String newPassword, String checkPassword) {
        Map<String, String> mapRet = new HashMap<String, String>();
        if (newPassword.isEmpty()){
            mapRet.put("result","false");
            mapRet.put("message","请输入修改密码");
            return mapRet;
        }
        if (newPassword.equals(checkPassword)) {
            String passwordMD5 = ToolUtil.getPassword(10, "11", newPassword, "MD5");
            sysAuthUserService.changePassword(id,passwordMD5);
            mapRet.put("result","true");
            mapRet.put("message","修改成功");
        }else {
            mapRet.put("result","false");
            mapRet.put("message","两次密码不一致");
        }
        return mapRet;
    }


    @RequestMapping(value = "/resetPassword.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> resetPassword(String ids) {
        Map<String, Object> mapRet = new HashMap<String, Object>();
        if (ids == null || ids.isEmpty()) {
            mapRet.put("result", false);
            return mapRet;
        }
        boolean bRet = sysAuthUserService.resetPassword(ids);
        mapRet.put("result", bRet);
        return mapRet;
    }

}

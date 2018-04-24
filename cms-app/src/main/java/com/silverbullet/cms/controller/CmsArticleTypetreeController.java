package com.silverbullet.cms.controller;

import com.silverbullet.cms.domain.CmsArticleTypetree;
import com.silverbullet.cms.service.ICmsArticleTypetreeService;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.utils.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章类别 控制器
 * Created by jeffyuan on 2018/2/11.
 */
@Controller
@RequestMapping(value = "/cms/cmsarticletypetree")
public class CmsArticleTypetreeController {

    @Autowired
    private ICmsArticleTypetreeService cmsArticleTypetreeService;

    @RequestMapping(value = "/list/pub.html", method = RequestMethod.GET)
    public ModelAndView index(){
        int nCurPage = 1;

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsArticleTypetree/list");

        return modelAndView;
    }

    @RequestMapping(value = "/list.do", method = RequestMethod.POST)
    @ResponseBody
    public List<Map<String,Object>> listData(String parentId){
        if (parentId == null) {
            parentId = "NONE";
        }
        List<Map<String, Object>> results = cmsArticleTypetreeService.findListByModule("NEWS", "back", parentId);
        return results;
     }

    @RequestMapping(value = "/tree/list.do", method = RequestMethod.POST)
    @ResponseBody
    public List<TreeNode> listDataForTree(String parentId){
        if (parentId == null) {
            parentId = "NONE";
        }

        List<Map<String, Object>> results = cmsArticleTypetreeService.findListByModule("NEWS", "back", parentId);
        List<TreeNode> treeNodeList = TreeNode.formatNodes2TreeNode(results, "name","parent_id", "id", "", "children_num");
        return treeNodeList;
    }

    @RequestMapping(value = "/add.html", method = RequestMethod.POST)
    public String add(Model model, String parentId) {
        CmsArticleTypetree cmsArticleTypetree = new CmsArticleTypetree();
        cmsArticleTypetree.setParentId(parentId);
        model.addAttribute("obj", cmsArticleTypetree);
        return "/CmsArticleTypetree/model";
    }

    @RequestMapping(value = "/edit.html", method = RequestMethod.POST)
    public String edit(Model model, String id) {
        CmsArticleTypetree cmsArticleTypetree = cmsArticleTypetreeService.getOneById(id);
        model.addAttribute("obj", cmsArticleTypetree);

        return "/CmsArticleTypetree/model";
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (ids == null || ids.isEmpty()) {
            mapRet.put("result", false);
            return mapRet;
        }

        boolean bRet = cmsArticleTypetreeService.delete(ids);

        mapRet.put("result", bRet);
        return mapRet;
    }

    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Validated({AddValidate.class}) CmsArticleTypetree cmsArticleTypetree,
                                    BindingResult result) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (result.hasErrors()) {
            mapRet.put("result", false);
            mapRet.put("errors", result.getFieldErrors());
            return mapRet;
        }

        boolean bTrue = false;
        String message = "";
        if (cmsArticleTypetree.getId().isEmpty()) {
            cmsArticleTypetree.setModule("NEWS");
            cmsArticleTypetree.setState("1");
            cmsArticleTypetree.setDomain("back");
            cmsArticleTypetree.setChildrenNum(0);
            bTrue = cmsArticleTypetreeService.Insert(cmsArticleTypetree);
            message = bTrue ? "添加成功！" : "添加失败！";
        } else {

            bTrue = cmsArticleTypetreeService.Update(cmsArticleTypetree.getId(), cmsArticleTypetree);
            message = bTrue ? "修改成功！" : "修改失败！";
        }

        mapRet.put("result", bTrue);
        mapRet.put("message", message);

        return mapRet;
    }
}

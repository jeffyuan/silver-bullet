package com.silverbullet.cms.controller.CmsRepair;


import com.silverbullet.cms.domain.CmsRepairServiceType;
import com.silverbullet.cms.service.ICmsRepairServiceTypeService;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
import com.silverbullet.utils.TreeNode;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.ibatis.annotations.Mapper;
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

@Controller
@RequestMapping(value = "/cms/cmsrepairservicetype")
public class CmsRepairServiceTypeController {

    @Autowired
    private ICmsRepairServiceTypeService cmsRepairServiceTypeService;

    @RequestMapping(value = "list/pub.html", method = RequestMethod.GET)
    public ModelAndView index(){
        int nCurPage = 1;

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairServiceType/list");

        BaseDataResult<CmsRepairServiceType> results = cmsRepairServiceTypeService.list(nCurPage, 10);


        if(results.getResultList().isEmpty()){
            modelAndView.addObject("noData", ToolUtil.noData());
        }
        modelAndView.addObject("list","list");
        modelAndView.addObject("method", "CmsRepairType.loadData");
        modelAndView.addObject("searchObj","CmsRepairType.search()");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }


    @RequestMapping(value = "/list.html", method = RequestMethod.POST)
    public ModelAndView listData(String curpage, String parentId){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        BaseDataResult<CmsRepairServiceType> results = new BaseDataResult<>();
        modelAndView.setViewName("/CmsRepairServiceType/listContent");

        if(parentId  != null){
            results = cmsRepairServiceTypeService.findChildListByParentId(parentId, nCurPage, 10);
        }else{
            results = cmsRepairServiceTypeService.list(nCurPage, 10);
        }

        modelAndView.addObject("list","list");
        modelAndView.addObject("method", "CmsRepairType.loadData");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }


    @RequestMapping(value = "/check.html", method = RequestMethod.POST)
    public String check(Model model, String pageName, String id){

        CmsRepairServiceType cmsRepairServiceType = cmsRepairServiceTypeService.getOneById(id);

        CmsRepairServiceType cmsRepairServiceTypeParent = cmsRepairServiceTypeService.getOneById(cmsRepairServiceType.getParentId());
        model.addAttribute("obj", cmsRepairServiceType);
        model.addAttribute("parentNode", cmsRepairServiceTypeParent);
        model.addAttribute("disabled", "disabled");
        return "/CmsRepairServiceType/model";
    }


    @RequestMapping(value = "/add.html", method = RequestMethod.POST)
    public String add(Model model, String pageName, String parentId) {

        CmsRepairServiceType cmsRepairServiceType = cmsRepairServiceTypeService.setParentId(parentId);
        CmsRepairServiceType patentNode = cmsRepairServiceTypeService.findParentById(parentId);

        model.addAttribute("obj", cmsRepairServiceType);
        model.addAttribute("parentNode", patentNode);
        model.addAttribute("disabled", " ");

        return "/CmsRepairServiceType/model";
    }


    @RequestMapping(value = "/edit.html", method = RequestMethod.POST)
    public String edit(Model model, String id){

        CmsRepairServiceType cmsRepairServiceType = cmsRepairServiceTypeService.getOneById(id);

        CmsRepairServiceType cmsRepairServiceTypeParent = cmsRepairServiceTypeService.getOneById(cmsRepairServiceType.getParentId());
        model.addAttribute("obj", cmsRepairServiceType);
        model.addAttribute("parentNode", cmsRepairServiceTypeParent);
        model.addAttribute("disabled", " ");

        return "/CmsRepairServiceType/model";
    }


    @RequestMapping(value = "tree.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> tree(String parentId){
        if(parentId == null){
            parentId = "NONE";
        }

        Map<String, Object> map = new HashMap<>();

        BaseDataResult<CmsRepairServiceType> result = cmsRepairServiceTypeService.findListByModule(parentId);

        if(result.getResultList().isEmpty()){
            map.put("noData", ToolUtil.noData());
        }else{
            map.put("result", result);
        }

        return map;
    }


    @RequestMapping(value = "/childList.html", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView childList(String parentId){
        int nCurPage = 1;

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairServiceType/listContent");

        BaseDataResult<CmsRepairServiceType> results = cmsRepairServiceTypeService.findChildListByParentId(parentId, nCurPage,10);


        if(results.getResultList().isEmpty()){
            modelAndView.addObject("noData", ToolUtil.noData());
        }
        modelAndView.addObject("list","list");
        modelAndView.addObject("method", "CmsRepairType.loadData");
        modelAndView.addObject("searchObj","CmsRepairType.search()");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }


    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Validated({AddValidate.class}) CmsRepairServiceType cmsRepairServiceType,
                                    BindingResult result) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (result.hasErrors()) {
            mapRet.put("result", false);
            mapRet.put("errors", result.getFieldErrors());
            return mapRet;
        }

        boolean bTrue = false;
        String message = "";
        if (cmsRepairServiceType.getId().isEmpty()) {
            bTrue = cmsRepairServiceTypeService.Insert(cmsRepairServiceType);
            message = bTrue ? "添加成功！" : "添加失败！";
        }else {
            bTrue = cmsRepairServiceTypeService.Update(cmsRepairServiceType);
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

        boolean bRet = cmsRepairServiceTypeService.delete(ids);

        mapRet.put("result", bRet);
        return mapRet;
    }

    @RequestMapping(value = "/search.do", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView search(String search, String curpage){
        int nCurPage = 1;

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairServiceType/listContent");

        BaseDataResult<CmsRepairServiceType> results = cmsRepairServiceTypeService.search(search, nCurPage ,10);

        if(results.getResultList().isEmpty()){
            modelAndView.addObject("noData", ToolUtil.noData());
        }

        modelAndView.addObject("list","searchList");
        modelAndView.addObject("searchValue", "'"+search+"'");
        modelAndView.addObject("method","CmsRepairService.loadSearchData");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);


        return modelAndView;
    }


}

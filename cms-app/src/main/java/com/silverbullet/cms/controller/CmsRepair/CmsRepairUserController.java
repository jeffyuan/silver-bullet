package com.silverbullet.cms.controller.CmsRepair;


import com.silverbullet.cms.domain.CmsArticleComment;
import com.silverbullet.cms.domain.CmsRepairFault;
import com.silverbullet.cms.domain.CmsRepairUser;
import com.silverbullet.cms.pojo.CmsArticleEx;
import com.silverbullet.cms.service.ICmsArticleCommentService;
import com.silverbullet.cms.service.ICmsArticleService;
import com.silverbullet.cms.service.ICmsArticleTypetreeService;
import com.silverbullet.cms.service.ICmsRepairUserService;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.silverbullet.core.validate.AddValidate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户管理 控制器
 * Created by OFG on 2018/7/23.
 */

@Controller
@RequestMapping(value = "/cms/cmsrepairuser")
public class CmsRepairUserController {


    @Autowired
    private ICmsRepairUserService cmsRepairUserService;


    @RequestMapping(value = "list/pub.html", method = RequestMethod.GET)
    public ModelAndView index(){
        int nCurPage = 1;

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairUser/list");

        BaseDataResult<CmsRepairUser> results = cmsRepairUserService.list(nCurPage, 10);

        modelAndView.addObject("list","list");
        modelAndView.addObject("method", "CmsRepairUser.loadData");
        modelAndView.addObject("searchObj","CmsRepairUser.search()");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;

    }


    @RequestMapping(value = "/searchList.html", method = RequestMethod.POST)
    public ModelAndView searchIndex(String search, String curpage){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairUser/listContent");

        BaseDataResult<CmsRepairUser> results = cmsRepairUserService.search(search, nCurPage ,10);

        modelAndView.addObject("list","searchList");
        modelAndView.addObject("searchValue", "'"+search+"'");
        modelAndView.addObject("method", "CmsRepairUser.loadSearchData");
        modelAndView.addObject("searchObj","CmsRepairUser.search()");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;

    }



    @RequestMapping(value = "/searchListModel.html", method = RequestMethod.POST)
    public ModelAndView searchIndexModel(String search, String curpage){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairFaultService/modelList/userModelList");

        BaseDataResult<CmsRepairUser> results = cmsRepairUserService.search(search, nCurPage ,5);

        modelAndView.addObject("list","searchListModel");
        modelAndView.addObject("searchValue", "'"+search+"'");
        modelAndView.addObject("method", "CmsRepairUser.loadSearchDataModel");
        modelAndView.addObject("searchObj","CmsRepairUser.modelSearch()");
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
        modelAndView.setViewName("/CmsRepairUser/listContent");

        BaseDataResult<CmsRepairUser> results = cmsRepairUserService.list(nCurPage, 10);

        modelAndView.addObject("list","list");
        modelAndView.addObject("method", "CmsRepairUser.loadData");
        modelAndView.addObject("searchObj","CmsRepairUser.search()");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }


    @RequestMapping(value = "/check.html", method = RequestMethod.POST)
    public String check(Model model, String pageName, String id){

        CmsRepairUser cmsRepairUser = cmsRepairUserService.getOneById(id);
        model.addAttribute("obj", cmsRepairUser);
        model.addAttribute("disabled", "disabled");
        return "/CmsRepairUser/model";
    }



    @RequestMapping(value = "/add.html", method = RequestMethod.POST)
    public String add(Model model, String pageName) {

        model.addAttribute("obj", new CmsRepairUser());
        model.addAttribute("disabled", " ");

        return "/CmsRepairUser/model";
    }


    @RequestMapping(value = "/edit.html", method = RequestMethod.POST)
    public String edit(Model model, String id) {
        CmsRepairUser cmsRepairUser = cmsRepairUserService.getOneById(id);
        model.addAttribute("obj", cmsRepairUser);
        model.addAttribute("disabled", " ");

        return "/CmsRepairUser/model";
    }


    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Validated({AddValidate.class}) CmsRepairUser cmsRepairUser,
                                    BindingResult result) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (result.hasErrors()) {
            mapRet.put("result", false);
            mapRet.put("errors", result.getFieldErrors());
            return mapRet;
        }

        boolean bTrue = false;
        String message = "";
        if (cmsRepairUser.getId().isEmpty()) {
            bTrue = cmsRepairUserService.Insert(cmsRepairUser);
            message = bTrue ? "添加成功！" : "添加失败！";
        }else {
            bTrue = cmsRepairUserService.Update(cmsRepairUser);
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

        boolean bRet = cmsRepairUserService.delete(ids);

        mapRet.put("result", bRet);
        return mapRet;
    }


    @RequestMapping(value = "/search.do", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView search(String search, String curpage){
        int nCurPage = 1;


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairUser/listContent");

        BaseDataResult<CmsRepairUser> results = cmsRepairUserService.search(search, nCurPage ,10);

        if(results.getResultList().isEmpty()){
            modelAndView.addObject("noData", ToolUtil.noData());
        }

        modelAndView.addObject("list","searchList");
        modelAndView.addObject("searchValue", "'"+search+"'");
        modelAndView.addObject("method", "CmsRepairUser.loadSearchData");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);


        return modelAndView;
    }


    @RequestMapping(value = "/listModel.html", method = RequestMethod.POST)
    public ModelAndView listDataModel(String curpage){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairFaultService/modelList/userModelList");

        BaseDataResult<CmsRepairUser> results = cmsRepairUserService.list(nCurPage, 5);

        modelAndView.addObject("list","listModel");
        modelAndView.addObject("method", "CmsRepairUser.loadDataModel");
        modelAndView.addObject("searchObj","CmsRepairUser.search()");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }



    @RequestMapping(value = "/searchModel.do", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView searchModel(String search, String curpage){
        int nCurPage = 1;


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairFaultService/modelList/userModelList");

        BaseDataResult<CmsRepairUser> results = cmsRepairUserService.search(search, nCurPage ,5);

        if(results.getResultList().isEmpty()){
            modelAndView.addObject("noData", ToolUtil.noData());
        }

        modelAndView.addObject("list","searchListModel");
        modelAndView.addObject("searchValue", "'"+search+"'");
        modelAndView.addObject("method", "CmsRepairUser.loadSearchDataModel");
        modelAndView.addObject("searchObj","CmsRepairUser.modelSearch()");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);


        return modelAndView;
    }


    @RequestMapping(value = "/blackList.html", method = RequestMethod.POST)
    public String blackList(Model model, String id) {
        CmsRepairUser cmsRepairUser = cmsRepairUserService.getOneById(id);
        model.addAttribute("obj", cmsRepairUser);
        model.addAttribute("disabled", " ");

        return "/CmsRepairUser/modelBlackList";
    }


    @RequestMapping(value = "/setBlackList.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setBlackList(CmsRepairUser cmsRepairUser) {
        Map<String,Object> mapRet = new HashMap<String, Object>();

        boolean bTrue = false;
        String message = "";

        bTrue = cmsRepairUserService.setBlackListById(cmsRepairUser);
        message = bTrue ? "设置成功！" : "设置失败！";


        mapRet.put("result", bTrue);
        mapRet.put("message", message);

        return mapRet;
    }

}

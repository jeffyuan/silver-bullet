package com.silverbullet.cms.controller.CmsRepair;

import com.silverbullet.cms.domain.CmsRepairFault;
import com.silverbullet.cms.service.*;
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
import com.silverbullet.core.validate.AddValidate;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户管理 控制器
 * Created by OFG on 2018/7/23.
 */

@Controller
@RequestMapping(value = "/cms/cmsrepairfault")
public class  CmsRepairFaultController{

      @Autowired
      private ICmsRepairFaultService  cmsRepairFaultService;

      @RequestMapping(value = "list/pub.html", method = RequestMethod.GET)
      public ModelAndView index(){
          int nCurPage = 1;

          ModelAndView modelAndView = new ModelAndView();
          modelAndView.setViewName("/CmsRepairFault/list");

          BaseDataResult<CmsRepairFault> results = cmsRepairFaultService.list(nCurPage, 10);

          modelAndView.addObject("list","list");
          modelAndView.addObject("method", "CmsRepairFault.loadData");
          modelAndView.addObject("searchObj","CmsRepairFault.search()");
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
        modelAndView.setViewName("/CmsRepairFault/listContent");

        BaseDataResult<CmsRepairFault> results = cmsRepairFaultService.list(nCurPage, 10);

        modelAndView.addObject("list","list");
        modelAndView.addObject("method", "CmsRepairFault.loadData");
        modelAndView.addObject("searchObj","CmsRepairFault.search()");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }



    @RequestMapping(value = "/check.html", method = RequestMethod.POST)
    public String check(Model model, String pageName, String id){

          CmsRepairFault cmsRepairFault = cmsRepairFaultService.getOneById(id);
          model.addAttribute("obj", cmsRepairFault);
          model.addAttribute("disabled", "disabled");
          return "/CmsRepairFault/model";
    }



      @RequestMapping(value = "/add.html", method = RequestMethod.POST)
      public String add(Model model, String pageName) {

          model.addAttribute("obj", new CmsRepairFault());
          model.addAttribute("disabled", " ");

          return "/CmsRepairFault/model";
      }


    @RequestMapping(value = "/edit.html", method = RequestMethod.POST)
    public String edit(Model model, String id) {
        CmsRepairFault cmsRepairFault = cmsRepairFaultService.getOneById(id);
        model.addAttribute("obj", cmsRepairFault);
        model.addAttribute("disabled", " ");

        return "/CmsRepairFault/model";
    }

    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Validated({AddValidate.class}) CmsRepairFault cmsRepairFault,
                                    BindingResult result) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (result.hasErrors()) {
            mapRet.put("result", false);
            mapRet.put("errors", result.getFieldErrors());
            return mapRet;
        }

        boolean bTrue = false;
        String message = "";
        if (cmsRepairFault.getId().isEmpty()) {
            bTrue = cmsRepairFaultService.Insert(cmsRepairFault);
            message = bTrue ? "添加成功！" : "添加失败！";
        }else {
            bTrue = cmsRepairFaultService.Update(cmsRepairFault);
            message = bTrue ? "修改成功！" : "修改失败！";
        }

        mapRet.put("result", bTrue);
        mapRet.put("message", message);

        return mapRet;
    }


    @RequestMapping(value = "/search.do", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView search(String search, String curpage){
        int nCurPage = 1;


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairFault/listContent");

        BaseDataResult<CmsRepairFault> results = cmsRepairFaultService.search(search, nCurPage ,10);

        if(results.getResultList().isEmpty()){
            modelAndView.addObject("noData", ToolUtil.noData());
        }

        modelAndView.addObject("list","searchList");
        modelAndView.addObject("searchValue", "'"+search+"'");
        modelAndView.addObject("method", "CmsRepairFault.loadSearchData");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);


        return modelAndView;
    }


    @RequestMapping(value = "/searchModel.do", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView searchModel(String search, String curpage){
        int nCurPage = 1;


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairFaultService/modelList/faultModelList");

        BaseDataResult<CmsRepairFault> results = cmsRepairFaultService.search(search, nCurPage ,5);

        if(results.getResultList().isEmpty()){
            modelAndView.addObject("noData", ToolUtil.noData());
        }

        modelAndView.addObject("list","searchListModel");
        modelAndView.addObject("searchValue", "'"+search+"'");
        modelAndView.addObject("method", "CmsRepairFault.loadSearchDataModel");
        modelAndView.addObject("searchObj","CmsRepairFault.modelSearch()");
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
        modelAndView.setViewName("/CmsRepairFaultService/modelList/faultModelList");

        BaseDataResult<CmsRepairFault> results = cmsRepairFaultService.list(nCurPage, 5);

        modelAndView.addObject("list","listModel");
        modelAndView.addObject("method", "CmsRepairFault.loadDataModel");
        modelAndView.addObject("searchObj","CmsRepairFault.search()");
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

        boolean bRet = cmsRepairFaultService.delete(ids);

        mapRet.put("result", bRet);
        return mapRet;
    }


    @RequestMapping(value = "/searchList.html", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView searchList(String search, String curpage){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairFault/listContent");

        BaseDataResult<CmsRepairFault> results = cmsRepairFaultService.search(search, nCurPage ,10);

        if(results.getResultList().isEmpty()){
            modelAndView.addObject("noData", ToolUtil.noData());
        }

        modelAndView.addObject("list","searchList");
        modelAndView.addObject("searchValue", "'"+search+"'");
        modelAndView.addObject("method", "CmsRepairFault.loadSearchData");
        modelAndView.addObject("searchObj","CmsRepairFault.search()");
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
        modelAndView.setViewName("/CmsRepairFaultService/modelList/faultModelList");

        BaseDataResult<CmsRepairFault> results = cmsRepairFaultService.search(search, nCurPage ,5);

        modelAndView.addObject("list","searchListModel");
        modelAndView.addObject("searchValue", "'"+search+"'");
        modelAndView.addObject("method", "CmsRepairFault.loadSearchDataModel");
        modelAndView.addObject("searchObj","CmsRepairFault.modelSearch()");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;

    }


}

package com.silverbullet.cms.controller.CmsRepair;


import com.silverbullet.cms.domain.CmsRepairFault;
import com.silverbullet.cms.domain.CmsRepairFaultService;
import com.silverbullet.cms.domain.CmsRepairServiceInfo;
import com.silverbullet.cms.domain.CmsRepairUser;
import com.silverbullet.cms.service.ICmsRepairFaultService;
import com.silverbullet.cms.service.ICmsRepairFaultServiceService;
import com.silverbullet.cms.service.ICmsRepairUserService;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.utils.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/cms/cmsrepairfaultservice")
public class CmsRepairFaultServiceController {

    @Autowired
    private ICmsRepairFaultServiceService cmsRepairFaultServiceService;

    @Autowired
    private ICmsRepairUserService cmsRepairUserService;

    @Autowired
    private ICmsRepairFaultService cmsRepairFaultService;

    @RequestMapping(value = "list/pub.html", method = RequestMethod.GET)
    public ModelAndView index(){
        int nCurPage = 1;

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairFaultService/list");

        BaseDataResult<CmsRepairServiceInfo> results = cmsRepairFaultServiceService.list(nCurPage, 10);

        if(results.getResultList().isEmpty()){
            modelAndView.addObject("noData", ToolUtil.noData());
        }

        modelAndView.addObject("list","list");
        modelAndView.addObject("method", "CmsRepairService.loadData");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;

    }

    @RequestMapping(value = "userInfo.html", method = RequestMethod.POST)
    public String userInfo(Model model, String InfoId){

        CmsRepairUser cmsRepairUser = cmsRepairUserService.getOneById(InfoId);
        model.addAttribute("obj", cmsRepairUser);

        return "/CmsRepairFaultService/userInfo";
    }

    @RequestMapping(value = "faultInfo.html", method = RequestMethod.POST)
    public String faultInfo(Model model, String faultId){

        CmsRepairFault cmsRepairFault = cmsRepairFaultService.getOneById(faultId);
        model.addAttribute("obj", cmsRepairFault);

        return "/CmsRepairFaultService/faultInfo";
    }

    @RequestMapping(value = "status.html", method = RequestMethod.POST)
    public String status(Model model, String id){

        CmsRepairFaultService cmsRepairFaultService = cmsRepairFaultServiceService.findStatusById(id);
        model.addAttribute("obj", cmsRepairFaultService);

        return "/CmsRepairFaultService/model/statusModel";
    }


    @RequestMapping(value = "/list.html", method = RequestMethod.POST)
    public ModelAndView listData(String curpage){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairFaultService/listContent");

        BaseDataResult<CmsRepairServiceInfo> results = cmsRepairFaultServiceService.list(nCurPage, 10);

        if(results.getResultList().isEmpty()){
            modelAndView.addObject("noData", ToolUtil.noData());
        }

        modelAndView.addObject("list","list");
        modelAndView.addObject("method", "CmsRepairService.loadData");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }


    @RequestMapping(value = "/check.html", method = RequestMethod.POST)
    public String check(Model model, String pageName, String id){

        CmsRepairServiceInfo cmsRepairServiceInfo = cmsRepairFaultServiceService.getOneById(id);
        model.addAttribute("obj", cmsRepairServiceInfo  );
        model.addAttribute("disabled", "disabled");
        return "/CmsRepairFaultService/model/model";
    }


    @RequestMapping(value = "/add.html", method = RequestMethod.POST)
    public String add(Model model, String pageName) {

        model.addAttribute("obj", new CmsRepairServiceInfo());
        model.addAttribute("disabled", " ");

        return "/CmsRepairFaultService/model/model";
    }

    @RequestMapping(value = "/edit.html", method = RequestMethod.POST)
    public String edit(Model model, String id){

        CmsRepairServiceInfo cmsRepairServiceInfo = cmsRepairFaultServiceService.getOneById(id);

        model.addAttribute("obj", cmsRepairServiceInfo);
        model.addAttribute("disabled", " ");

        return "/CmsRepairFaultService/model/model";
    }



    @RequestMapping(value = "/userlist.html", method = RequestMethod.POST)
    public ModelAndView userList(String curpage){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairFaultService/model/userModel");

        BaseDataResult<CmsRepairUser> results = cmsRepairUserService.list(nCurPage, 5);

        if(results.getResultList().isEmpty()){
            modelAndView.addObject("noData", ToolUtil.noData());
        }

        modelAndView.addObject("list","listModel");
        modelAndView.addObject("method", "CmsRepairUser.loadDataModel");
        modelAndView.addObject("disabled", "disabled");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }


    @RequestMapping(value = "/faultlist.html", method = RequestMethod.POST)
    public ModelAndView faultList(String curpage){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairFaultService/model/faultModel");

        BaseDataResult<CmsRepairFault> results = cmsRepairFaultService.list(nCurPage, 5);

        if(results.getResultList().isEmpty()){
            modelAndView.addObject("noData", ToolUtil.noData());
        }

        modelAndView.addObject("list","listModel");
        modelAndView.addObject("method", "CmsRepairFault.loadDataModel");
        modelAndView.addObject("disabled", "disabled");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }


    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Validated({AddValidate.class}) CmsRepairFaultService cmsRepairFaultService,
                                    BindingResult result) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (result.hasErrors()) {
            mapRet.put("result", false);
            mapRet.put("errors", result.getFieldErrors());
            return mapRet;
        }

        boolean bTrue = false;
        String message = "";
        if (cmsRepairFaultService.getId().isEmpty()) {
            bTrue = cmsRepairFaultServiceService.Insert(cmsRepairFaultService);
            message = bTrue ? "添加成功！" : "添加失败！";
        }else {
            bTrue = cmsRepairFaultServiceService.Update(cmsRepairFaultService);
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

        boolean bRet = cmsRepairFaultServiceService.delete(ids);

        mapRet.put("result", bRet);
        return mapRet;
    }



    @RequestMapping(value = "/search.do", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView search(String search, String curpage){
        int nCurPage = 1;


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairFaultService/listContent");

        BaseDataResult<CmsRepairServiceInfo> results = cmsRepairFaultServiceService.search(search, nCurPage ,10);

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


    @RequestMapping(value = "/searchList.html", method = RequestMethod.POST)
    public ModelAndView searchIndex(String search, String curpage){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsRepairFaultService/listContent");

        BaseDataResult<CmsRepairServiceInfo> results = cmsRepairFaultServiceService.search(search, nCurPage ,10);

        if(results.getResultList().isEmpty()){
            modelAndView.addObject("noData", ToolUtil.noData());
        }

        modelAndView.addObject("list","searchList");
        modelAndView.addObject("searchValue", "'"+search+"'");
        modelAndView.addObject("method", "CmsRepairService.loadSearchData");
        modelAndView.addObject("searchObj","CmsRepairService.search()");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;

    }



    @RequestMapping(value = "/tree.html", method = RequestMethod.POST)
    public String tree(){
        return "CmsRepairFaultService/model/typeModel";
    }





    @RequestMapping(value = "/setStatus.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setStatus(CmsRepairFaultService cmsRepairFaultService) {
        Map<String,Object> mapRet = new HashMap<String, Object>();

        boolean bTrue = false;
        String message = "";

        bTrue = cmsRepairFaultServiceService.updateStatusById(cmsRepairFaultService);
        message = bTrue ? "设置成功！" : "设置失败！";


        mapRet.put("result", bTrue);
        mapRet.put("message", message);

        return mapRet;
    }

}

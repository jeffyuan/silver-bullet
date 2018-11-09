package com.silverbullet.controller;


import com.silverbullet.cms.domain.CmsRepairServiceInfo;
import com.silverbullet.cms.service.ICmsRepairFaultService;
import com.silverbullet.cms.service.ICmsRepairFaultServiceService;
import com.silverbullet.cms.service.ICmsRepairUserService;
import com.silverbullet.utils.BaseDataResult;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/amain/board")
public class BoardController {

    @Autowired
    private ICmsRepairFaultServiceService cmsRepairFaultServiceService;

    @Autowired
    private ICmsRepairUserService cmsRepairUserService;

    @Autowired
    private ICmsRepairFaultService cmsRepairFaultService;


    @RequestMapping(value = "/list0.html", method = RequestMethod.POST)
    public ModelAndView listData0(String curpage){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/indexList/List0");

//        BaseDataResult<CmsRepairServiceInfo> results = cmsRepairFaultServiceService.list0(nCurPage, 4);

//        modelAndView.addObject("list","list0");
//        modelAndView.addObject("method", "Board0.loadData");
//        modelAndView.addObject("results", results);
//        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }


    @RequestMapping(value = "/list1.html", method = RequestMethod.POST)
    public ModelAndView listData1(String curpage){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/indexList/List1");

        BaseDataResult<CmsRepairServiceInfo> results = cmsRepairFaultServiceService.list(nCurPage, 4);

        modelAndView.addObject("list","list1");
        modelAndView.addObject("method", "Board1.loadData");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }


    @RequestMapping(value = "/list2.html", method = RequestMethod.POST)
    public ModelAndView listData2(String curpage){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/indexList/List2");

        BaseDataResult<CmsRepairServiceInfo> results = cmsRepairFaultServiceService.list(nCurPage, 4);

        modelAndView.addObject("list","list2");
        modelAndView.addObject("method", "Board2.loadData");
        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }


    @RequestMapping(value = "/chartB.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> charB (String data) {

        Map<String, Object> mapt = new HashMap<>();
        if(data == null && data.isEmpty()){
            mapt.put("data", "数据传入出错" );
            return mapt;
        }

         mapt = cmsRepairFaultServiceService.getCharB(data);


        return mapt;
    }


    @RequestMapping(value = "/chartSZ.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> charSZ (Date data){

        Map<String, Object> mapt = new HashMap<>();
        if(data == null){
            mapt.put("data", "数据传入出错" );
            return mapt;
        }

        mapt = cmsRepairFaultServiceService.getCharSZ(data);

        return mapt;
    }

}

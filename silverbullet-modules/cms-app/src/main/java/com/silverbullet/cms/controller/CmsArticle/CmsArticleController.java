package com.silverbullet.cms.controller.CmsArticle;

import com.silverbullet.cms.domain.CmsArticle;
import com.silverbullet.cms.domain.CmsArticleContent;
import com.silverbullet.cms.domain.CmsArticleFile;
import com.silverbullet.cms.pojo.CmsArticleEntity;
import com.silverbullet.cms.pojo.CmsArticleEx;
import com.silverbullet.cms.pojo.CmsFileInfo;
import com.silverbullet.cms.service.ICmsArticleService;
import com.silverbullet.cms.service.ICmsArticleTypetreeService;
import com.silverbullet.cms.util.EntryCreateFactory;
import com.silverbullet.utils.BaseDataResult;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.utils.HtmlUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章表 控制器
 * Created by jeffyuan on 2018/2/11.
 */
@Controller
@RequestMapping(value = "/cms/cmsarticle")
public class CmsArticleController {

    @Autowired
    private ICmsArticleService cmsArticleService;
    @Autowired
    private ICmsArticleTypetreeService cmsArticleTypetreeService;

    @RequestMapping(value = "/list/pub.html", method = RequestMethod.GET)
    public ModelAndView index(){
        int nCurPage = 1;

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsArticle/list");

        BaseDataResult<CmsArticle> results = cmsArticleService.list("NEWS","", nCurPage, 5);

        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
    }

    @RequestMapping(value = "/list.html", method = RequestMethod.POST)
    public ModelAndView listData(String curpage, String typeId, String pageName){
        int nCurPage = 1;
        if (curpage != null) {
            nCurPage = Integer.valueOf(curpage);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/CmsArticle/listContent_"+pageName);

        BaseDataResult<CmsArticle> results = cmsArticleService.list("NEWS",typeId, nCurPage, 5);

        modelAndView.addObject("results", results);
        modelAndView.addObject("curPage", nCurPage);

        return modelAndView;
     }

    @RequestMapping(value = "/add.html", method = RequestMethod.POST)
    public String add(Model model, String typeId, String type, String pageName) {

        CmsArticleEx cmsArticle = new CmsArticleEx();
        cmsArticle.setModuleFilterKey(typeId);

        String ops = cmsArticleService.ops(pageName);

        model.addAttribute("obj", cmsArticle);
        model.addAttribute("type", type);
        model.addAttribute("ops",ops);

        return "/CmsArticle/model_"+pageName;
    }

    @RequestMapping(value = "/edit.html", method = RequestMethod.POST)
    public String edit(Model model, String id, String type, String pageName) {
        CmsArticleEx cmsArticle = cmsArticleService.getOneExById(id);
        String ops = cmsArticleService.ops(pageName);

        model.addAttribute("obj", cmsArticle);
        model.addAttribute("type", type);
        model.addAttribute("ops", ops);

        return "/CmsArticle/model_+pageName";
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String ids) {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (ids == null || ids.isEmpty()) {
            mapRet.put("result", false);
            return mapRet;
        }

        boolean bRet = cmsArticleService.delete(ids);

        mapRet.put("result", bRet);
        return mapRet;
    }

    @RequestMapping(value = "/save.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@Validated({AddValidate.class}) CmsArticleEx cmsArticle,
                                    BindingResult result, @RequestParam("cmsArticleImage") MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String,Object> mapRet = new HashMap<String, Object>();
        if (result.hasErrors()) {
            mapRet.put("result", false);
            mapRet.put("errors", result.getFieldErrors());
            return mapRet;
        }

        boolean bTrue = false;
        String message = "";
        if (cmsArticle.getId().isEmpty()) {

            cmsArticle.setModule("NEWS");
            CmsArticleEntity cmsArticleEntity = EntryCreateFactory.createCmsArticleEntry();

            CmsArticleContent cmsArticleContent = EntryCreateFactory.createCmsArticleContent();
            cmsArticleContent.setContHtml(cmsArticle.getContHtml());
            cmsArticleContent.setContText(HtmlUtils.removeHtmlTag(cmsArticle.getContHtml()));

            cmsArticleEntity.setCmsArticle(EntryCreateFactory.initCmsArticle(cmsArticle));
            cmsArticleEntity.setCmsArticleContent(cmsArticleContent);


            CmsFileInfo fileDis = cmsArticleService.fileDispose(file, request);
            cmsArticleEntity.setCmsArticleImage(fileDis);

            /*cmsArticleEntity.setCmsArticleImage();*/

            bTrue = cmsArticleService.createArticle(cmsArticleEntity);
            message = bTrue ? "添加成功！" : "添加失败！";
        } else {
            bTrue = cmsArticleService.Update(cmsArticle);
            message = bTrue ? "修改成功！" : "修改失败！";
        }

        mapRet.put("result", bTrue);
        mapRet.put("message", message);

        return mapRet;
    }
}

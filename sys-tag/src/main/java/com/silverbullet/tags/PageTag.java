package com.silverbullet.tags;

import com.github.pagehelper.Page;
import org.beetl.core.GeneralVarTagBinding;
import org.beetl.core.Tag;

import java.io.IOException;
import java.util.Map;

/**
 * 分页标签
 * Created by jeffyuan on 2018/3/5.
 */
public class PageTag extends GeneralVarTagBinding {
    private  long totalNum = 0;
    private  int perSize = 0;
    private int curPage = 0;
    private int pageNum = 0;

    private String action = "";
    private String loadDataFunName = "";  //加载数据的方法，否则将通过ajax方式进行加载到
    private String searchValue = "";
    private String dom = "";
    private String value1 = "";

    private void init(){
       // String tagName = (String) this.args[0];
       // Map attrs = (Map) args[1];
        totalNum = (Integer)getAttributeValue("totalNum");
        perSize = Integer.valueOf((String)getAttributeValue("perSize"));
        curPage = (Integer)getAttributeValue("curPage");
        action = (String)getAttributeValue("action");
        searchValue = (String)getAttributeValue("searchValue");
        dom = (String)getAttributeValue("dom");
        value1 = (String)getAttributeValue("value1");

        Object obj = getAttributeValue("loadDataFunName");
        if (obj != null) {
            loadDataFunName = (String)getAttributeValue("loadDataFunName");
        }

        pageNum = (int)(totalNum / perSize);
        if(pageNum * perSize < totalNum){
            pageNum++;
        }
    }

    /**
     * 非异步加载方式
     */
    private void loadNormal() {
        StringBuilder paging = new StringBuilder("");
        paging.append("<div class=\"box-footer\" style=\"padding-bottom: 0;\">");
        paging.append("<div class=\"row box-controls\">");
        paging.append("<div class=\"col-sm-5\">");
        paging.append("<div class=\"dataTables_info\">");
        paging.append("<span class=\"pageSkip\">").append("共&nbsp;").append(pageNum).append("&nbsp;页&nbsp;&nbsp;").append(totalNum).append("&nbsp;条记录</span>");
        paging.append("</div></div>");

        paging.append("<div class=\"col-sm-7\">");
        paging.append("<div class=\"pull-right\">");
        paging.append("<ul class=\"pagination\" style=\"margin:0 0 10px 0;\">");


        if(curPage > 1){
            paging.append("<li class=\"paginate_button\"><a href=\"" + getGlobalLoadDataAHref(1) + "\">首页</a></li>")
                    .append("<li class=\"paginate_button previous\"><a href=\"" + getGlobalLoadDataAHref(curPage - 1)  + "\">上页</a></li>");

            if(curPage > 4){
                paging.append("<li class=\"paginate_button disabled\"><a href=\"#\">...</a></li>");
            }

            if(curPage - 2 > 0){
                paging.append("<li class=\"paginate_button\"><a href=\"" + getGlobalLoadDataAHref(curPage - 2) + "\">")
                        .append(curPage - 2).append("</a></li>");
            }

            if(curPage - 1 > 0){
                paging.append("<li class=\"paginate_button\"><a href=\"" + getGlobalLoadDataAHref(curPage - 1) + "\">")
                        .append(curPage - 1).append("</a></i>");
            }
        } else if(curPage == 1){
            paging.append("<li class=\"paginate_button disabled\"><a href=\"#\">首页</a>")
                    .append("<li class=\"paginate_button next disabled\"><a href=\"#\">上页</a></li>");
        }


        paging.append("<li class=\"paginate_button active\"><span style=\"font-size:14px;\">").append(curPage).append("</span></li>");

        if(curPage + 1 <= pageNum){
            paging.append("<li class=\"paginate_button\"><a href=\"" + getGlobalLoadDataAHref(curPage + 1) + "\">")
                    .append(curPage + 1).append("</a></li>");
        }

        if(curPage + 2 <= pageNum){
            paging.append("<li class=\"paginate_button\"><a href=\"" + getGlobalLoadDataAHref(curPage + 2)+ "\">")
                    .append(curPage + 2).append("</a></li>");
        }

        if(pageNum - curPage > 2){
            paging.append("<li class=\"paginate_button disabled\"><a href=\"#\">...</a></li>");
        }

        if(curPage >= pageNum){
            paging.append("<li class=\"paginate_button next disabled\"><a href=\"#\">下页</a></li>")
                    .append("<li class=\"paginate_button disabled\"><a href=\"#\">最后一页</a></li>");
        } else{
            paging.append("<li class=\"paginate_button next\"><a href=\"" + getGlobalLoadDataAHref(curPage + 1) +"\">下页</a></li>")
                    .append("<li class=\"paginate_button\"><a href=\"" + getGlobalLoadDataAHref(pageNum) + "\">最后一页</a></li>");
        }

        paging.append("</ul></div></div></div></div></div>");

        try{
            ctx.byteWriter.writeString(paging.toString());
            this.doBodyRender();
            //ctx.byteWriter.writeString(paging.toString());
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取连接跳转的href
     * @param pageNum
     * @return
     */
    private String getGlobalLoadDataAHref(int pageNum) {
        if (action.isEmpty()) {
            return "";
        }

        String aHref = action;
        aHref += pageNum + ".html";

        return aHref;
    }

    /**
     * 获取异步加载数据的ajax方法字符串
     * @param pageNum
     * @return
     */
    private String getAjaxLoadDataFunTmp(int pageNum) {
        if (loadDataFunName.isEmpty()) {
            return "";
        }

        String loadDataFun = loadDataFunName;
        loadDataFun += "('"+searchValue +"','"+ action +"', " + pageNum +",'"+dom+"')";

        return loadDataFun;
    }

    /**
     * 异步加载方式
     */
    private void loadAjax() {
        StringBuilder paging = new StringBuilder("");
        paging.append("<div class=\"box-footer\" style=\"padding-bottom: 0;\">");
        paging.append("<div class=\"row box-controls\">");
        paging.append("<div class=\"col-sm-3\">");
        paging.append("<div class=\"dataTables_info\">");
        paging.append("<span class=\"pageSkip\">").append("共&nbsp;").append(pageNum).append("&nbsp;页&nbsp;&nbsp;").append(totalNum).append("&nbsp;条记录</span>");
        paging.append("</div></div>");

        paging.append("<div class=\"col-sm-9\">");
        paging.append("<div class=\"pull-right\">");
        paging.append("<ul class=\"pagination\" style=\"margin:0 0 10px 0;\">");


        if(curPage > 1){
            paging.append("<li class=\"paginate_button\"><a href=\"javascript:void(0)\" onclick=\"" + getAjaxLoadDataFunTmp(1) + "\">首页</a></li>")
                    .append("<li class=\"paginate_button previous\"><a href=\"javascript:void(0)\" onclick=\"" + getAjaxLoadDataFunTmp(curPage - 1)+ "\">上页</a></li>");

            if(curPage > 4){
                paging.append("<li class=\"paginate_button disabled\"><a href=\"#\">...</a></li>");
            }

            if(curPage - 2 > 0){
                paging.append("<li class=\"paginate_button\"><a href=\"javascript:void(0)\" onclick=\"" + getAjaxLoadDataFunTmp(curPage - 2)+"\">")
                        .append(curPage - 2).append("</a></li>");
            }

            if(curPage - 1 > 0){
                paging.append("<li class=\"paginate_button\"><a href=\"javascript:void(0)\" onclick=\"" + getAjaxLoadDataFunTmp(curPage - 1)+ "\">")
                        .append(curPage - 1).append("</a></i>");
            }
        } else if(curPage == 1){
            paging.append("<li class=\"paginate_button disabled\"><a href=\"#\">首页</a>")
                    .append("<li class=\"paginate_button next disabled\"><a href=\"#\">上页</a></li>");
        }


        paging.append("<li class=\"paginate_button active\"><span style=\"font-size:14px;\">").append(curPage).append("</span></li>");

        if(curPage + 1 <= pageNum){
            paging.append("<li class=\"paginate_button\"><a href=\"javascript:void(0)\" onclick=\"" + getAjaxLoadDataFunTmp(curPage + 1)+ "\">")
                    .append(curPage + 1).append("</a></li>");
        }

        if(curPage + 2 <= pageNum){
            paging.append("<li class=\"paginate_button\"><a href=\"javascript:void(0)\" onclick=\"" + getAjaxLoadDataFunTmp(curPage + 2)+"\">")
                    .append(curPage + 2).append("</a></li>");
        }

        if(pageNum - curPage > 2){
            paging.append("<li class=\"paginate_button disabled\"><a href=\"#\">...</a></li>");
        }

        if(curPage >= pageNum){
            paging.append("<li class=\"paginate_button next disabled\"><a href=\"#\">下页</a></li>")
                    .append("<li class=\"paginate_button disabled\"><a href=\"#\">末页</a></li>");
        } else{
            paging.append("<li class=\"paginate_button next\"><a href=\"javascript:void(0)\" onclick=\"" + getAjaxLoadDataFunTmp(curPage + 1) + "\">下页</a></li>")
                    .append("<li class=\"paginate_button\"><a href=\"javascript:void(0)\" onclick=\"" + getAjaxLoadDataFunTmp(pageNum) + "\">末页</a></li>");
        }

        paging.append("</ul></div></div></div></div></div>");

        try{
            ctx.byteWriter.writeString(paging.toString());
            this.doBodyRender();
            //ctx.byteWriter.writeString(paging.toString());
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(){
        init();

        if (loadDataFunName.isEmpty()) {
            loadNormal();
        } else {
            loadAjax();
        }
    }
}


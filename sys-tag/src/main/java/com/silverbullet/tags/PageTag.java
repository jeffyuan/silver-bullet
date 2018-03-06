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
    private static final String SPAN = "span";
    private static final String A = "a";

    private void init(){
       // String tagName = (String) this.args[0];
       // Map attrs = (Map) args[1];
        totalNum = (Integer)getAttributeValue("totalNum");
        perSize = Integer.valueOf((String)getAttributeValue("perSize"));
        curPage = (Integer)getAttributeValue("curPage");
        action = (String)getAttributeValue("action");
        pageNum = (int)(totalNum / perSize);
        if(pageNum * perSize < totalNum){
            pageNum++;
        }
    }

    /**
     * 有超链接情况
     */
    private void withAction() {
        StringBuilder paging = new StringBuilder("");
        paging.append("<div class=\"pagination\">");

        if(curPage > 1){
            paging.append("<a class=\"firstPage\" href=\"" + action + "1.html\">首页</a>")
                    .append("<a class=\"previousPage\" href=\"" + action + (curPage - 1) + ".html\">上一页</a>");

            if(curPage > 4){
                paging.append("<span class=\"pageBreak\">...</span>");
            }

            if(curPage - 2 > 0){
                paging.append("<a href=\"" + action + (curPage - 2) + ".html\">").append(curPage - 2).append("</a>");
            }

            if(curPage - 1 > 0){
                paging.append("<a href=\"" + action + (curPage - 1) + ".html\">").append(curPage - 1).append("</a>");
            }
        } else if(curPage == 1){
            paging.append("<span class=\"firstPage\"> </span>").append("<span class=\"previousPage\"> </span>");
        }


        paging.append("<span class=\"currentPage\">").append(curPage).append("</span>");
        if(curPage + 1 <= pageNum){
            paging.append("<a href=\"" + action + (curPage + 1) + ".html\">").append(curPage + 1).append("</a>");
        }

        if(curPage + 2 <= pageNum){
            paging.append("<a href=\"" + action + (curPage + 2) + ".html\">").append(curPage + 2).append("</a>");
        }

        if(pageNum - curPage > 2){
            paging.append("<span class=\"pageBreak\">...</span>");
        }

        if(curPage >= pageNum){
            paging.append("<span class=\"nextPage\" href=\"javascript:;\"> </span>")
                    .append("<span class=\"lastPage\" href=\"javascript:;\"> </span>");
        } else{
            paging.append("<a class=\"nextPage\" href=\"" + action + (curPage + 1) +".html\">下一页</a>")
                    .append("<a class=\"lastPage\" href=\"" + action + pageNum + ".html\">尾页</a>");
        }


        paging.append("<span class=\"pageSkip\">")
                .append("共").append(pageNum).append("页").append(totalNum)
//                .append("条记录 到第<input id=\"pageNumber\" name=\"pageNumber\" value=\"1\" maxlength=\"9\" onpaste=\"return false;\" />")
//                .append("页<button type=\"submit\"> </button>")
                .append("条记录</span></div>");
        try{
            ctx.byteWriter.writeString(paging.toString());
            this.doBodyRender();
            //ctx.byteWriter.writeString(paging.toString());
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 没有超链接情况
     */
    private void noAction() {
        StringBuilder paging = new StringBuilder("");
        paging.append("<div class=\"pagination\">");

        if(curPage > 1){
            paging.append("<a class=\"firstPage\"> </a>").append("<a class=\"previousPage\"> </a>");

            if(curPage > 4){
                paging.append("<span class=\"pageBreak\">...</span>");
            }

            if(curPage - 2 > 0){
                paging.append("<a href=\"javascript:;\">").append(curPage - 2).append("</a>");
            }

            if(curPage - 1 > 0){
                paging.append("<a href=\"javascript:;\">").append(curPage - 1).append("</a>");
            }
        } else if(curPage == 1){
            paging.append("<span class=\"firstPage\"> </span>").append("<span class=\"previousPage\"> </span>");
        }


        paging.append("<span class=\"currentPage\">").append(curPage).append("</span>");
        if(curPage + 1 <= pageNum){
            paging.append("<a href=\"javascript:;\">").append(curPage + 1).append("</a>");
        }

        if(curPage + 2 <= pageNum){
            paging.append("<a href=\"javascript:;\">").append(curPage + 2).append("</a>");
        }

        if(pageNum - curPage > 2){
            paging.append("<span class=\"pageBreak\">...</span>");
        }

        if(curPage >= pageNum){
            paging.append("<span class=\"nextPage\" href=\"javascript:;\"> </span>")
                    .append("<span class=\"lastPage\" href=\"javascript:;\"> </span>");
        } else{
            paging.append("<a class=\"nextPage\" href=\"javascript:;\"> </a>")
                    .append("<a class=\"lastPage\" href=\"javascript:;\"> </a>");
        }


        paging.append("<span class=\"pageSkip\">")
                .append("共").append(pageNum).append("页").append(totalNum)
//                .append("条记录 到第<input id=\"pageNumber\" name=\"pageNumber\" value=\"1\" maxlength=\"9\" onpaste=\"return false;\" />")
//                .append("页<button type=\"submit\"> </button>")
                .append("条记录</span></div>");
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

        if (action.isEmpty()) {
            noAction();
        } else {
            withAction();
        }


    }
}


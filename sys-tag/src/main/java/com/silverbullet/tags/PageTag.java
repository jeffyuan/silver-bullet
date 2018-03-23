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
        paging.append("<div class=\"box-footer no-padding\">");
        paging.append("<div class=\"row box-controls\">");
        paging.append("<div class=\"col-sm-5\">");
        paging.append("<div class=\"dataTables_info\">");
        paging.append("<span class=\"pageSkip\">").append("共&nbsp;").append(pageNum).append("&nbsp;页&nbsp;&nbsp;").append(totalNum).append("&nbsp;条记录</span>");
        paging.append("</div></div>");

        paging.append("<div class=\"col-sm-7\">");
        paging.append("<div class=\"pull-right\">");
        paging.append("<ul class=\"pagination\" style=\"margin:0 0 10px 0;\">");


        if(curPage > 1){
            paging.append("<li class=\"paginate_button\"><a href=\"" + action + "1.html\">首页</a></li>")
                    .append("<li class=\"paginate_button previous\"><a href=\"" + action + (curPage - 1) + ".html\">上一页</a></li>");

            if(curPage > 4){
                paging.append("<li class=\"paginate_button disabled\"><a href=\"#\">...</a></li>");
            }

            if(curPage - 2 > 0){
                paging.append("<li class=\"paginate_button\"><a href=\"" + action + (curPage - 2) + ".html\">")
                        .append(curPage - 2).append("</a></li>");
            }

            if(curPage - 1 > 0){
                paging.append("<li class=\"paginate_button\"><a href=\"" + action + (curPage - 1) + ".html\">")
                        .append(curPage - 1).append("</a></i>");
            }
        } else if(curPage == 1){
            paging.append("<li class=\"paginate_button disabled\"><a href=\"#\">首页</a>")
                    .append("<li class=\"paginate_button next disabled\"><a href=\"#\">下一页</a></li>");
        }


        paging.append("<li class=\"paginate_button active\"><span>").append(curPage).append("</span></li>");

        if(curPage + 1 <= pageNum){
            paging.append("<li class=\"paginate_button\"><a href=\"" + action + (curPage + 1) + ".html\">")
                    .append(curPage + 1).append("</a></li>");
        }

        if(curPage + 2 <= pageNum){
            paging.append("<li class=\"paginate_button\"><a href=\"" + action + (curPage + 2) + ".html\">")
                    .append(curPage + 2).append("</a></li>");
        }

        if(pageNum - curPage > 2){
            paging.append("<li class=\"paginate_button disabled\"><a href=\"#\">...</a></li>");
        }

        if(curPage >= pageNum){
            paging.append("<li class=\"paginate_button next disabled\"><a href=\"#\">下一页</a></li>")
                    .append("<li class=\"paginate_button disabled\"><a href=\"#\">最后一页</a></li>");
        } else{
            paging.append("<li class=\"paginate_button next\"><a href=\"" + action + (curPage + 1) +".html\">下一页</a></li>")
                    .append("<li class=\"paginate_button\"><a href=\"" + action + pageNum + ".html\">最后一页</a></li>");
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
     * 没有超链接情况
     */
    private void noAction() {
        StringBuilder paging = new StringBuilder("");paging.append("<div class=\"box-footer no-padding\">");
        paging.append("<div class=\"row box-controls\">");
        paging.append("<div class=\"col-sm-5\">");
        paging.append("<div class=\"dataTables_info\">");
        paging.append("<span class=\"pageSkip\">").append("共").append(pageNum).append("页").append(totalNum).append("条记录</span>");
        paging.append("</div></div>");

        paging.append("<div class=\"col-sm-7\">");
        paging.append("<div class=\"pull-right\">");
        paging.append("<ul class=\"pagination\" style=\"margin:0 0 10px 0;\">");

        if(curPage > 1){
            paging.append("<li class=\"paginate_button disabled\"><a href=\"#\">首页</a>")
                    .append("<li class=\"paginate_button previous disabled\"><a href=\"#\">上一页</a></li>");

            if(curPage > 4){
                paging.append("<li class=\"paginate_button disabled\"><a href=\"#\">...</a></li>");
            }

            if(curPage - 2 > 0){
                paging.append("<li class=\"paginate_button\"><a href=\"#\">").append(curPage - 2).append("</a></li>");
            }

            if(curPage - 1 > 0){
                paging.append("<li class=\"paginate_button\"><a href=\"#\">").append(curPage - 1).append("</a></li>");
            }
        } else if(curPage == 1){
            paging.append("<li class=\"paginate_button disabled\"><a href=\"#\">首页</a></li>")
                    .append("<li class=\"paginate_button previous disabled\"><a href=\"#\">下一页</a></li>");
        }


        paging.append("<li class=\"paginate_button active\"><a href=\"#\">").append(curPage).append("</a></li>");
        if(curPage + 1 <= pageNum){
            paging.append("<li class=\"paginate_button\"><a href=\"#\">").append(curPage + 1).append("</a></li>");
        }

        if(curPage + 2 <= pageNum){
            paging.append("<li class=\"paginate_button\"><a href=\"#\">").append(curPage + 2).append("</a></li>");
        }

        if(pageNum - curPage > 2){
            paging.append("<li class=\"paginate_button\"><a href=\"#\">...</a></li>");
        }

        if(curPage >= pageNum){
            paging.append("<li class=\"paginate_button next\"><a href=\"#\">下一页</span></li>")
                    .append("<li class=\"paginate_button \"><a href=\"#\">最后一页</span></li>");
        } else{
            paging.append("<li class=\"paginate_button next\"><a href=\"#\">下一页</a></li>")
                    .append("<li class=\"paginate_button\"><a href=\"#\">最后一页</a></li>");
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

        if (action.isEmpty()) {
            noAction();
        } else {
            withAction();
        }


    }
}


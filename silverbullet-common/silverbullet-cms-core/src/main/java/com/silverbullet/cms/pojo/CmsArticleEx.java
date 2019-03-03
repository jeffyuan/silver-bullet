package com.silverbullet.cms.pojo;

import com.silverbullet.cms.domain.CmsArticle;
import com.silverbullet.core.validate.AddValidate;
import com.silverbullet.core.validate.FullValidate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by jeffyuan on 2018/4/24.
 */
public class CmsArticleEx extends CmsArticle {
    // contHtml
    @NotBlank(message = "contHtml 不能为空" , groups = {FullValidate.class, AddValidate.class})
    @Size(message = "contHtml 长度不能超过4294967295", groups = {FullValidate.class, AddValidate.class})
    private String contHtml;

    public String getContHtml() {
        return contHtml;
    }

    public void setContHtml(String contHtml) {
        this.contHtml = contHtml;
    }



}

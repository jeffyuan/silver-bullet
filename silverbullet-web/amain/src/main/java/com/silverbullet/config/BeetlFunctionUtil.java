package com.silverbullet.config;

import com.silverbullet.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

/**
 * 提供给beetl模板使用的通用注册类
 * @author jeffyuan
 * @createDate 2019/2/28 15:57
 * @updateUser jeffyuan
 * @updateDate 2019/2/28 15:57
 * @updateRemark
 * @version 1.0
 */
public class BeetlFunctionUtil {
    /**
     * 对静态URL进行MD5处理
     * @param lookupPath
     * @return java.lang.String
     * @author jeffyuan
     * @createDate 2019/2/28 15:57
     * @updateUser jeffyuan
     * @updateDate 2019/2/28 15:57
     * @updateRemark
     */
    public final String getForLookupPath(String lookupPath) {
        return SpringUtil.getBean(ResourceUrlProvider.class).getForLookupPath(lookupPath);
    }
}
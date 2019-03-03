package com.silverbullet.codegenerator.beetl.config;


import java.util.ArrayList;
import java.util.List;

/**
 * Controller 信息配置
 * Created by jeffyuan on 2018/3/6.
 */
public class ControllerConfig {

    private List<String> imports; // 需要引入的包

    public ControllerConfig(ContextConfig contextConfig) {

        imports = new ArrayList<String>();

        imports.add("com." + contextConfig.getPrjPackage() + "."+ contextConfig.getModulePackage() +".domain." + contextConfig.getBizEnName());
        imports.add("com." + contextConfig.getPrjPackage() + "."+ contextConfig.getModulePackage() +".service.I" + contextConfig.getBizEnName() + "Service");
        imports.add("com." + contextConfig.getPrjPackage() + ".utils.BaseDataResult");
        imports.add("com." + contextConfig.getPrjPackage() +".core.validate.AddValidate");

        imports.add("org.springframework.beans.factory.annotation.Autowired");
        imports.add("org.springframework.stereotype.Controller");
        imports.add("org.springframework.web.bind.annotation.PathVariable");
        imports.add("org.springframework.web.bind.annotation.RequestMapping");
        imports.add("org.springframework.web.bind.annotation.RequestMethod");
        imports.add("org.springframework.ui.Model");
        imports.add("org.springframework.web.servlet.ModelAndView");
        imports.add("org.springframework.validation.BindingResult");
        imports.add("org.springframework.validation.annotation.Validated");
        imports.add("org.springframework.web.bind.annotation.PathVariable");
        imports.add("org.springframework.web.bind.annotation.ResponseBody");
        imports.add("java.util.HashMap");
        imports.add("java.util.Map");

    }

    public List<String> getImports() {
        return imports;
    }
}

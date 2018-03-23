package com.silverbullet.codegenerator.beetl.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Service 信息配置
 * Created by jeffyuan on 2018/3/6.
 */
public class ServiceConfig {
    private List<String> imports; // 需要引入的包

    public ServiceConfig(ContextConfig contextConfig) {
        imports = new ArrayList<String>();

        imports.add("com.github.pagehelper.PageHelper");
        imports.add("com." + contextConfig.getPrjPackage() + "."+ contextConfig.getModulePackage() +".dao." + contextConfig.getBizEnName() + "Mapper");
        imports.add("com." + contextConfig.getPrjPackage() + "."+ contextConfig.getModulePackage() +".domain." + contextConfig.getBizEnName());
        imports.add("com." + contextConfig.getPrjPackage() + "."+ contextConfig.getModulePackage() +".service.I" + contextConfig.getBizEnName() + "Service");
        imports.add("com." + contextConfig.getPrjPackage() + ".utils.BaseDataResult");
        imports.add("org.springframework.beans.factory.annotation.Autowired");
        imports.add("org.springframework.stereotype.Service");
        imports.add("org.springframework.transaction.annotation.Transactional");

        imports.add("com." + contextConfig.getPrjPackage() +".utils.ToolUtil");
        imports.add("com." + contextConfig.getPrjPackage() + ".core.pojo.UserInfo;");
        imports.add("org.apache.shiro.SecurityUtils");
        imports.add("org.slf4j.Logger");
        imports.add("org.slf4j.LoggerFactory");
    }


    public List<String> getImports() {
        return imports;
    }
}

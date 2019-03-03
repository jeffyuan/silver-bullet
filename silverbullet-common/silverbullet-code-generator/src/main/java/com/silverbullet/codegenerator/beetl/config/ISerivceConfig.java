package com.silverbullet.codegenerator.beetl.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Service 接口信息配置
 * Created by jeffyuan on 2018/3/6.
 */
public class ISerivceConfig {
    private List<String> imports; // 需要引入的包

    public ISerivceConfig(ContextConfig contextConfig) {
        imports = new ArrayList<String>();

        imports.add("com." + contextConfig.getPrjPackage() + "."+ contextConfig.getModulePackage() +".domain." + contextConfig.getBizEnName());
        imports.add("com." + contextConfig.getPrjPackage() + ".utils.BaseDataResult");
    }


    public List<String> getImports() {
        return imports;
    }
}

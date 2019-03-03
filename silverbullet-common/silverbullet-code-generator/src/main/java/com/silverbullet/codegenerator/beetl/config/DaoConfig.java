package com.silverbullet.codegenerator.beetl.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Dao信息配置
 * Created by jeffyuan on 2018/3/6.
 */
public class DaoConfig {

    private List<String> imports; // 需要引入的包

    public DaoConfig(ContextConfig contextConfig) {
        imports = new ArrayList<String>();

        imports.add("com." + contextConfig.getPrjPackage() + "."+ contextConfig.getModulePackage() +".domain." + contextConfig.getBizEnName());
        imports.add("com." + contextConfig.getPrjPackage() + ".data.repository.CrudRepository");
        imports.add("java.util.List");
        imports.add("java.util.Map");
    }

    public List<String> getImports() {
        return imports;
    }
}

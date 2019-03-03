package com.silverbullet.activiti.ext;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Activiti 扩展配置文件
 * @author jeffyuan
 * @date 2019-02-21
 */
@ConfigurationProperties(prefix = "spring.activiti.ext")
public class ActivitiExtendProperties {

    // 流程定义缓存数量
    private int processDefinitionCacheLimit;

    // 是否自动创建数据库
    private String databaseSchemaUpdate = "false";

    // 流程图字体配置
    private String activityFontName = "宋体";
    private String labelFontName = "宋体";

    public int getProcessDefinitionCacheLimit() {
        return processDefinitionCacheLimit;
    }

    public void setProcessDefinitionCacheLimit(int processDefinitionCacheLimit) {
        this.processDefinitionCacheLimit = processDefinitionCacheLimit;
    }

    public String getDatabaseSchemaUpdate() {
        return databaseSchemaUpdate;
    }

    public void setDatabaseSchemaUpdate(String databaseSchemaUpdate) {
        this.databaseSchemaUpdate = databaseSchemaUpdate;
    }

    public String getActivityFontName() {
        return activityFontName;
    }

    public void setActivityFontName(String activityFontName) {
        this.activityFontName = activityFontName;
    }

    public String getLabelFontName() {
        return labelFontName;
    }

    public void setLabelFontName(String labelFontName) {
        this.labelFontName = labelFontName;
    }
}

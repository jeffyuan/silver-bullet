package com.silverbullet.codegenerator.pojo;

/**
 * mybatis table 配置文件
 * Created by jeffyuan on 2018/3/6.
 */
public class TableConfig {
    private String tableName;
    private String domainObjectName ;
    private String tableDiscript;  //模块描述

    private boolean insertStatementEnabled = true;
    private boolean selectByPrimaryKeyStatementEnabled = true;
    private boolean updateByPrimaryKeyStatementEnabled = true;
    private boolean deleteByPrimaryKeyStatementEnabled = true;

    private boolean deleteByExampleStatementEnabled = false;
    private boolean countByExampleStatementEnabled = false;
    private boolean updateByExampleStatementEnabled = false;
    private boolean selectByExampleStatementEnabled = false;

    private boolean onlyMybatis = false;

    public TableConfig(String tableName, String domainObjectName, String tableDiscript, Boolean onlyMybatis) {
        this.tableName = tableName;
        this.domainObjectName = domainObjectName;
        this.tableDiscript = tableDiscript;
        this.onlyMybatis = onlyMybatis;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDomainObjectName() {
        return domainObjectName;
    }

    public void setDomainObjectName(String domainObjectName) {
        this.domainObjectName = domainObjectName;
    }

    public String getTableDiscript() {
        return tableDiscript;
    }

    public void setTableDiscript(String tableDiscript) {
        this.tableDiscript = tableDiscript;
    }

    public boolean isInsertStatementEnabled() {
        return insertStatementEnabled;
    }

    public void setInsertStatementEnabled(boolean insertStatementEnabled) {
        this.insertStatementEnabled = insertStatementEnabled;
    }

    public boolean isSelectByPrimaryKeyStatementEnabled() {
        return selectByPrimaryKeyStatementEnabled;
    }

    public void setSelectByPrimaryKeyStatementEnabled(boolean selectByPrimaryKeyStatementEnabled) {
        this.selectByPrimaryKeyStatementEnabled = selectByPrimaryKeyStatementEnabled;
    }

    public boolean isSelectByExampleStatementEnabled() {
        return selectByExampleStatementEnabled;
    }

    public void setSelectByExampleStatementEnabled(boolean selectByExampleStatementEnabled) {
        this.selectByExampleStatementEnabled = selectByExampleStatementEnabled;
    }

    public boolean isUpdateByPrimaryKeyStatementEnabled() {
        return updateByPrimaryKeyStatementEnabled;
    }

    public void setUpdateByPrimaryKeyStatementEnabled(boolean updateByPrimaryKeyStatementEnabled) {
        this.updateByPrimaryKeyStatementEnabled = updateByPrimaryKeyStatementEnabled;
    }

    public boolean isDeleteByPrimaryKeyStatementEnabled() {
        return deleteByPrimaryKeyStatementEnabled;
    }

    public void setDeleteByPrimaryKeyStatementEnabled(boolean deleteByPrimaryKeyStatementEnabled) {
        this.deleteByPrimaryKeyStatementEnabled = deleteByPrimaryKeyStatementEnabled;
    }

    public boolean isDeleteByExampleStatementEnabled() {
        return deleteByExampleStatementEnabled;
    }

    public void setDeleteByExampleStatementEnabled(boolean deleteByExampleStatementEnabled) {
        this.deleteByExampleStatementEnabled = deleteByExampleStatementEnabled;
    }

    public boolean isCountByExampleStatementEnabled() {
        return countByExampleStatementEnabled;
    }

    public void setCountByExampleStatementEnabled(boolean countByExampleStatementEnabled) {
        this.countByExampleStatementEnabled = countByExampleStatementEnabled;
    }

    public boolean isUpdateByExampleStatementEnabled() {
        return updateByExampleStatementEnabled;
    }

    public void setUpdateByExampleStatementEnabled(boolean updateByExampleStatementEnabled) {
        this.updateByExampleStatementEnabled = updateByExampleStatementEnabled;
    }

    public boolean isOnlyMybatis() {
        return onlyMybatis;
    }

    public void setOnlyMybatis(boolean onlyMybatis) {
        this.onlyMybatis = onlyMybatis;
    }
}

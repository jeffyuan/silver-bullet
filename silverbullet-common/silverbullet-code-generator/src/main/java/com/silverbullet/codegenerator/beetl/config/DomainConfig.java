package com.silverbullet.codegenerator.beetl.config;

import com.silverbullet.codegenerator.pojo.TableColumnsInfo;
import com.silverbullet.codegenerator.pojo.TableConfig;
import com.silverbullet.utils.ToolUtil;

import javax.swing.table.TableColumn;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain 信息配置
 * Created by jeffyuan on 2018/3/9.
 */
public class DomainConfig {

    private List<String> imports;  //进入包信息
    private TableConfig tableConfig;

    private TableColumnsInfo primaryKeyColumn = null;  //主键列信息
    private String columnNameWithSplit = "" ; // 列名用","进行连接
    private String columnNameMybatisSetValue = ""; //符合mybatis的value字段，例如#{"id",jdbcType="VARCHAR"},
    private String columnNameMybatisSetKey = ""; //符合mybatis的value字段，例如id = #{"id",jdbcType="VARCHAR"},

    public DomainConfig() {
        imports = new ArrayList<String>();
    }

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public void SetTableConfig(TableConfig tableConfig) {
        this.tableConfig = tableConfig;

        for (TableColumnsInfo tableColumnsInfo : this.tableConfig.getColumns()) {
            if (tableColumnsInfo.getJavaTypePackage().trim().length() > 0) {
                imports.add(tableColumnsInfo.getJavaTypePackage());
            }
        }

        ToolUtil.removeDuplicate(imports);

        imports.add("com.silverbullet.core.validate.AddValidate");
        imports.add("com.silverbullet.core.validate.FullValidate");
        imports.add("org.hibernate.validator.constraints.NotEmpty");
        imports.add("javax.validation.constraints.NotBlank");
        imports.add("javax.validation.constraints.NotNull");
        imports.add("javax.validation.constraints.Size");
    }

    public TableConfig getTableConfig() {
        return tableConfig;
    }

    public void setTableConfig(TableConfig tableConfig) {
        this.tableConfig = tableConfig;
    }


    /**
     * 返回 用于sql查找的字符串，例如id,name,age
     * @return
     */
    public String getColumnNameWithSplit() {
        if (columnNameWithSplit.isEmpty()) {
                        for (TableColumnsInfo tableColumnsInfo : this.tableConfig.getColumns()) {
                if (columnNameWithSplit.length() > 0) {
                    columnNameWithSplit += ",";
                }

                columnNameWithSplit += tableColumnsInfo.getName();
            }
        }

        return columnNameWithSplit;
    }

    /**
     * 获取column对应的value格式
     * @return
     */
    public String getColumnNameMybatisSetValue() {
        if (columnNameMybatisSetValue.isEmpty()) {
            for (TableColumnsInfo tableColumnsInfo : this.tableConfig.getColumns()) {
                if (columnNameMybatisSetValue.length() > 0) {
                    columnNameMybatisSetValue += ",";
                }

                columnNameMybatisSetValue += "#{" + tableColumnsInfo.getJavaName() +",jdbcType="+ tableColumnsInfo.getType().toUpperCase() +"}";
            }

        }

        return columnNameMybatisSetValue;
    }

    /**
     * 获取update set的模板内容
     * @return
     */
    public String getColumnNameMybatisSetKey() {

        if (columnNameMybatisSetKey.isEmpty()) {
            for (TableColumnsInfo tableColumnsInfo : this.tableConfig.getColumns()) {
                if (columnNameMybatisSetKey.length() > 0) {
                    columnNameMybatisSetKey += ",";
                }

                columnNameMybatisSetKey += tableColumnsInfo.getName() + " = #{" +
                        tableColumnsInfo.getJavaName() +",jdbcType=" + tableColumnsInfo.getType()+"}";
            }

        }

        return columnNameMybatisSetKey;
    }

    /**
     * 获取主键
     * @return
     */
    public TableColumnsInfo getPrimaryKeyColumn() {

        if (primaryKeyColumn == null) {
            for (TableColumnsInfo tableColumnsInfo : this.tableConfig.getColumns()) {
                if (tableColumnsInfo.isPrimaryKey()) {
                    primaryKeyColumn = tableColumnsInfo;
                    break;
                }
            }
        }

        return primaryKeyColumn;
    }
}

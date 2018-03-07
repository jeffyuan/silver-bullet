package com.silverbullet.codegenerator.mybatis;

import com.silverbullet.codegenerator.pojo.TableConfig;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * mybatis 对应mapper、domain生成类
 * Created by jeffyuan on 2018/3/7.
 */
public class MybatisCodeGenerator {

    private String ClasspathEntry = "";
    private String ConnectionURL = "";
    private String DriverClass = "";
    private String UserId = "";
    private String Password = "";

    public MybatisCodeGenerator(String classpathEntry, String connectionURL, String driverClass, String userId, String password) {
        this.ClasspathEntry = classpathEntry;
        this.ConnectionURL = connectionURL;
        this.DriverClass = driverClass;
        this.UserId = userId;
        this.Password = password;
    }

    /**
     * 生成Mybatic 基础文件。包括mapper、domain以及dao
     * @param fileExpPath 工程绝对路径，例如:C:\Users\GESOFT\Documents\GitHub\silver-bullet\code-generator 不用带最后路径
     * @param modelPackage 模型文件的包名; 例如com.silverbullet.domain
     * @param mappPackage  mapper文件的包名; 例如mapper.silverbullet
     * @param daoPackage   Dao文件的包名; 例如com.silverbullet.dao
     * @param bOverWrite 存在是否覆盖
     *
     */
    public void mybatisGeneratorJ(String fileExpPath, String modelPackage, String mappPackage, String daoPackage,
                                         List<TableConfig> listTables, boolean bOverWrite) {

        if (((modelPackage == null || modelPackage.trim().length() == 0)
                && (mappPackage == null || mappPackage.trim().length() == 0)
                &&  (daoPackage == null || daoPackage.trim().length() == 0))
                || listTables == null || listTables.isEmpty())  {
            System.out.println("参数错误！");
            return;
        }

        List<String> warnings = new ArrayList<String>();

        Configuration configuration = new Configuration();

        // 设置mysql驱动
        configuration.addClasspathEntry(ClasspathEntry);;

        // 设置context
        Context context = new Context(ModelType.CONDITIONAL);
        context.setId("context");
        context.setTargetRuntime("MyBatis3");

        CommentGeneratorConfiguration commentGenerator = new CommentGeneratorConfiguration();
        commentGenerator.addProperty("suppressAllComments", "false");
        commentGenerator.addProperty("suppressDate", "true");
        context.setCommentGeneratorConfiguration(commentGenerator);

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(ConnectionURL);
        jdbcConnectionConfiguration.setDriverClass(DriverClass);
        jdbcConnectionConfiguration.setUserId(UserId);
        jdbcConnectionConfiguration.setPassword(Password);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        JavaTypeResolverConfiguration javaTypeResolver = new JavaTypeResolverConfiguration();
        javaTypeResolver.addProperty("forceBigDecimals", "false");
        context.setJavaTypeResolverConfiguration(javaTypeResolver);

        if (modelPackage != null && modelPackage.length() > 0) {
            JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
            javaModelGeneratorConfiguration.setTargetPackage(modelPackage);
            javaModelGeneratorConfiguration.setTargetProject(fileExpPath + "/src/main/java");
            javaModelGeneratorConfiguration.addProperty("enableSubPackages", "false");
            javaModelGeneratorConfiguration.addProperty("trimStrings", "true");
            context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
        }

        if (mappPackage != null && mappPackage.length() > 0) {
            SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
            sqlMapGeneratorConfiguration.setTargetPackage(mappPackage);
            sqlMapGeneratorConfiguration.setTargetProject(fileExpPath + "/src/main/resources");
            sqlMapGeneratorConfiguration.addProperty("enableSubPackages", "false");
            context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
        }

        if (daoPackage != null && daoPackage.length() > 0) {
            JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
            javaClientGeneratorConfiguration.setTargetPackage(daoPackage);
            javaClientGeneratorConfiguration.setTargetProject(fileExpPath + "/src/main/java");
            javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
            javaClientGeneratorConfiguration.addProperty("enableSubPackages", "false");
            context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
        }

        for (TableConfig tableConfig : listTables) {

            TableConfiguration tableConfiguration = new TableConfiguration(context);
            tableConfiguration.setTableName(tableConfig.getTableName());
            tableConfiguration.setDomainObjectName(tableConfig.getDomainObjectName());

            tableConfiguration.setCountByExampleStatementEnabled(tableConfig.isCountByExampleStatementEnabled());
            tableConfiguration.setDeleteByExampleStatementEnabled(tableConfig.isDeleteByExampleStatementEnabled());
            tableConfiguration.setDeleteByPrimaryKeyStatementEnabled(tableConfig.isDeleteByPrimaryKeyStatementEnabled());
            tableConfiguration.setInsertStatementEnabled(tableConfig.isInsertStatementEnabled());
            tableConfiguration.setSelectByPrimaryKeyStatementEnabled(tableConfig.isSelectByPrimaryKeyStatementEnabled());
            tableConfiguration.setSelectByExampleStatementEnabled(tableConfig.isSelectByExampleStatementEnabled());
            tableConfiguration.setUpdateByExampleStatementEnabled(tableConfig.isUpdateByExampleStatementEnabled());
            tableConfiguration.setUpdateByPrimaryKeyStatementEnabled(tableConfig.isUpdateByPrimaryKeyStatementEnabled());
            context.addTableConfiguration(tableConfiguration);
        }

        configuration.addContext(context);

        DefaultShellCallback callback = new DefaultShellCallback(bOverWrite);
        MyBatisGenerator generator = null;

        try {
            generator = new MyBatisGenerator(configuration, callback, warnings);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        try {
            generator.generate(null);

            System.out.println("mybatis 代码生成成功。。。");
            for (String warning : warnings) {
                System.out.println(warning);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

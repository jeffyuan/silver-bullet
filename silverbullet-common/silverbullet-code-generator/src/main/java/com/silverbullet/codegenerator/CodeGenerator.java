package com.silverbullet.codegenerator;

import com.silverbullet.codegenerator.beetl.JavaCodeGenerator;
import com.silverbullet.codegenerator.mybatis.MybatisCodeGenerator;
import com.silverbullet.codegenerator.mybatis.MysqlTableService;
import com.silverbullet.codegenerator.pojo.TableConfig;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 代码自动生成主框架类
 */
public class CodeGenerator
{
    //数据库配置路径
    private String ClasspathEntry = "C:\\Users\\GESOFT\\.m2\\repository\\mysql\\mysql-connector-java\\5.1.29\\mysql-connector-java-5.1.29.jar";
    private String DriverClass = "com.mysql.jdbc.Driver";
    private String ConnectionURL = "jdbc:mysql://localhost:3306/silverbullet?useUnicode=true&characterEncoding=utf-8";
    private String DatabaseName = "silverbullet";
    private String UserId = "tkrobot";
    private String Password = "tkrobot";

    /**
     * 通过mybatis codegenerator 生成xmlmapper和domain方式
     */
    public void codeGeneratorWithMybatisCodeGenerator() {
        //----------------------------------------------模块信息配置（重点配置此）-------------------------------------
        String prjPackage = "silverbullet";  // 工程的名称，一般为com下面的包名，例如com.silverbullet
        String modulePackage = "auth";       // 模块名称，一般为com.silverbullet下面的子包名，例如com.silverbullet.auth
        String moduleName = "sys-auth";      // 子工程名称
        String moduleDir = "silverbullet-modules";

        // 填写业务表的信息
        List<TableConfig> listTables = new ArrayList<TableConfig>();
        /*listTables.add(new TableConfig("sys_auth_user", "SysAuthUser", "用户管理", false));//表名，domain类名，用途
        listTables.add(new TableConfig("sys_auth_organization", "SysAuthOrganization", "组织机构管理", false));
        listTables.add(new TableConfig("sys_auth_action", "SysAuthAction", "功能权限", false));
        listTables.add(new TableConfig("sys_auth_actiontree", "SysAuthActionTree", "权限定义", false));
        listTables.add(new TableConfig("sys_auth_post", "SysAuthPost", "机构管理角色", false));

        listTables.add(new TableConfig("sys_auth_userorg", "SysAuthUserOrg", "用户对应组织机构", true));
        listTables.add(new TableConfig("sys_auth_postaction", "SysAuthPostAction", "角色权限表定义",true));*/
        listTables.add(new TableConfig("sys_auth_userpost", "SysAuthUserPost", "用户对应角色",true));

        String path = System.getProperty("user.dir");
        // controller、serivce、dao文件生成的路径
        String prjAbsolutePath = path +  File.separator + moduleDir + File.separator + moduleName;
        // mapper.xml文件以及html资源文件的路径
        String prjResAbsolutePath = path + File.separator + moduleDir + File.separator + moduleName;

        String navigationType = "local";  //页面加载方式 local：局部加载  global：页面跳转
        //----------------------------------------------开始生成-----------------------------------------
        // 生成mybatis的xml文件和domain文件
        MybatisCodeGenerator mybatisCodeGenerator = new MybatisCodeGenerator(ClasspathEntry, ConnectionURL,
                DriverClass, UserId, Password);
        mybatisCodeGenerator.mybatisGeneratorJ(prjAbsolutePath, "com." + prjPackage + "." + modulePackage +  ".domain",
                "mybatis.mapper", "", listTables, true);


        // 根据每个domain生成自己的管理代码
        for (TableConfig tableConfig : listTables) {

            JavaCodeGenerator javaCodeGenerator = new JavaCodeGenerator(prjAbsolutePath, prjResAbsolutePath,
                    prjPackage, modulePackage, tableConfig.getTableDiscript(), tableConfig.getDomainObjectName(), navigationType);

            javaCodeGenerator.beetlGeneratorDao();
            if (!tableConfig.isOnlyMybatis()) {
                javaCodeGenerator.beetlGeneratorISerivce();
                javaCodeGenerator.beetlGeneratorSerivce();
                javaCodeGenerator.beetlGeneratorController();
                javaCodeGenerator.beetlGeneratorHtml();
            }
        }

        System.out.println("代码生成完成......");
        //----------------------------------------------结束----------------------------------------------
        System.out.println("需要进行以下操作：");
        System.out.println("1. 修改mapper 文件namespace,对应正确的mapper，例如com.silverbullet.auth.dao.xxx");
        System.out.println("2. 在mapper 文件增加findList方法，返回所有字段一般select * from xxx表即可");
        System.out.println("3. 在mapper 文件增加countNum方法，返回所有字段一般select count(*) from xxx表即可");
        System.out.println("注: 2,3步骤对应的dao已经具备有，不用再添加");
    }

    /**
     * 按照模板生成代码
     */
    public void codeGeneratorOwn() {
        //----------------------------------------------模块信息配置（重点配置此）-------------------------------------
        String moduleName = "ztest";      // 子工程名称
        String prjPackage = "silverbullet";  // 工程的名称，一般为com下面的包名，例如com.silverbullet
        String modulePackage = "ztest";       // 模块名称，一般为com.silverbullet下面的子包名，例如com.silverbullet.auth
        String moduleDir = "silverbullet-modules";

        // 填写业务表的信息
        List<TableConfig> listTables = new ArrayList<TableConfig>();
        listTables.add(new TableConfig("test2", "SysTest2", "文章类别", false));//表名，domain类名，用途
//        listTables.add(new TableConfig("cms_article_typetree", "CmsArticleTypetree", "文章类别", false));//表名，domain类名，用途
//        listTables.add(new TableConfig("cms_article", "CmsArticle", "文章表", false));//表名，domain类名，用途
//        listTables.add(new TableConfig("cms_article_comment", "CmsArticleComment", "文章评论", false));
//
//        listTables.add(new TableConfig("cms_article_run_behavior", "CmsArticleRunBehavior", "文章在线访问行为", true));
//        listTables.add(new TableConfig("cms_article_content", "CmsArticleContent", "在线文章内容", true));
//        listTables.add(new TableConfig("cms_article_comment_reply", "CmsArticleCommentReply", "文章评论回复", true));
//        listTables.add(new TableConfig("cms_article_file", "CmsArticleFile", "文章中附件", true));
//        listTables.add(new TableConfig("cms_article_file_history", "CmsArticleFileHistory", "文章附件历史", true));
//        listTables.add(new TableConfig("cms_rf_article_file", "CmsRfArticleFile", "文章附件表", true));

        String path = System.getProperty("user.dir");
        // controller、serivce、dao文件生成的路径
        String prjAbsolutePath = path +  File.separator + moduleDir + File.separator + moduleName;
        // mapper.xml文件以及html资源文件的路径
        String prjResAbsolutePath = path + File.separator + moduleDir + File.separator + moduleName;

        String navigationType = "local";  //页面加载方式 local：局部加载  global：页面跳转
        //----------------------------------------------开始生成-----------------------------------------

        // 根据表获取表相关信息
        MysqlTableService mysqlTableService = new MysqlTableService(DriverClass, ConnectionURL, DatabaseName, UserId, Password);
        try {
            mysqlTableService.getTableInfo(listTables);
            // 和java Domain的对应关系
            mysqlTableService.table2java(listTables);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 根据每个domain生成自己的管理代码
        for (TableConfig tableConfig : listTables) {

            JavaCodeGenerator javaCodeGenerator = new JavaCodeGenerator(prjAbsolutePath, prjResAbsolutePath,
                    prjPackage, modulePackage, tableConfig.getTableDiscript(), tableConfig.getDomainObjectName(), navigationType);

            javaCodeGenerator.beetlGeneratorXMLMapper(tableConfig);
            javaCodeGenerator.beetlGeneratorDomain(tableConfig);
            javaCodeGenerator.beetlGeneratorDao();

            if (!tableConfig.isOnlyMybatis()) {
                javaCodeGenerator.beetlGeneratorISerivce();
                javaCodeGenerator.beetlGeneratorSerivce();
                javaCodeGenerator.beetlGeneratorHtml(tableConfig);
                javaCodeGenerator.beetlGeneratorController();
            }
        }

        System.out.println("代码生成完成......");
    }

    public static void main( String[] args ) {

        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.codeGeneratorOwn();
    }

}

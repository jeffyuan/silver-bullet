package com.silverbullet.codegenerator;

import com.silverbullet.codegenerator.beetl.JavaCodeGenerator;
import com.silverbullet.codegenerator.mybatis.MybatisCodeGenerator;
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
    public static void main( String[] args ) {
        //数据库配置路径
        String ClasspathEntry = "C:\\Users\\GESOFT\\.m2\\repository\\mysql\\mysql-connector-java\\5.1.29\\mysql-connector-java-5.1.29.jar";
        String ConnectionURL = "jdbc:mysql://localhost:3306/silverbullet?useUnicode=true&characterEncoding=utf-8";
        String DriverClass = "com.mysql.jdbc.Driver";
        String UserId = "tkrobot";
        String Password = "tkrobot";

        //----------------------------------------------模块信息配置（重点配置此）-------------------------------------
        String prjPackage = "silverbullet";  // 工程的名称，一般为com下面的包名，例如com.silverbullet
        String modulePackage = "auth";       // 模块名称，一般为com.silverbullet下面的子包名，例如com.silverbullet.auth
        String moduleName = "sys-auth";      // 子工程名称

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
        String prjAbsolutePath = path +  File.separator + moduleName;
        // mapper.xml文件以及html资源文件的路径
        String prjResAbsolutePath = path + File.separator + moduleName;
        //----------------------------------------------开始生成-----------------------------------------
        // 生成mybatis的xml文件和domain文件
        MybatisCodeGenerator mybatisCodeGenerator = new MybatisCodeGenerator(ClasspathEntry, ConnectionURL,
                DriverClass, UserId, Password);
        mybatisCodeGenerator.mybatisGeneratorJ(prjAbsolutePath, "com." + prjPackage + "." + modulePackage +  ".domain",
                "mybatis.mapper", "", listTables, true);


        // 根据每个domain生成自己的管理代码
        for (TableConfig tableConfig : listTables) {

            JavaCodeGenerator javaCodeGenerator = new JavaCodeGenerator(prjAbsolutePath, prjResAbsolutePath,
                    prjPackage, modulePackage, tableConfig.getTableDiscript(), tableConfig.getDomainObjectName());

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
}

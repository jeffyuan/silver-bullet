package com.silverbullet.codegenerator.beetl;

import com.silverbullet.codegenerator.beetl.config.*;
import com.silverbullet.codegenerator.pojo.TableColumnsInfo;
import com.silverbullet.codegenerator.pojo.TableConfig;
import com.silverbullet.utils.ToolUtil;
import com.sun.javafx.PlatformUtil;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * controller、service、dao、view代码自动生成类
 * Created by jeffyuan on 2018/3/6.
 */
public class JavaCodeGenerator {

    private String prjAbsolutePath = ""; //保存controller，service,等的工程路径
    private String prjResAbsolutePath = ""; //存放资源文件的工程路径

    private ContextConfig contextConfig;
    private ControllerConfig controllerConfig;
    private ISerivceConfig iSerivceConfig;
    private ServiceConfig serviceConfig;
    private DaoConfig daoConfig;
    private DomainConfig domainConfig;
    private HtmlConfig htmlConfig;

    private GroupTemplate groupTemplate;

    public JavaCodeGenerator(String prjAbsolutePath, String prjResAbsolutePath,
                             String prjPackage, String modulePackage, String bizChName, String bizEnName) {

        this.prjAbsolutePath = prjAbsolutePath + File.separator + "src" + File.separator + "main" + File.separator + "java";
        this.prjResAbsolutePath = prjResAbsolutePath + File.separator + "src" + File.separator + "main" + File.separator + "resources";

        contextConfig = new ContextConfig();
        contextConfig.setPrjPackage(prjPackage);
        contextConfig.setModulePackage(modulePackage);
        contextConfig.setBizChName(bizChName);
        contextConfig.setBizEnName(bizEnName);

        controllerConfig = new ControllerConfig(contextConfig);
        iSerivceConfig = new ISerivceConfig(contextConfig);
        serviceConfig = new ServiceConfig(contextConfig);
        daoConfig = new DaoConfig(contextConfig);
        domainConfig = new DomainConfig();
        htmlConfig = new HtmlConfig();

        initBeetlEngine();
    }

    protected void initBeetlEngine() {
        Properties properties = new Properties();
        properties.put("RESOURCE.root", "");
        properties.put("DELIMITER_STATEMENT_START", "<%");
        properties.put("DELIMITER_STATEMENT_END", "%>");
        properties.put("HTML_TAG_FLAG", "#");
        Configuration cfg = null;

        try {
            cfg = new Configuration(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
        groupTemplate = new GroupTemplate(resourceLoader, cfg);

    }

    protected void configTemplate(Template template) {
        template.binding("controller", controllerConfig);
        template.binding("context", contextConfig);
        template.binding("dao", daoConfig);
        template.binding("service", serviceConfig);
        template.binding("iservice", iSerivceConfig);
        template.binding("html", htmlConfig);
        template.binding("domain", domainConfig);
    }

    /**
     * 根据模块生成controller, service, 以及浏览、增加、删除和修改页面
     */
    private void beetlGenerator(String template, String filePath) throws IOException {

        Template pageTemplate = groupTemplate.getTemplate(template);
        configTemplate(pageTemplate);

        if (PlatformUtil.isWindows()) {
            filePath = filePath.replaceAll("/+|\\\\+", "\\\\");
        } else {
            filePath = filePath.replaceAll("/+|\\\\+", "/");
        }

        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            pageTemplate.renderTo(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成xmlMapper文件
     * @param tableConfig
     */
    public void beetlGeneratorXMLMapper(TableConfig tableConfig) {
        try {
            // 设置import包 和 columns信息
            domainConfig.SetTableConfig(tableConfig);

            beetlGenerator("/template/XmlMapper.xml.btl",
                    prjResAbsolutePath + File.separator + "mybatis" +
                            File.separator + "mapper" + File.separator + contextConfig.getBizEnName() + "Mapper.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成domain
     */
    public void beetlGeneratorDomain(TableConfig tableConfig) {
        try {
            // 设置import包 和 columns信息
            domainConfig.SetTableConfig(tableConfig);

            beetlGenerator("/template/Domain.java.btl",
                    prjAbsolutePath + File.separator + "com" +
                            File.separator + contextConfig.getPrjPackage() + File.separator + contextConfig.getModulePackage() +
                            File.separator + "domain" + File.separator + contextConfig.getBizEnName() + ".java");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成Dao接口
     */
    public void beetlGeneratorDao() {
        try {
            beetlGenerator("/template/Dao.java.btl",
                    prjAbsolutePath + File.separator + "com" +
                            File.separator + contextConfig.getPrjPackage() + File.separator + contextConfig.getModulePackage() +
                            File.separator + "dao" + File.separator + contextConfig.getBizEnName() + "Mapper.java");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成Service的接口
     */
    public void beetlGeneratorISerivce() {
        try {
            beetlGenerator("/template/IService.java.btl",
                    prjAbsolutePath + File.separator + "com" +
                            File.separator + contextConfig.getPrjPackage() + File.separator + contextConfig.getModulePackage() +
                            File.separator + "service" + File.separator + "I" + contextConfig.getBizEnName() + "Service.java");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成Service的实现
     */
    public void beetlGeneratorSerivce() {
        try {
            beetlGenerator("/template/Service.java.btl",
                    prjAbsolutePath + File.separator + "com" +
                            File.separator + contextConfig.getPrjPackage() + File.separator + contextConfig.getModulePackage() +
                            File.separator + "service" + File.separator + "impl" + File.separator + contextConfig.getBizEnName() + "Service.java");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成controller模板
     */
    public void beetlGeneratorController() {
        try {
            beetlGenerator("/template/Controller.java.btl",
                    prjAbsolutePath + File.separator + "com" +
                            File.separator + contextConfig.getPrjPackage() + File.separator + contextConfig.getModulePackage() +
                            File.separator + "controller" + File.separator + contextConfig.getBizEnName() + "Controller.java");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成html
     * @param tableConfig
     */
    public void beetlGeneratorHtml(TableConfig tableConfig) {
        try {
            List<String> tableNo = new ArrayList<String>();
            for (TableColumnsInfo tableColumnsInfo : tableConfig.getColumns()) {
                if (!tableColumnsInfo.isPrimaryKey()) {
                    tableNo.add(tableColumnsInfo.getJavaName());
                }
            }
            htmlConfig.setTableNo(tableNo);

            // 生成列表
            beetlGenerator("/template/view/list.html.btl",
                    prjResAbsolutePath + File.separator + "WEB-INF" +
                            File.separator + "views" + File.separator + contextConfig.getBizEnName() +
                            File.separator + "list.html");

            // 生成model.html
            beetlGenerator("/template/view/model.html.btl",
                    prjResAbsolutePath + File.separator + "WEB-INF" +
                            File.separator + "views" + File.separator + contextConfig.getBizEnName() +
                            File.separator + "model.html");

            // 生成javascript
            beetlGenerator("/template/view/model.js.btl",
                    prjResAbsolutePath + File.separator + "static" +
                            File.separator + contextConfig.getPrjPackage() + File.separator + "js" + File.separator +
                            contextConfig.getPrjPackage() + "-" + contextConfig.getModulePackage() + ".js");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成html
     */
    public void beetlGeneratorHtml() {
        try {

             //需要编译类，并获取变量
            String packName = "com." + contextConfig.getPrjPackage() + "." + contextConfig.getModulePackage() + ".domain." + contextConfig.getBizEnName();
            htmlConfig.setTableNo(ToolUtil.getClassFields(prjAbsolutePath, packName));

            // 生成列表
            beetlGenerator("/template/view/list.html.btl",
                    prjResAbsolutePath + File.separator + "WEB-INF" +
                            File.separator + "views" + File.separator + contextConfig.getBizEnName() +
                            File.separator + "list.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main (String [] args) {
            /*String path = System.getProperty("user.dir");
            String prjAbsolutePath = path +  File.separator + "code-generator";
            String prjResAbsolutePath = path + File.separator + "code-generator";

            String prjPackage = "silverbullet";
            String modulePackage = "auth";
            String bizEnName = "SysAuthUser";
            String bizChName = "用户管理";

            JavaCodeGenerator javaCodeGenerator = new JavaCodeGenerator(prjAbsolutePath, prjResAbsolutePath,
                    prjPackage, modulePackage, bizChName, bizEnName);
             javaCodeGenerator.beetlGeneratorController();*/

            /*String path = System.getProperty("user.dir");
            String tmpPath = path + File.separator + "code-generator/src/main/resources/template";
            FileResourceLoader resourceLoader = new FileResourceLoader(tmpPath,"utf-8");
            Configuration cfg = Configuration.defaultConfiguration();
            GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
            Template t = gt.getTemplate("Controller.java.btl");
            t.binding("name", "beetl");
            String str = t.render();
            System.out.println(str);*/

          /*  Properties properties = new Properties();
            properties.put("RESOURCE.root", "");
            properties.put("DELIMITER_STATEMENT_START", "<%");
            properties.put("DELIMITER_STATEMENT_END", "%>");
            properties.put("HTML_TAG_FLAG", "#");
            Configuration cfg = null;

            try {
                cfg = new Configuration(properties);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();

           // Configuration cfg = Configuration.defaultConfiguration();
            GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
            Template t = gt.getTemplate("/template/Controller.java.btl");
            t.binding("name", "beetl");
            String str = t.render();
            System.out.println(str);*/

    }
}

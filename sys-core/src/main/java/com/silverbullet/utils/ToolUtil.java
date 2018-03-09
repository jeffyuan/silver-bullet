package com.silverbullet.utils;

import com.sun.javafx.PlatformUtil;
import com.sun.prism.PixelFormat;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工具类
 * Created by jeffyuan on 2018/3/6.
 */
public class ToolUtil {

    /**
     * 格式化时间
     * @param dateTime
     * @return
     */
    public static String formatDate(Date dateTime) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sf.format(dateTime);
    }

    /**
     * 首字母转小写
     * @param cont 英文
     * @return
     */
    public static String formatStringCapLower(String cont) {
        if(Character.isLowerCase(cont.charAt(0)))
            return cont;
        else
            return (new StringBuilder()).append(Character.toLowerCase(cont.charAt(0))).append(cont.substring(1)).toString();
    }

    /**
     * 获取某个类的变量名称
     * @param fileRootPath 类的跟目录，例如c://工程名称//src//main//java
     * @param packageName 类的包名，例如：com.silverbullet.auth.domain.SysAuthUser
     * @return
     */
    public static List<String> getClassFields(String fileRootPath, String packageName) {
        try {

            String url = fileRootPath;
            String fileSeparator = "/";
            if (PlatformUtil.isWindows()) {
                url = "file:/" + url + File.separator;
                fileSeparator = "\\\\";
            }

            String javaPath = fileRootPath + File.separator + packageName.replaceAll("\\.", fileSeparator);
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            int compilationResult = compiler.run(null, null, null, javaPath  + ".java");

            // 使用URLClassLoader加载class到内存
            URL[] urls = new URL[] { new URL(url) };
            URLClassLoader cLoader = new URLClassLoader(urls);
            Class<?> c = cLoader.loadClass(packageName);

            List<String> listFields = new ArrayList<String>();

            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                listFields.add(field.getName());
            }

            cLoader.close();

            File file = new File(javaPath  + ".class");
            if (file.exists()) {
                file.delete();
            }

            return listFields;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void main(String [] args) {
        System.out.println(ToolUtil.formatStringCapLower("SysAuthUser"));

        String javaPath = "C:\\Users\\GESOFT\\Documents\\GitHub\\silver-bullet\\sys-auth\\src\\main\\java";
        String packName = "com.silverbullet.auth.domain.SysAuthUser";
        System.out.println(ToolUtil.getClassFields(javaPath,packName));
    }
}

package com.silverbullet.utils;

import com.sun.javafx.PlatformUtil;
import com.sun.prism.PixelFormat;

import javax.servlet.http.HttpServletRequest;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    /**
     * 返回请求的IP地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }

    /**
     * 获取url的相对地址
     * @param httpUrl  http地址 例如:http://xxxx:port/a/auth
     * @return
     */
    public static String getRelativeUrl(String httpUrl) {
        if (httpUrl.indexOf("http://") == -1 || httpUrl.length() <= 8) {
            return httpUrl;
        }

        return httpUrl.substring(httpUrl.indexOf('/', 8));
    }

    /**
     * 获取数据库的UUID
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }

    public static void main(String [] args) {
        System.out.println(ToolUtil.formatStringCapLower("SysAuthUser"));

        String javaPath = "C:\\Users\\GESOFT\\Documents\\GitHub\\silver-bullet\\sys-auth\\src\\main\\java";
        String packName = "com.silverbullet.auth.domain.SysAuthUser";
        System.out.println(ToolUtil.getClassFields(javaPath,packName));
    }
}

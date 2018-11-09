package com.silverbullet.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.javafx.PlatformUtil;
import com.sun.prism.PixelFormat;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.springframework.boot.jackson.JsonObjectSerializer;

import javax.servlet.http.HttpServletRequest;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.alibaba.druid.sql.dialect.mysql.ast.clause.MySqlFormatName.JSON;

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


    /**
     * 获取密码
     * @param num
     * @param saltVal
     * @param pwd
     * @param pwdType
     * @return
     */
    public static String getPassword(int num,String saltVal,String pwd,String pwdType){
        int hashIterations = num;//加密的次数
        Object salt = saltVal;//盐值
        Object credentials = pwd;//密码
        String hashAlgorithmName = pwdType;//加密方式
        Object simpleHash = new SimpleHash(hashAlgorithmName, credentials,
                salt, hashIterations);
        return simpleHash.toString();
    }

    /**
     * 将“/”转换成“:”
     * 如果有.html 则删除
     * 例如 输入：auth/sys/1.html
     *     输出：auth:sys
     * @param url
     * @return
     */
    public static String getPermission(String url){
        if(url == null && "".equals(url.trim())){
            return ":";
        }
        StringBuffer sb = new StringBuffer("");
        String  s[] = url.split("/");
        if(s[s.length-1].contains(".")){
            for(int i = 0;i<s.length-1;i++){
                sb.append(s[i]);
                sb.append(":");
            }
        }else{
            return url.replace("/",":");
        }
        return sb.substring(0,sb.length()-1).trim().toString();
    }

    public static void main(String [] args) {
//        System.out.println(ToolUtil.formatStringCapLower("SysAuthUser"));
//
//        String javaPath = "C:\\Users\\GESOFT\\Documents\\GitHub\\silver-bullet\\sys-auth\\src\\main\\java";
//        String packName = "com.silverbullet.auth.domain.SysAuthUser";
//        System.out.println(ToolUtil.getClassFields(javaPath,packName));
        String s = getPermission("sys/auth/list");
        System.out.println(s);
    }


    /**
     * 搜索数据格式化为map
     * @param data
     * @return
     */
    public static Map<String, String> searchJsonData(String data){
        Map<String, String> map = new HashMap<>();
        String x[] = data.split(":");
        for(int i = 0; x.length > i; i++){
            if(x.length == 1){

            }else{
                map.put(x[0], x[1]);
            }

        }
        return map;
    }



    public static List<String> searchJsonList(String data){
        List<String> list = new ArrayList<>();
        String x[] = data.split("&");
        for(int i = 0; x.length > i; i++){
            String y[] = x[i].split(":");
            if(y.length == 1){
                list.add(" ");
            }else{
                list.add(y[i]);
            }
        }

        return list;
    }



    /**
     * 无数据信息返回内容
     * @return
     */
    public static Map<String, String> noData() {

        Map<String, String> noData = new HashMap<>();
        noData.put("icon", "fa fa fa-warning");
        noData.put("text", "无相关数据");

        return noData;
    }



    /**
     * yyyy-mm-nn中文化
     * @return
     */
    public static String Datazw (String data){
        String e[] = data.split("-");
        return e[0]+"年"+e[1]+"月"+e[2]+"日";
    }


    /**
     * mm-nn中文化
     * @return
     */
    public static String Datamn (String data){
        String e[] = data.split("-");
        return e[2]+"日";
    }




    /**
     * 饼状图数据结构构建方法
     * @param dataList
     * @return
     */
    public static Map<String, Object> ChartsBData (List<String> dataList, String data) {

        Map<String, Object> option = new HashMap<>();
        Map<String, Object> title = new HashMap<>();
        Map<String, Object> tooltip = new HashMap<>();
        List<Object> series = new ArrayList<>();
        Map seriesMap = new HashMap<>();
        List<Object> datalist  = new ArrayList<>();
        Map data0 = new HashMap<>();
        Map data1 = new HashMap<>();
        Map data2 = new HashMap<>();
        Map itemStyleMap = new HashMap<>();
        List<String> center = new ArrayList<>();
        Map<String, String> itemStyleDatasuccess = new HashMap<>();
        Map<String, String> itemStyleDatawarning = new HashMap<>();
        Map<String, String> itemStyleDatadanger = new HashMap<>();


        itemStyleDatasuccess.put("color", "#008d4c");
        itemStyleDatawarning.put("color", "#f39c12");
        itemStyleDatadanger.put("color", "#dd4b39");
        title.put("text", Datazw(data)+"  业务状态占比");
        title.put("x", "center");
        title.put("top", 20);
        tooltip.put("trigger", "item");
        tooltip.put("formatter", "{a} <br/>{b} : {c} ({d}%)");
        seriesMap.put("name", "状态占比");
        seriesMap.put("type", "pie");
        seriesMap.put("radius", "55%");
        seriesMap.put("animationType", "scale");
        seriesMap.put("animationEasing", "elasticOut");
        center.add("50%");
        center.add("50%");
        seriesMap.put("center", center);
        data0.put("value", dataList.get(0));
        data0.put("name", "未开始");
        data0.put("itemStyle", itemStyleDatadanger);
        data1.put("value", dataList.get(1));
        data1.put("name", "进行中");
        data1.put("itemStyle", itemStyleDatawarning);
        data2.put("value", dataList.get(2));
        data2.put("name", "已结束");
        data2.put("itemStyle", itemStyleDatasuccess);
        datalist.add(data0);
        datalist.add(data1);
        datalist.add(data2);
        seriesMap.put("data", datalist);
        itemStyleMap.put("shadowBlur", 100);
        itemStyleMap.put("shadowOffsetX", 0);
        itemStyleMap.put("shadowOffsetY", 0);
        itemStyleMap.put("shadowColor", "rgba(0, 0, 0, .5)");
        seriesMap.put("itemStyle", itemStyleMap);
        series.add(seriesMap);
        option.put("title", title);
        option.put("tooltip", tooltip);
        option.put("series", series);

        return option;
    }


    /**
     * 根据周获取当前周所有日
     * @param date
     * @return
     */
    public static List<String> getDateByWeek(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Integer weekNow = calendar.get(Calendar.DAY_OF_WEEK)-1;
        List<String> dateList = new ArrayList<>();
        calendar.add(Calendar.DATE, +1);

        for(int i=1;i<=7; i++){
            calendar.add(Calendar.DATE, -1);
            dateList.add(String.valueOf(calendar.get(Calendar.YEAR))+'-'+String.valueOf(calendar.get(Calendar.MONTH)+1)+'-'+String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        }

        return dateList;
    }


    /**
     * 饼状图数据结构构建方法
     * @param dateList
     * @param week
     * @return
     */
    public static Map<String, Object> ChartsSZData(List<List<String>> dateList, List<String> week){
        Map<String, Object> option = new HashMap<>();
        Map<String, Object> title = new HashMap<>();
        Map<String, Object> xAxis = new HashMap<>();
        Map<String, Object> yAxis = new HashMap<>();
        Map<String, Object> tooltip = new HashMap<>();
        Map<String, Object> grid = new HashMap<>();
        List<Map> series = new ArrayList<>();
        List<String> weekName = new ArrayList<>();
        Map<String, Object> axisPointer = new HashMap<>();
        Map<String, Object> serieData1 = new HashMap<>();
        Map<String, Object> serieData2 = new HashMap<>();
        Map<String, Object> serieData3 = new HashMap<>();
        Map<String, Object> itemStyle1 = new HashMap<>();
        Map<String, Object> itemStyle2 = new HashMap<>();
        Map<String, Object> itemStyle3 = new HashMap<>();
        List<String> data1 = new ArrayList<>();
        List<String> data2 = new ArrayList<>();
        List<String> data3 = new ArrayList<>();

        for(int i=0;i<dateList.size();i++){
            data1.add(dateList.get(i).get(0));
            data2.add(dateList.get(i).get(1));
            data3.add(dateList.get(i).get(2));
        }


        itemStyle1.put("color", "#d33724");
        itemStyle1.put("shadowBlur",100);
        itemStyle1.put("shadowColor", "rgba(0, 0, 0, .3)");
        itemStyle2.put("color", "#db8b0b");
        itemStyle2.put("shadowBlur", 100);
        itemStyle2.put("shadowColor", "rgba(0, 0, 0, .3)");
        itemStyle3.put("color", "#008d4c");
        itemStyle3.put("shadowBlur", 100);
        itemStyle3.put("shadowColor", "rgba(0, 0, 0, .3)");

        serieData1.put("name", "未完成");
        serieData1.put("type", "bar");
        serieData1.put("data", data1);
        serieData1.put("itemStyle", itemStyle1);
        serieData2.put("name", "进行中");
        serieData2.put("type", "bar");
        serieData2.put("data", data2);
        serieData2.put("itemStyle", itemStyle2);
        serieData3.put("name", "已完成");
        serieData3.put("type", "bar");
        serieData3.put("data", data3);
        serieData3.put("itemStyle", itemStyle3);
        series.add(serieData1);
        series.add(serieData2);
        series.add(serieData3);


        title.put("top", 20);
        title.put("text", "近七天业务状态");
        title.put("x", "center");
        for(int i=0;i<week.size();i++){
            weekName.add(Datamn(week.get(i)));
        }
        axisPointer.put("type", "shadow");
        xAxis.put("type", "category");
        xAxis.put("data", weekName);
        yAxis.put("type", "value");
        tooltip.put("trigger", "axis");
        tooltip.put("axisPointer", axisPointer);
        grid.put("left", "3%");
        grid.put("right", "4%");
        grid.put("containLabel", true);



        option.put("title", title);
        option.put("xAxis",xAxis);
        option.put("yAxis",yAxis);
        option.put("tooltip",tooltip);
        option.put("grid", grid);
        option.put("series", series);


        return option;
    }


}

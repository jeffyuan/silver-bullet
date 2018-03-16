package com.silverbullet.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.silverbullet.utils.ToolUtil;
import org.slf4j.MDC;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * LOG -> DB appender 类
 * Created by jeffyuan on 2018/3/12.
 */
public class DataBaseAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    public void start() {

        if (MysqlConnectionPool.ConnectionURL.isEmpty()) {
            Properties pps = new Properties();
            try {
                String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
                pps.load(new FileInputStream(path + "logdb.properties"));

                Map<String, String> mapDbInfo = new HashMap<String, String>();

                Enumeration enum1 = pps.propertyNames();//得到配置文件的名字
                while(enum1.hasMoreElements()) {
                    String strKey = (String) enum1.nextElement();
                    String strValue = pps.getProperty(strKey);
                    mapDbInfo.put(strKey, strValue);
                }

                MysqlConnectionPool.DriverClass = mapDbInfo.get("logdb.driver");
                MysqlConnectionPool.ConnectionURL = mapDbInfo.get("logdb.url");
                MysqlConnectionPool.UserId = mapDbInfo.get("logdb.username");
                MysqlConnectionPool.Password = mapDbInfo.get("logdb.password");

                MysqlConnectionPool.getInstance().createPool(2);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.start();
    }

    @Override
    public void stop() {

        try {
            MysqlConnectionPool.getInstance().closeConnectionPool();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.stop();
    }

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {

        // 只记录controller访问的日志
        if (iLoggingEvent.getMessage().indexOf("[APP]") == -1) {
            return ;
        }

        String username = MDC.get("username");
        String ip = MDC.get("ip");
        String classMethod = MDC.get("class_method");
        String httpMethod = MDC.get("http_method");
        String url = MDC.get("url");


        Connection conn = null;
        try {

            conn = MysqlConnectionPool.getInstance().getConnection();

            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");

            String sql = "insert into sys_log_applog(id, create_time,action_url, descripts," +
                    "create_user,log_levels,method,class_name,ip) values('" + ToolUtil.getUUID() +"', '" +
                    sf.format(Calendar.getInstance().getTime())+ "',?,?,?,?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, url);
            pstmt.setString(2, iLoggingEvent.getMessage());
            pstmt.setString(3, username);
            pstmt.setString(4, iLoggingEvent.getLevel().toString());
            pstmt.setString(5, httpMethod);
            pstmt.setString(6, classMethod);
            pstmt.setString(7, ip);

            pstmt.execute();
        } catch (Exception ex) {
            System.out.println("ERROR: 日志记录异常！" );
        } finally {
            if (conn != null) {
                MysqlConnectionPool.getInstance().freeConnection(conn);
            }
        }
    }
}

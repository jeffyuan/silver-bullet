package com.silverbullet.codegenerator.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * mysql链接
 * Created by jeffyuan on 2018/3/9.
 */
public class MysqlConnection {

    private String ConnectionURL = "";
    private String DriverClass = "";
    private String UserId = "";
    private String databaseName = "";
    private String Password = "";

    public MysqlConnection(String driverClass, String connectionURL, String databaseName, String userId, String password) {
        this.ConnectionURL = connectionURL;
        this.DriverClass = driverClass;
        this.UserId = userId;
        this.Password = password;

        this.databaseName = databaseName;
    }

    /**
     * 获取数据链接
     * @return
     */
    public Connection getConnection() {
        try {
            Class.forName(DriverClass);

            Connection conn = DriverManager.getConnection(ConnectionURL,UserId, Password);
            if (conn.isClosed()) {
                return null;
            }

            return conn;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getDatabaseName() {
        return databaseName;
    }
}


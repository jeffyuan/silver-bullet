package com.silverbullet.log;

import java.sql.*;
import java.util.Enumeration;
import java.util.Vector;

/**
 * mysql连接池
 * Created by jeffyuan on 2018/3/9.
 */
public class MysqlConnectionPool {

    public static String ConnectionURL = "";  //连接URL地址
    public static String DriverClass = "";    //驱动
    public static String UserId = "";         //用户
    public static String databaseName = "";   //数据库
    public static String Password = "";       //密码
    private int maxConnections = 5;

    private Vector connections = null;         //连接池

    private MysqlConnectionPool() {

    }

    public static MysqlConnectionPool getInstance() {
        return MysqlConnectionHandle.instance;
    }

    private static class MysqlConnectionHandle {
        private static MysqlConnectionPool instance = new MysqlConnectionPool();
    }

    /**
     * 创建初始化连接池
     * @param poolSize 连接池大小
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     */
    public synchronized void createPool(int poolSize) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        if (connections != null) {
            return ;
        }

        Driver driver = (Driver)(Class.forName(DriverClass).newInstance());
        DriverManager.registerDriver(driver);

        connections = new Vector<Connection>();
        createConnections(poolSize);

        System.out.println("数据库连接池创建成功。");
    }

    /**
     * 创建新的链接
     * @param poolSize 一次性创建个数
     */
    private void createConnections(int poolSize) {
        for (int i = 0; i < poolSize; i++) {
            if (this.maxConnections >0 && this.connections.size() >= this.maxConnections) {
                break;
            }

            try {
                connections.addElement(new PooledConnection(newConnection()));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 创建一个新的链接
     * @return
     */
    private Connection newConnection() throws SQLException {

         Connection conn = DriverManager.getConnection(MysqlConnectionPool.ConnectionURL,
                MysqlConnectionPool.UserId, MysqlConnectionPool.Password);
        if (conn.isClosed()) {
            return null;
        }

        if (connections.size() == 0) {
            DatabaseMetaData metaData = conn.getMetaData();
            int driverMaxConnections = metaData.getMaxConnections(); //数据库允许连接最大数

            if (driverMaxConnections > 0 && this.maxConnections > driverMaxConnections) {
                this.maxConnections = driverMaxConnections;
            }
        }

        return conn;
    }

    /**
     * 测试一个链接的有效性
     * @param conn 链接
     * @return
     */
    private boolean testConnection(Connection conn) {

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select '1'");
            rs.next();

            System.out.println(rs.getString("1"));
            rs.close();
            stmt.close();

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * 查找一个空闲的链接
     * @return
     */
    private Connection findFreeConnection() {
        Connection conn = null;
        PooledConnection pooledConnection = null;

        // 遍历连接池
        Enumeration enumeration = connections.elements();
        while (enumeration.hasMoreElements()) {

            pooledConnection = (PooledConnection)enumeration.nextElement();

            if (!pooledConnection.isBusy()) {
                conn = pooledConnection.getConnection();
                pooledConnection.setBusy(true);

                if (!testConnection(conn)) {
                    try {
                        conn = newConnection();
                    } catch (SQLException ex) {
                        System.out.println("创建数据库连接失败！" + ex.getMessage());
                        return null;
                    }

                    pooledConnection.setConnection(conn);
                }

                break;
            }
        }

        return conn;
    }

    /**
     * 获取一个空闲的连接，如果获得不到，则新增加2个
     * @return
     */
    private Connection getFreeConnection() {
        Connection conn = findFreeConnection();
        if (conn == null) {
            createConnections(2);
            conn = findFreeConnection();
            if (conn == null) {
                return null;
            }
        }

        return conn;
    }

    /**
     * 从连接池中获取一个连接
     * @return
     * @throws SQLException
     * @throws InterruptedException
     */
    public synchronized Connection getConnection() throws SQLException, InterruptedException {
        if (connections == null) {
            return null;
        }

        Connection conn = getFreeConnection();
        while (conn == null) {
            wait(30);
            conn = getFreeConnection();
        }

        return conn;
    }

    /**
     * 归还连接
     * @param conn
     */
    public void freeConnection(Connection conn) {
        if (connections == null) {
            return ;
        }

        PooledConnection pooledConnection = null;
        Enumeration enumeration = connections.elements();

        while (enumeration.hasMoreElements()) {
            pooledConnection = (PooledConnection) enumeration.nextElement();
            if (conn == pooledConnection.getConnection()) {
                pooledConnection.setBusy(false);
                break;
            }
        }
    }

    /**
     * 刷新一个连接池
     * @throws SQLException
     * @throws InterruptedException
     */
    public synchronized void refreshConnections() throws SQLException, InterruptedException {
        if (connections == null) {
            return ;
        }

        PooledConnection pooledConnection = null;
        Enumeration enumeration = connections.elements();

        while (enumeration.hasMoreElements()) {
            pooledConnection = (PooledConnection)enumeration.nextElement();

            if (pooledConnection.isBusy()) {
                wait(5000);
            }

            closeConnection(pooledConnection.getConnection());
            pooledConnection.setConnection(newConnection());
            pooledConnection.setBusy(false);
        }
    }

    /**
     * 关闭连接池中的所有连接
     * @throws InterruptedException
     */
    public synchronized void closeConnectionPool() throws InterruptedException {
        if (connections == null) {
            return ;
        }

        PooledConnection pooledConnection = null;
        Enumeration enumeration = connections.elements();
        while (enumeration.hasMoreElements()) {
            pooledConnection = (PooledConnection)enumeration.nextElement();

            if (pooledConnection.isBusy()) {
                wait(5000);
            }

            closeConnection(pooledConnection.getConnection());
            connections.removeElement(pooledConnection);
        }
    }

    /**
     * 关闭一个连接
     * @param conn
     */
    private void closeConnection(Connection conn) {
       try {
           conn.close();
       } catch (SQLException e) {

       }
    }

    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * 内部类，描述连接和状态
     */
    class PooledConnection {
        Connection connection = null;
        boolean isBusy = false;

        public PooledConnection(Connection connection) {
            this.connection = connection;
        }

        public Connection getConnection() {
            return connection;
        }

        public void setConnection(Connection connection) {
            this.connection = connection;
        }

        public boolean isBusy() {
            return isBusy;
        }

        public void setBusy(boolean busy) {
            isBusy = busy;
        }
    }
}


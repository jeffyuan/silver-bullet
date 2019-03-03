package com.silverbullet.codegenerator.mybatis;

import com.silverbullet.codegenerator.database.MysqlConnection;
import com.silverbullet.codegenerator.pojo.TableColumnsInfo;
import com.silverbullet.codegenerator.pojo.TableConfig;
import com.silverbullet.utils.Table2JavaUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL表操作，根据表信息自动补齐字段类型以及java对应的类型
 * Created by jeffyuan on 2018/3/9.
 */
public class MysqlTableService {

    private MysqlConnection mysqlConnection = null;

    public MysqlTableService(String driverClass, String connectionURL, String databaseName, String userId, String password) {

        mysqlConnection = new MysqlConnection(driverClass, connectionURL, databaseName, userId, password);

    }

    /**
     * 获取table 的信息
     * @param listTables
     * @return
     */
    public void getTableInfo(List<TableConfig> listTables) throws SQLException {

        Connection connection = mysqlConnection.getConnection();

        for (TableConfig tableConfig : listTables) {
            getTableInfo(tableConfig, connection);
        }

        connection.close();
    }


    /**
     * 获取一个表的列信息
     * @param table
     * @param conn
     * @return
     */
    private TableConfig getTableInfo(TableConfig table, Connection conn) throws SQLException {

        String sql = "SELECT k.column_name from information_schema.TABLE_CONSTRAINTS t " +
                "JOIN information_schema.KEY_COLUMN_USAGE k using(constraint_name,table_schema, table_name) " +
                "WHERE t.constraint_type = 'PRIMARY KEY' AND  t.TABLE_SCHEMA='" +  mysqlConnection.getDatabaseName() + "' AND t.table_name = '"+ table.getTableName() +"'";

        // 获取表的主键
        String primaryKey = "";
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs.next()) {
            primaryKey = rs.getString("column_name");
        }

        rs.close();

        sql = "select COLUMN_NAME as 'CNAME', DATA_TYPE as 'DTYPE' ,COLUMN_TYPE as 'CTYPE',COLUMN_COMMENT as 'COMMENT'" +
                ",COLUMN_DEFAULT as 'CDEFAULT', IS_NULLABLE as 'ISNULL', CHARACTER_MAXIMUM_LENGTH as 'CLENGTH'" +
                ",NUMERIC_PRECISION as 'NUMPRECISION',NUMERIC_SCALE as 'NUMSCALE',COLUMN_KEY AS 'CKEY'" +
                ",PRIVILEGES,EXTRA FROM information_schema.`COLUMNS` where TABLE_SCHEMA='" +
                mysqlConnection.getDatabaseName() + "' AND table_name = '" + table.getTableName() + "';";
        rs = statement.executeQuery(sql);
        while (rs.next()) {

            String name = rs.getString("CNAME");
            String type = rs.getString("DTYPE");
            String typeAndLen = rs.getString("CTYPE");
            String comments = rs.getString("COMMENT");
            Long valueLen = rs.getLong("CLENGTH");
            String isNull = rs.getString("ISNULL");  // YES NO
            String defaultVal = rs.getString("CDEFAULT");
            Long numPrecision = rs.getLong("NUMPRECISION");  //某列类型的精确度(类型的长度)
            Long numSCALE = rs.getLong("NUMSCALE");  //小数点后的位数
            String columnKey = rs.getString("CKEY");  //PRI
            String privileges = rs.getString("PRIVILEGES"); //select,insert,update,references
            String extra = rs.getString("EXTRA");
            if (extra == null) {
                extra = "";
            }

            // 将数据库type 转为 batis的type
            type = Table2JavaUtil.tableType2BatisType(type);

            TableColumnsInfo tableColumnsInfo = new TableColumnsInfo(name, type.toUpperCase(),
                    typeAndLen.toUpperCase(), comments,defaultVal, columnKey, valueLen, numPrecision, numSCALE,
                    privileges,  isNull.equals("YES") ? true : false, extra.equals("auto_increment") ? true:false);

            if(name.equals(primaryKey)) {
                tableColumnsInfo.setPrimaryKey(true);
            }

            table.getColumns().add(tableColumnsInfo);
        }

        rs.close();
        statement.close();

        return table;
    }


    /**
     * 完成从table到java的转换
     * @param listTables
     */
    public void table2java(List<TableConfig> listTables) {
        for(TableConfig tableConfig : listTables) {

            for (TableColumnsInfo columnsInfo : tableConfig.getColumns()) {
                // 返回java 变量名称 和对应的类型，基础类型则无
                List<String> javaTypeInfo = Table2JavaUtil.tableType2JavaType(columnsInfo.getType());
                columnsInfo.setJavaType(javaTypeInfo.get(0));
                if (javaTypeInfo.size() > 1) {
                    columnsInfo.setJavaTypePackage(javaTypeInfo.get(1));
                }

                // 变量名
                columnsInfo.setJavaName(Table2JavaUtil.underline2Camel(columnsInfo.getName()));
            }

        }
    }

    public static void main(String [] args) {
        String ClasspathEntry = "C:\\Users\\GESOFT\\.m2\\repository\\mysql\\mysql-connector-java\\5.1.29\\mysql-connector-java-5.1.29.jar";
        String ConnectionURL = "jdbc:mysql://localhost:3306/silverbullet?useUnicode=true&characterEncoding=utf-8";
        String DriverClass = "com.mysql.jdbc.Driver";
        String UserId = "tkrobot";
        String Password = "tkrobot";

        MysqlTableService mysqlTableService = new MysqlTableService(DriverClass, ConnectionURL, "silverbullet", UserId, Password);

        List<TableConfig> listTables = new ArrayList<TableConfig>();

        listTables.add(new TableConfig("sys_auth_post", "SysAuthPost", "机构管理角色", false));

        try {
            mysqlTableService.getTableInfo(listTables);
            mysqlTableService.table2java(listTables);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

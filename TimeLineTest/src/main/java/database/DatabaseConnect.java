package database;

import java.sql.*;

public class DatabaseConnect {

    public static Connection conn() throws SQLException{

        Connection connection = null;
        String driverName = "com.mysql.cj.jdbc.Driver";
        String dbURL = "jdbc:mysql://127.0.0.1:3306/timeline?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
        String username = "root";
        String userPwd = "fujiaming123*";

        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(dbURL,username,userPwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }

}

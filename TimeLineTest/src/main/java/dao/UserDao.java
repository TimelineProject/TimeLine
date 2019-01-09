package dao;

import bean.User;
import database.DatabaseConnect;

import java.sql.*;

public class UserDao {

    public void addNewUser(String userName,String password) throws SQLException{
        Connection conn = DatabaseConnect.conn();
        String sql = "insert into users(account,password) values ('"
                + userName + "','"
                + password + "')";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
        conn.close();
    }

    public User findUser(String userName,String password) throws SQLException {
        Connection conn = DatabaseConnect.conn();
        String sql = "select * from users where account = '" + userName + "' and " + "password = '" + password + "'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if(!rs.next()){
            rs.close();
            stmt.close();
            conn.close();
            return null;
        }else{
            User user = new User(userName,password);
            rs.close();
            stmt.close();
            conn.close();
            return user;
        }

    }

    public boolean existUserName(String userName) throws SQLException {
        Connection conn = DatabaseConnect.conn();
        String sql = "select * from users where account = '" + userName + "'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if(!rs.next()){
            rs.close();
            stmt.close();
            conn.close();
            return false;
        }else{
            rs.close();
            stmt.close();
            conn.close();
            return true;
        }
    }
}
